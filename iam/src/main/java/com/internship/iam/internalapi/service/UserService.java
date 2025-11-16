package com.internship.iam.internalapi.service;

import com.internship.iam.model.entity.User;
import com.internship.iam.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    /**
     * @author Mentor
     */
    public User getOneById(String id) {
        return userRepository.findById(id).orElse(null);
    }

}
