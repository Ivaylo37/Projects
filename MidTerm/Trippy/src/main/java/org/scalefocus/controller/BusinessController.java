package org.scalefocus.controller;

import org.scalefocus.domain.Business;
import org.scalefocus.domain.request.BusinessRequest;
import org.scalefocus.service.BusinessService;
import org.scalefocus.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/business")
public class BusinessController {

    private final BusinessService businessService;

    public BusinessController(BusinessService businessService) {
        this.businessService = businessService;
    }

    @GetMapping
    public ResponseEntity printBusinesses(@RequestParam(required = false) String type,
                                          @RequestParam(required = false) String city,
                                          @RequestParam(required = false) Integer rating) {
        List<Business> business;
        if (type != null) {
            return ResponseEntity.ok(businessService.getBusinessesByType(type));
        }
        if (city != null) {
            return ResponseEntity.ok(businessService.getBusinessesByCity(city));
        }
        if (rating != null) {
            return ResponseEntity.ok(businessService.getBusinessesByRating(rating));
        }
        business = businessService.getAllBusinesses();
        return ResponseEntity.ok(business);
    }

    @PostMapping
    public ResponseEntity createBusiness(@RequestBody BusinessRequest businessRequest) {
        businessService.createBusiness(businessRequest);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity editBusiness(@PathVariable Integer id,
                                       @RequestParam(required = false) String email,
                                       @RequestParam(required = false) String phone) {
        if (email != null) {
            businessService.updateEmail(id, email);
        }
        if (phone != null) {
            businessService.updatePhoneNumber(id, phone);
        }
        return ResponseEntity.status(201).build();
    }
}