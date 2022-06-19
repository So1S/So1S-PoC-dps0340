package io.so1s.Poc.domain.model.dto.mapper;

import io.so1s.Poc.domain.model.dto.response.ModelResponseDto;
import io.so1s.Poc.domain.model.entity.Model;
import org.springframework.stereotype.Component;

@Component
public class ModelMapper {
    public ModelResponseDto toResponseDto(Model model) {
        return ModelResponseDto.builder()
                .build();
    }
}
