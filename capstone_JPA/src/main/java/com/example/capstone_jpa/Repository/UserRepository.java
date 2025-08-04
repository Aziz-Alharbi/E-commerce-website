package com.example.capstone_jpa.Repository;

import com.example.capstone_jpa.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
