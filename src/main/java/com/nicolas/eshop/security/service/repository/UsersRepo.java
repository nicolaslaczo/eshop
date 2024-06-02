package com.nicolas.eshop.security.service.repository;


import com.nicolas.eshop.security.service.entity.OurUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UsersRepo extends JpaRepository<OurUsers, Integer> {

    Optional<OurUsers> findByEmail(String email);

    List<OurUsers> findByRole(String role);
}
