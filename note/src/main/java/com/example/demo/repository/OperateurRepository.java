package com.example.demo.repository;

import com.example.demo.entity.Operateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperateurRepository extends JpaRepository<Operateur, Integer> {
    // Les méthodes findAll, findById, save, delete sont déjà fournies par JpaRepository
}