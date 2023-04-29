package org.scalefocus.business;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BusinessController {

    private final BusinessService businessService;

    public BusinessController(BusinessService businessService) {
        this.businessService = businessService;
    }

    @GetMapping("/businesses")
    public ResponseEntity printAllBusinesses(){
        List<BusinessDto> businessDtos = businessService.getAllBusinesses();
        return ResponseEntity.ok(businessDtos);
    }
}
