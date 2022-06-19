package io.so1s.Poc.domain.model.service;

import io.so1s.Poc.domain.model.entity.Model;
import io.so1s.Poc.domain.model.entity.ModelType;
import io.so1s.Poc.domain.model.repository.ModelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
@RequiredArgsConstructor
@Slf4j
public class ModelService {
    private final ModelRepository modelRepository;

    public File cloneGitRepository(Model model) throws GitAPIException, IOException {
        var url = model.getUrl();

        File tempFolder = Files.createTempDirectory("tempGit").toFile();

        Git git = Git.cloneRepository()
                .setURI(url)
                .setDirectory(tempFolder)
                .call();

        return tempFolder;
    }

    synchronized public void buildImageFromModel(Model model) throws GitAPIException, IOException {
        File gitFolder = cloneGitRepository(model);

        var folderUrl = gitFolder.toString();

        var builderFolder = new File("/usr/src/git-repo/builder");

        var processBuilder = new ProcessBuilder("/bin/bash", "/usr/src/git-repo/builder/load-template.sh").directory(builderFolder).redirectErrorStream(true);;

        var environment = processBuilder.environment();

        environment.put("BUILD_GIT_REPOSITORY", gitFolder.toString());

        var process = processBuilder.start();

        synchronized (process) {
            try {
                process.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        log.info(new String(process.getInputStream().readAllBytes()));

        process = new ProcessBuilder("kubectl", "delete", "-f", "job.yaml").directory(builderFolder).redirectErrorStream(true).start();

        synchronized (process) {
            try {
                process.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        log.info(new String(process.getInputStream().readAllBytes()));

        // run builder -> build bentoml images
        process = new ProcessBuilder("skaffold", "run", "--tail").directory(builderFolder).redirectErrorStream(true).start();

        synchronized (process) {
            try {
                process.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        log.info(new String(process.getInputStream().readAllBytes()));

    }

}
