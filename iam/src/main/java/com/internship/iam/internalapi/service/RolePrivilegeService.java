package com.internship.iam.internalapi.service;

import com.internship.iam.model.entity.RolePrivilege;
import com.internship.iam.repository.RolePrivilegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolePrivilegeService {

    @Autowired
    private RolePrivilegeRepository rolePrivilegeRepository;

    public List<RolePrivilege> getManyByRoleId(String roleId) {
        return rolePrivilegeRepository.findByRoleId(roleId);
    }

}
