package com.chubock.propertyservice.model;

import com.chubock.propertyservice.entity.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class AbstractModel<T extends AbstractEntity> {

    private String id;

    public void fill(T entity) {
        setId(entity.getId());
    }

}
