package com.chubock.propertyservice.rest;

import com.chubock.propertyservice.model.ReportModel;
import com.chubock.propertyservice.service.ReportService;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reports")
public class ReportRestController {

    private final ReportService reportService;

    public ReportRestController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    public ReportModel save(@RequestBody @Validated ReportModel model, Authentication authentication) {

        return reportService.save(authentication.getName(), model);

    }
}
