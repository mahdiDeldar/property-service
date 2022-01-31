package com.chubock.propertyservice.controller;

import com.chubock.propertyservice.model.PropertyModel;
import com.chubock.propertyservice.service.PropertyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/properties")
public class PropertyController {

    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping("/{id}")
    public String property(@PathVariable("id") String id, Model model) {

        PropertyModel property = propertyService.getProperty(id);
        model.addAttribute("property", property);
        return "properties";

    }

}
