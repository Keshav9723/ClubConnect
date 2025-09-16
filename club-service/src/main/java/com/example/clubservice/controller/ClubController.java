package com.example.clubservice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.clubservice.dto.ClubDTO;
import com.example.clubservice.service.ClubService;

@RestController
public class ClubController {

    @Autowired
    private ClubService clubService;

    @GetMapping("/")
    public Map<String, String> home() {
        Map<String, String> response = new HashMap<>();
        response.put("service", "Club Service");
        response.put("port", "8081");
        response.put("status", "running");
        return response;
    }

    @GetMapping("/clubs")
    public List<ClubDTO> getClubs() {
        return clubService.getAllClubs();
    }

    @GetMapping("/clubs/{id}")
    public ClubDTO getClub(@PathVariable int id) {
        Optional<ClubDTO> club = clubService.getClubById(id);
        return club.orElse(null); // Returns club if present, otherwise null
    }

    @GetMapping("/clubs/name/{name}")
    public ClubDTO getClubByName(@PathVariable String name) {
        Optional<ClubDTO> club = clubService.getClubByName(name);
        return club.orElse(null);
    }

    @GetMapping("/clubs/validate/{name}")
    public Map<String, Object> validateClub(@PathVariable String name) {
        boolean exists = clubService.validateClubExists(name);
        Map<String, Object> response = new HashMap<>();
        response.put("exists", exists);
        return response;
    }

    @PostMapping("/clubs")
    public ClubDTO createClub(@RequestBody ClubDTO clubDTO) {
        return clubService.createClub(clubDTO);
    }

    @PutMapping("/clubs/{id}")
    public ClubDTO updateClub(@PathVariable int id, @RequestBody ClubDTO clubDTO) {
        return clubService.updateClub(id, clubDTO);
    }

    @DeleteMapping("/clubs/{id}")
    public Map<String, String> deleteClub(@PathVariable int id) {
        clubService.deleteClub(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Club deleted successfully");
        return response;
    }

    @GetMapping("/clubs/{name}/statistics")
    public Map<String, Object> getClubStatistics(@PathVariable String name) {
        return clubService.getClubStatistics(name);
    }
}