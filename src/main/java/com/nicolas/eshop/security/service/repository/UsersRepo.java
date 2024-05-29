package com.nicolas.eshop.security.service.repository;


import com.nicolas.eshop.security.service.entity.OurUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsersRepo extends JpaRepository<OurUsers, Integer> {

    Optional<OurUsers> findByEmail(String email);

    List<OurUsers> findByRole(String role);
}
