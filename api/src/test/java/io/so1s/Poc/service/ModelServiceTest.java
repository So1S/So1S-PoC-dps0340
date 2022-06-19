package io.so1s.Poc.service;

import io.so1s.Poc.domain.model.entity.Model;
import io.so1s.Poc.domain.model.entity.ModelType;
import io.so1s.Poc.domain.model.service.ModelService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ModelServiceTest {
    @Autowired
    private ModelService modelService;

    @Test
    public void gitRepositoryTest() throws Exception {
        var url = "https://github.com/So1S/So1S-PoC-bentoml";

        final var model = Model.builder().url(url).modelType(ModelType.GIT).build();

        modelService.buildImageFromModel(model);

    }
}
