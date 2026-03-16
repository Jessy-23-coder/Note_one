package com.example.demo.repository;

import com.example.demo.entity.Resolution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResolutionRepository extends JpaRepository<Resolution, Integer> {
    // Les méthodes findAll, findById, save, delete sont déjà fournies par JpaRepository
}