package com.chubock.propertyservice.rest;

import com.chubock.propertyservice.model.PropertyModel;
import com.chubock.propertyservice.model.PropertySearchModel;
import com.chubock.propertyservice.model.UserModel;
import com.chubock.propertyservice.service.PropertyService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/properties")
public class PropertyRestController {

    private final PropertyService propertyService;

    public PropertyRestController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @PostMapping("search")
    public Page<PropertyModel> get(Authentication authentication,
                                   @RequestBody PropertySearchModel model,
                                   Pageable pageable) {

        return propertyService.getProperties(authentication.getName(), model, pageable);

    }

    @PostMapping
    @Operation(summary = "Adding a new property")
    public PropertyModel add(Authentication authentication, @Validated @RequestBody PropertyModel model) {

        model.setId(null);
        model.setOwner(new UserModel(authentication.getName()));
        return propertyService.save(model);

    }

    @PutMapping("/{id}")
    @Operation(summary = "Updating an existing property")
    public PropertyModel update(Authentication authentication,
                                @PathVariable("id") String id, @Validated @RequestBody PropertyModel model) {

        UserModel owner = propertyService.getPropertyOwner(id);

        if (!owner.getId().equals(authentication.getName()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        model.setId(id);
        model.setOwner(new UserModel(authentication.getName()));
        return propertyService.save(model);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Getting a property details")
    public PropertyModel get(Authentication authentication, @PathVariable("id") String id) {
        return propertyService.getProperty(authentication.getName(), id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Removing a property")
    public void delete(@PathVariable("id") String id, Authentication authentication) {
        UserModel owner = propertyService.getPropertyOwner(id);

        if (!owner.getId().equals(authentication.getName()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        propertyService.delete(id);
    }
}
