package com.avaya.ept.pocs.odataspike.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avaya.ept.pocs.odataspike.domains.AttributeList;
import com.avaya.ept.pocs.odataspike.repository.AttributeListRepository;

@Service
public class AttributeListService {
    @Autowired
    AttributeListRepository attributeListRepository;

    public List<AttributeList> getAttributeListList() {
        return attributeListRepository.findAll();
    }
}
