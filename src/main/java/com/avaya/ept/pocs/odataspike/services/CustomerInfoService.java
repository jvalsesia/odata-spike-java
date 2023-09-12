package com.avaya.ept.pocs.odataspike.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avaya.ept.pocs.odataspike.domains.CustomerInfo;
import com.avaya.ept.pocs.odataspike.repository.CustomerInfoRepository;

@Service
public class CustomerInfoService {

    @Autowired
    CustomerInfoRepository customerInfoRepository;

    public List<CustomerInfo> getCustomerInfoList() {
        List<CustomerInfo> list = customerInfoRepository.findAll();
        return list;
    }

}
