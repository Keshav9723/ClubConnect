package com.clubconnect.clubservice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.clubconnect.clubservice.dto.ClubDTO;
import com.clubconnect.clubservice.service.ClubService;

@RestController
public class ClubController {

    ClubService _ClubService;

    @Autowired
    public ClubController(ClubService clubService) {
        this._ClubService = clubService;
    }
    @GetMapping("/")
    public Map<String, String> home() {
        try {
            Map<String, String> response = new HashMap<>();
            response.put("service", "Club Service");
            response.put("port", "8081");
            response.put("status", "running");
            return response;
        } catch (Exception e) {
            System.out.println("Error in home endpoint: " + e.getMessage());
            throw new RuntimeException("Error in home endpoint", e);
        }
    }

    @GetMapping("/clubs")
    public List<ClubDTO> getClubs() {
        try {
            return _ClubService.GetAllClubs();
        } catch (Exception e) {
            System.out.println("Error fetching all clubs: " + e.getMessage());
            throw new RuntimeException("Error fetching all clubs", e);
        }
    }

    @GetMapping("/clubs/{id}")
    public ResponseEntity<ClubDTO> getClubById(@PathVariable("id") int id) {
        try {
            ClubDTO club = _ClubService.GetClubById(id);
            if (club != null) {
                return ResponseEntity.ok(club);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.out.println("Error fetching club by ID: " + e.getMessage());
            throw new RuntimeException("Error fetching club by ID", e);
        }
    }

    @GetMapping("/clubs/name/{name}")
    public ResponseEntity<ClubDTO> getClubByName(@PathVariable("name") String name) {
        try {
            ClubDTO club = _ClubService.GetClubByName(name);
            if (club != null) {
                return ResponseEntity.ok(club);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.out.println("Error fetching club by name: " + e.getMessage());
            throw new RuntimeException("Error fetching club by name", e);
        }
    }

    @GetMapping("/clubs/validate/{name}")
    public Map<String, Object> validateClubExists(@PathVariable("name") String name) {
        try {
            boolean exists = _ClubService.ValidateClubExists(name);
            Map<String, Object> response = new HashMap<>();
            response.put("exists", exists);
            return response;
        } catch (Exception e) {
            System.out.println("Error validating club: " + e.getMessage());
            throw new RuntimeException("Error validating club", e);
        }
    }

    @PostMapping("/clubs")
    public ClubDTO createClub(@RequestBody ClubDTO clubDTO) {
        try {
            return _ClubService.CreateClub(clubDTO);
        } catch (Exception e) {
            System.out.println("Error creating club: " + e.getMessage());
            throw new RuntimeException("Error creating club", e);
        }
    }

    @PutMapping("/clubs/{id}")
    public ClubDTO updateClub(@PathVariable("id") int id, @RequestBody ClubDTO clubDTO) {
        try {
            return _ClubService.UpdateClub(id, clubDTO);
        } catch (Exception e) {
            System.out.println("Error updating club: " + e.getMessage());
            throw new RuntimeException("Error updating club", e);
        }
    }

    @DeleteMapping("/clubs/{id}")
    public Map<String, String> deleteClub(@PathVariable("id") int id) {
        try {
            _ClubService.DeleteClub(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Club deleted successfully");
            return response;
        } catch (Exception e) {
            System.out.println("Error deleting club: " + e.getMessage());
            throw new RuntimeException("Error deleting club", e);
        }
    }


    @GetMapping("/clubs/{name}/statistics")
    public Map<String, Object> getClubStatistics(@PathVariable("name") String name) {
        try {
            return _ClubService.GetClubStatistics(name);
        } catch (Exception e) {
            System.out.println("Error fetching club statistics: " + e.getMessage());
            throw new RuntimeException("Error fetching club statistics", e);
        }
    }
}