package com.clubconnect.clubservice.repository;

import java.util.List;
import java.util.Optional;

import com.clubconnect.clubservice.model.Club;

public interface ClubRepository {
    List<Club> findAll();
    Club findById(int id);
    Club findByName(String name);
    boolean existsByName(String name);
    Club save(Club club);
    Club update(Club club);
    boolean deleteById(int id);
    Integer GetClubCount();
}