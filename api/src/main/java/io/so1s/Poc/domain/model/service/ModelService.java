package io.so1s.Poc.domain.model.service;

import io.so1s.Poc.domain.model.entity.Model;
import io.so1s.Poc.domain.model.entity.ModelType;
import io.so1s.Poc.domain.model.repository.ModelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.util.IO;
import org.springframework.boot.logging.Slf4JLoggingSystem;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

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

    public void launchProcess(String[] args, File directory) throws IOException, InterruptedException {
        var env = new HashMap<String, String>();

        launchProcess(args, directory, env);
    }

    public void launchProcess(String[] args, File directory, Map<String, String> env) throws IOException, InterruptedException {

        var processBuilder = new ProcessBuilder(args).directory(directory).inheritIO();

        var environment = processBuilder.environment();

        environment.putAll(env);

        var process = processBuilder.start();

        process.waitFor();
    }

    synchronized public void buildImageFromModel(Model model) throws GitAPIException, IOException, InterruptedException {
        File gitFolder = cloneGitRepository(model);

        var folderUrl = gitFolder.toString();

        var builderFolder = new File("/usr/src/git-repo/builder");

        var env = new HashMap<String, String>();

        env.put("BUILD_GIT_REPOSITORY", gitFolder.toString());

        launchProcess(new String[] {"/bin/bash", "/usr/src/git-repo/builder/load-template.sh"}, builderFolder, env);
        launchProcess(new String[] {"kubectl", "delete", "-f", "job.yaml"}, builderFolder);
        // run builder -> build bentoml images
        launchProcess(new String[] {"skaffold", "run", "--tail"}, builderFolder);
    }

}
