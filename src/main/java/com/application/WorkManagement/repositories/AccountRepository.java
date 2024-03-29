package com.application.WorkManagement.repositories;

import com.application.WorkManagement.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

    Optional<Account> findAccountByEmail(String email);

    Boolean existsAccountByEmail(String email);

}
