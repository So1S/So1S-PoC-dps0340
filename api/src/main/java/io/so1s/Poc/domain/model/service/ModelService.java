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

    public void buildImageFromModel(Model model) throws GitAPIException, IOException {
        File gitFolder = cloneGitRepository(model);

        var folderUrl = gitFolder.toString();

        var process = new ProcessBuilder(String.format("%s/dockerize.sh", folderUrl)).directory(gitFolder).inheritIO().start();

        synchronized (process) {
            try {
                process.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
