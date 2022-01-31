package com.chubock.propertyservice.endpoint.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {

    private String id;
    private String name;
    private String username;
    private String imageUrl;

}
