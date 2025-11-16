package com.internship.iam.repository;

import com.internship.iam.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    public User findByUsernameAndPassword(String username, String password);

}
