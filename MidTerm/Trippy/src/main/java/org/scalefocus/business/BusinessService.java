package org.scalefocus.business;

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
}