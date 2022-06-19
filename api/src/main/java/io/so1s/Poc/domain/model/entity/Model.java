package io.so1s.Poc.domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "model")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Builder.Default
    private String url = "";

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private ModelType modelType = ModelType.DEFAULT;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @Builder.Default
    private List<ModelData> modelDataList = new ArrayList<>();
}
