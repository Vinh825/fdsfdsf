package com.internship.iam.internalapi.service;

import com.internship.iam.model.entity.Role;
import com.internship.iam.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role getOneById(String id) {
        return roleRepository.findById(id).orElse(null);
    }

}
