package com.banking.project.repository;

import com.banking.project.model.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardsRepository extends JpaRepository<CardEntity,Long> {
}
