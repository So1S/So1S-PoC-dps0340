package io.so1s.Poc.domain.model.service;

import io.so1s.Poc.domain.model.entity.Model;
import io.so1s.Poc.domain.model.entity.ModelType;
import io.so1s.Poc.domain.model.repository.ModelRepository;
import lombok.RequiredArgsConstructor;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
@RequiredArgsConstructor
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

        var builderFolder = gitFolder.toPath().resolve("builder").toFile();


        var processBuilder = new ProcessBuilder(String.format("/bin/bash /usr/src/git-repo/builder/load-template.sh", folderUrl)).directory(builderFolder);

        var environment = processBuilder.environment();

        environment.put("BUILD_GIT_REPOSITORY", gitFolder.toString());

        var process = processBuilder.inheritIO().start();

        synchronized (process) {
            try {
                process.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        process = new ProcessBuilder("skaffold build").directory(builderFolder).inheritIO().start();

        synchronized (process) {
            try {
                process.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
