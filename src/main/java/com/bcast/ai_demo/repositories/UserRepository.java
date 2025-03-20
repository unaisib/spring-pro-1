package com.bcast.ai_demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bcast.ai_demo.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}

