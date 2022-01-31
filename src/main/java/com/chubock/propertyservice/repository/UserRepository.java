package com.chubock.propertyservice.repository;


import com.chubock.propertyservice.entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends AbstractRepository<User>, JpaSpecificationExecutor<User> {
}
