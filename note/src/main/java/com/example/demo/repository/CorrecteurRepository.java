package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.Correcteur;

@Repository
public interface CorrecteurRepository extends JpaRepository<Correcteur, Integer>{

}
