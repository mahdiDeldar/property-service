package com.chubock.propertyservice.rest;

import com.chubock.propertyservice.model.PropertySearchModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/config")
public class ConfigRestController {

    private static Map<String, Object> CONFIG = new HashMap<>();

    static {
        CONFIG.put("PROPERTY_SEARCH_DEFAULT_ZOOM", 16);
        CONFIG.put("PROPERTY_SEARCH_GEOGRAPHY_RADIUS", PropertySearchModel.RADIUS);
        CONFIG.put("PRIVACY_POLICY", "This is privacy policy");
        CONFIG.put("TERMS_OF_SERVICE", "This is terms of service");
        CONFIG.put("APPLICABLE", Arrays.asList("Mix gendered living", "Same gendered living", "Pet friendly", "Quiet living", "LGBTQ Friendly", "420 Friendly"));
        CONFIG.put("DESCRIPTORS", Arrays.asList("Student", "Self-Employed", "Employed", "Seeking Work", "Retired", "Other"));
        CONFIG.put("REPORT_TYPES", Arrays.asList("Spam or Abuse", "Something isn't Working", "General Feedback"));
    }
    @GetMapping
    public Map<String, Object> config() {
        return CONFIG;
    }

}
