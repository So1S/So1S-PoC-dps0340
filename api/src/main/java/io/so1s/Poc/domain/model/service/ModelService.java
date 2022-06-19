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

    public Model cloneGitRepository(String url) throws GitAPIException, IOException {
        File tempFolder = Files.createTempDirectory("tempGit").toFile();

        Git git = Git.cloneRepository()
                .setURI(url)
                .setDirectory(tempFolder)
                .call();


        Model model = modelRepository.saveAndFlush(
                Model.builder()
                        .url(url)
                        .modelType(ModelType.GIT)
                        .build()
        );

        return model;
    }

}
