package org.scalefocus.business;

import org.scalefocus.customExceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "businesses")
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
            try {
                business = businessService.getBusinessesByType(type);
            } catch (BusinessNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
            return ResponseEntity.ok(business);
        }
        if (city != null) {
            try {
                business = businessService.getBusinessesByCity(city);
            } catch (BusinessNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
            return ResponseEntity.ok(business);
        }
        if (rating != null) {
            try {
                business = businessService.getBusinessesByRating(rating);
            } catch (BusinessNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
            return ResponseEntity.ok(business);
        }
        business = businessService.getAllBusinesses();
        return ResponseEntity.ok(business);
    }

    @PostMapping
    public ResponseEntity addBusiness(@RequestBody BusinessRequest businessRequest) {
        try {
            businessService.addBusiness(businessRequest);
        } catch (InvalidTypeException | InvalidCityException | InvalidPhoneNumberFormatException |
                 InvalidEmailException | InvalidNameException | BusinessAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
        return ResponseEntity.status(201).build();
    }
}