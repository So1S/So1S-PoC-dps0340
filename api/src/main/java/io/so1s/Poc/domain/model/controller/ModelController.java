package io.so1s.Poc.domain.model.controller;

import io.so1s.Poc.domain.model.dto.request.GitRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/models")
public class ModelController {

    @PostMapping
    public ResponseEntity<?> createByGit(@Valid @RequestBody GitRequestDto gitRequestDto) {



        return ResponseEntity.ok(null);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id) {


        return ResponseEntity.ok(null);
    }
}
