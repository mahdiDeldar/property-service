package com.chubock.propertyservice.rest;

import com.chubock.propertyservice.model.PropertyModel;
import com.chubock.propertyservice.model.UserModel;
import com.chubock.propertyservice.service.PropertyService;
import com.chubock.propertyservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users/me")
public class UserRestController {

    private final UserService userService;
    private final PropertyService propertyService;

    public UserRestController(UserService userService, PropertyService propertyService) {
        this.userService = userService;
        this.propertyService = propertyService;
    }

    @GetMapping
    @Operation(summary = "Getting profile")
    public UserModel getProfile(Authentication authentication) {
        return userService.getProfile(authentication.getName());
    }

    @PutMapping
    @Operation(summary = "Updating profile")
    public UserModel updateProfile(Authentication authentication, @RequestBody UserModel model) {
        model.setId(authentication.getName());
        return userService.updateProfile(model);
    }


    @GetMapping("/properties")
    @Operation(summary = "Getting properties registered by user")
    public List<PropertyModel> getProperties(Authentication authentication) {
        return propertyService.getUserProperties(authentication.getName());
    }

    @GetMapping("/favorites")
    @Operation(summary = "Getting user favourite properties")
    public List<PropertyModel> getFavoriteProperties(Authentication authentication) {
        return propertyService.getUserFavoriteProperties(authentication.getName());
    }

    @PutMapping("/favorites/{id}")
    @Operation(summary = "Adding a property to users favourite properties")
    public void addToFavorites(@PathVariable("id") String id, Authentication authentication) {
        propertyService.addToUserFavorites(authentication.getName(), id);
    }

    @DeleteMapping("/favorites/{id}")
    @Operation(summary = "Removing a property from users favourite properties")
    public void removeFromFavorites(@PathVariable("id") String id, Authentication authentication) {
        propertyService.removeFromUserFavorites(authentication.getName(), id);
    }
}
