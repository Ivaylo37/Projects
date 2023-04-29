package org.scalefocus.business;

import org.scalefocus.customExceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BusinessController {

    private final BusinessService businessService;

    public BusinessController(BusinessService businessService) {
        this.businessService = businessService;
    }

    @GetMapping("/businesses")
    public ResponseEntity printAllBusinesses(@RequestParam(required = false) String type,
                                             @RequestParam(required = false) String city,
                                             @RequestParam(required = false) Integer rating)
    {
        List<BusinessDto> businessDtos;
        if (type != null){
            try {
                businessDtos = businessService.getBusinessesByType(type);
            } catch (BusinessNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
            return ResponseEntity.ok(businessDtos);
        }
        if (city != null){
            try {
                businessDtos = businessService.getBusinessesByCity(city);
            } catch (BusinessNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
            return ResponseEntity.ok(businessDtos);
        }
        if (rating != null){
            try {
                businessDtos = businessService.getBusinessesByRating(rating);
            } catch (BusinessNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
            return ResponseEntity.ok(businessDtos);
        }
        businessDtos = businessService.getAllBusinesses();
        return ResponseEntity.ok(businessDtos);
    }

    @PostMapping("/businesses")
    public ResponseEntity addBusiness(@RequestBody BusinessRequest businessRequest){
        try {
            businessService.addBusiness(businessRequest);
        } catch (InvalidTypeException | InvalidCityException | InvalidPhoneNumberFormatException |
                 InvalidEmailException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
        return ResponseEntity.status(201).build();

    }
}
