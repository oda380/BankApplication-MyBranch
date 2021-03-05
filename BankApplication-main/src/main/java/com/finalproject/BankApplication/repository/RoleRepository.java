package com.finalproject.BankApplication.repository;

import com.finalproject.BankApplication.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface RoleRepository extends JpaRepository<Role, Integer> {

    public Role findByRole(String role);
}
