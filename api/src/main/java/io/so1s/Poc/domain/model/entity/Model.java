package io.so1s.Poc.domain.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Model {

    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }
}
