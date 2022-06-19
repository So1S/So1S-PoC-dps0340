package io.so1s.Poc.domain.model.dto.response;

import io.so1s.Poc.domain.model.entity.ModelType;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class ModelResponseDto {

    private Long id;

    @Builder.Default
    private String url = "";

    @Builder.Default
    private ModelType modelType = ModelType.DEFAULT;
}
