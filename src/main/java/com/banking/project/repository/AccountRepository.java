package com.banking.project.repository;

import com.banking.project.model.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    List<AccountEntity> findByPersonId(Long id);
}
