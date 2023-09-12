package com.avaya.ept.pocs.odataspike.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avaya.ept.pocs.odataspike.domains.CustomerInfo;
import com.avaya.ept.pocs.odataspike.services.CustomerInfoService;

@RestController
@RequestMapping("/customerinfo")
public class CustomerInfoController {

    @Autowired
    CustomerInfoService customerInfoService;

    @GetMapping(value = "/list")
    List<CustomerInfo> list() {
        return customerInfoService.getCustomerInfoList();
    }
}
