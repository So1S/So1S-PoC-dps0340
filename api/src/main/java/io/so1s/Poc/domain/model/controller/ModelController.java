package io.so1s.Poc.domain.model.controller;

import io.so1s.Poc.domain.model.dto.mapper.ModelMapper;
import io.so1s.Poc.domain.model.dto.request.GitRequestDto;
import io.so1s.Poc.domain.model.entity.Model;
import io.so1s.Poc.domain.model.entity.ModelType;
import io.so1s.Poc.domain.model.service.ModelService;
import lombok.RequiredArgsConstructor;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/models")
@RequiredArgsConstructor
public class ModelController {

    private final ModelService modelService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<?> createByGit(@Valid @RequestBody GitRequestDto gitRequestDto) throws GitAPIException, IOException {
        final var url = gitRequestDto.getUrl();

        final var modelEntity = Model.builder().url(url).modelType(ModelType.GIT).build();

        modelService.buildImageFromModel(modelEntity);

        final var modelDto = modelMapper.toResponseDto(modelEntity);

        return ResponseEntity.ok(modelDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id) {


        return ResponseEntity.ok(null);
    }
}
