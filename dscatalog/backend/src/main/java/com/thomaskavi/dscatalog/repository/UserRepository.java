package com.thomaskavi.dscatalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thomaskavi.dscatalog.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  User findByEmail(String email);
}
