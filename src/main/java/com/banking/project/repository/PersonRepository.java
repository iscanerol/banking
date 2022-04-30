package com.banking.project.repository;

import com.banking.project.model.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {
    PersonEntity findByEmail(String email);
}
