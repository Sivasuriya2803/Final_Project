package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.Policy;

public interface PolicyRepository extends JpaRepository<Policy, Long> {
    // Add custom queries if needed
}
