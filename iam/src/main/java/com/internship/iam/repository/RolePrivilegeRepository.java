package com.internship.iam.repository;

import com.internship.iam.model.entity.RolePrivilege;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolePrivilegeRepository extends JpaRepository<RolePrivilege, String> {

    public List<RolePrivilege> findByRoleId(String roleId);

}
