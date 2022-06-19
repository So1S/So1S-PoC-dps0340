package io.so1s.Poc.domain.model.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class GitRequestDto {

    @NotNull
    private String url = "";
}
