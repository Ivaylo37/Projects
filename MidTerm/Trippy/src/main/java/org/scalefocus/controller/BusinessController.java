package org.scalefocus.controller;

import org.scalefocus.model.request.BusinessRequest;
import org.scalefocus.enums.Type;
import org.scalefocus.service.BusinessService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/business")
public class BusinessController {

    private final BusinessService businessService;

    public BusinessController(BusinessService businessService) {
        this.businessService = businessService;
    }

    @GetMapping
    public ResponseEntity getBusinesses(@RequestParam(required = false) Type type,
                                        @RequestParam(required = false) String city,
                                        @RequestParam(required = false) Integer rating) {
        if (type != null) {
            return ResponseEntity.ok(businessService.getBusinessesByType(type));
        }
        if (city != null) {
            return ResponseEntity.ok(businessService.getBusinessesByCity(city));
        }
        if (rating != null) {
            return ResponseEntity.ok(businessService.getBusinessesByRating(rating));
        }
        return ResponseEntity.ok(businessService.getAllBusinesses());
    }

    @PostMapping
    public ResponseEntity createBusiness(@RequestBody BusinessRequest businessRequest) {
        businessService.createBusiness(businessRequest);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity updateBusiness(@PathVariable Integer id,
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