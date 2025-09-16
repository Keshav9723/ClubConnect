package com.example.clubservice.repository;

import java.util.List;
import java.util.Optional;

import com.example.clubservice.model.Club;

public interface ClubRepository {
    List<Club> findAll();
    Optional<Club> findById(int id);
    Optional<Club> findByName(String name);
    boolean existsByName(String name);
    Club save(Club club);
    Club update(Club club);
    void deleteById(int id);
}