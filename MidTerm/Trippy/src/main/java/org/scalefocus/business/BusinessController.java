package org.scalefocus.business;

import org.scalefocus.customExceptions.BusinessNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BusinessController {

    private final BusinessService businessService;

    public BusinessController(BusinessService businessService) {
        this.businessService = businessService;
    }

    @GetMapping("/businesses")
    public ResponseEntity printAllBusinesses(@RequestParam(required = false) String type)
    {
        List<BusinessDto> businessDtos;
        if (type != null){
            try {
                businessDtos = businessService.getBusinessesByType(type);
            } catch (BusinessNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        }
        businessDtos = businessService.getAllBusinesses();
        return ResponseEntity.ok(businessDtos);
    }
}
