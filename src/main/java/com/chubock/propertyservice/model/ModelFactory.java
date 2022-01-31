package com.chubock.propertyservice.model;

import com.chubock.propertyservice.entity.AbstractEntity;
import org.springframework.beans.BeanUtils;

public class ModelFactory {

    public static <T extends AbstractEntity, R extends AbstractModel<T>> R of(T entity, Class<R> clazz) {

        R model = BeanUtils.instantiateClass(clazz);
        model.fill(entity);
        return model;

    }

}
