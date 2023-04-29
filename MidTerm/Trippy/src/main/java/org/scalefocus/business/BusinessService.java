package org.scalefocus.business;

import org.scalefocus.customExceptions.BusinessNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BusinessService {

    private final BusinessAccessor businessAccessor;
    @Autowired
    public BusinessService(BusinessAccessor businessAccessor) {
        this.businessAccessor = businessAccessor;
    }

    public List<BusinessDto> getAllBusinesses(){
        return businessAccessor.getAllBusinesses();
    }

    public List<BusinessDto> getBusinessesByType(String type) throws BusinessNotFoundException {
        return businessAccessor.getBusinessByType(type);
    }

    public List<BusinessDto> getBusinessesByCity(String city) throws BusinessNotFoundException {
        return businessAccessor.getBusinessByCity(city);
    }

    public List<BusinessDto> getBusinessesByRating(int rating) throws BusinessNotFoundException {
        return businessAccessor.getBusinessByRating(rating);
    }
}