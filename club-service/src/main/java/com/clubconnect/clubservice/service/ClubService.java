package com.clubconnect.clubservice.service;

import com.clubconnect.clubservice.dto.ClubDTO;

import java.util.List;
import java.util.Map;

public interface ClubService {

    List<ClubDTO> GetAllClubs();
    ClubDTO GetClubById(int id);
    ClubDTO GetClubByName(String name);
    boolean ValidateClubExists(String name);
    ClubDTO CreateClub(ClubDTO clubDTO);
    ClubDTO UpdateClub(int id, ClubDTO clubDTO);
    boolean DeleteClub(int id);
    Map<String, Object> GetClubStatistics(String name);
}