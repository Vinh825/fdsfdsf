package com.internship.iam.repository;

import com.internship.iam.model.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, String> {

    public List<UserRole> findByUserId(String userId);

}
