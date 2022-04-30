package com.banking.project.repository;

import com.banking.project.model.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<AddressEntity,Long> {
    List<AddressEntity> findByPersonId(Long id);
//    List<AddressEntity> findByPersonEmail(String email);

}
