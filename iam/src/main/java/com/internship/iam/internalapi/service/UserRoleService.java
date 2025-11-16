package com.internship.iam.internalapi.service;

import com.internship.iam.model.entity.UserRole;
import com.internship.iam.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    public List<UserRole> getManyByUserId(String userId) {
        return userRoleRepository.findByUserId(userId);
    }

}
