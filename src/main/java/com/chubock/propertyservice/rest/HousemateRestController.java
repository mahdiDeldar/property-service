package com.chubock.propertyservice.rest;

import com.chubock.propertyservice.model.UserModel;
import com.chubock.propertyservice.model.UserSearchModel;
import com.chubock.propertyservice.service.HousemateService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/housemates")
public class HousemateRestController {

    private final HousemateService housemateService;

    public HousemateRestController(HousemateService housemateService) {
        this.housemateService = housemateService;
    }

    @PostMapping("search")
    public Page<UserModel> getHousemates(Authentication authentication, @RequestBody UserSearchModel model, Pageable pageable) {

        model.setIgnoreUserId(authentication.getName());

        return housemateService.getHousemates(model, pageable);
    }

    @GetMapping("/{id}")
    public UserModel getHousemate(@PathVariable("id") String id) {
        return housemateService.getHousemate(id);
    }

}
