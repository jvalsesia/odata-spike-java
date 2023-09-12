package com.avaya.ept.pocs.odataspike.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import com.avaya.ept.pocs.odataspike.domains.QuestionOption;

public interface QuestionOptionRepository extends JpaRepository<QuestionOption, UUID> {
}
