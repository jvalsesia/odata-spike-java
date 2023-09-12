package com.avaya.ept.pocs.odataspike.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import com.avaya.ept.pocs.odataspike.domains.Answer;

public interface AnswerRepository extends JpaRepository<Answer, UUID> {
}
