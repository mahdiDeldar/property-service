package com.chubock.propertyservice.repository;

import com.chubock.propertyservice.entity.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbstractRepository<T extends AbstractEntity> extends JpaRepository<T, String> {
}
