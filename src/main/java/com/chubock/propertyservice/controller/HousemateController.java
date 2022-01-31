package com.chubock.propertyservice.controller;

import com.chubock.propertyservice.model.UserModel;
import com.chubock.propertyservice.service.HousemateService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/housemates")
public class HousemateController {

    private final HousemateService housemateService;

    public HousemateController(HousemateService housemateService) {
        this.housemateService = housemateService;
    }

    @GetMapping("/{id}")
    public String housemate(@PathVariable("id") String id, Model model) {

        UserModel housemate = housemateService.getHousemate(id);
        model.addAttribute("url", "https://test.elegant-designs.net/housemates/" + id);
        model.addAttribute("housemate", housemate);
        return "housemates";
    }
}
