package com.chubock.propertyservice.endpoint;

import com.chubock.propertyservice.endpoint.dto.UserDTO;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "user-service")
public interface UserEndpoint {

    @GetMapping("/users/me")
    @Headers("Content-Type: application/json")
    UserDTO getUserDetails();

    @GetMapping("/users/{id}")
    @Headers("Content-Type: application/json")
    UserDTO getUserDetails(@PathVariable("id") String id);

    @PutMapping("/users/me")
    @Headers("Content-Type: application/json")
    UserDTO updateUserDetails(UserDTO userDTO);


}
