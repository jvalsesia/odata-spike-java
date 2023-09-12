package com.avaya.ept.pocs.odataspike.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.avaya.ept.pocs.odataspike.domains.CustomerInfo;

public interface CustomerInfoRepository extends JpaRepository<CustomerInfo, UUID> {

    @Query("SELECT DISTINCT customerInfo FROM CustomerInfo customerInfo "
            + "JOIN FETCH customerInfo.customerInfoSurveyContexts customerInfoSurveyContexts")
    List<CustomerInfo> retrieveAll();
}
