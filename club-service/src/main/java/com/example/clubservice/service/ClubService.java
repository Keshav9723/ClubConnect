package com.example.clubservice.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.clubservice.dto.ClubDTO;

public interface ClubService {
    List<ClubDTO> getAllClubs();
    Optional<ClubDTO> getClubById(int id);
    Optional<ClubDTO> getClubByName(String name);
    boolean validateClubExists(String name);
    ClubDTO createClub(ClubDTO clubDTO);
    ClubDTO updateClub(int id, ClubDTO clubDTO);
    void deleteClub(int id);
    Map<String, Object> getClubStatistics(String name);
}