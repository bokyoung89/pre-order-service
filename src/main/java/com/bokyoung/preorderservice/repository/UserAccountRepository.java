package com.bokyoung.preorderservice.repository;

import com.bokyoung.preorderservice.domain.UserAccount;
import com.bokyoung.preorderservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    Optional<UserAccount> findByEmail(String email);
}
