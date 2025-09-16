package com.example.clubservice.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.example.clubservice.dto.ClubDTO;
import com.example.clubservice.model.Club;
import com.example.clubservice.repository.ClubRepository;

@Service
public class ClubServiceImpl implements ClubService {

    private final ClubRepository clubRepository;
    private final ModelMapper modelMapper;
    private final RestClient restClient; 

    private final String memberServiceUrl = "http://localhost:8082";
    private final String eventServiceUrl = "http://localhost:8083";

    @Autowired
    public ClubServiceImpl(ClubRepository clubRepository, ModelMapper modelMapper, RestClient restClient) {
        this.clubRepository = clubRepository;
        this.modelMapper = modelMapper;
        this.restClient = restClient; 
    }

    // ... (getAllClubs, getClubById, etc. remain the same) ...

    @Override
    public List<ClubDTO> getAllClubs() {
        return clubRepository.findAll()
                .stream()
                .map(club -> modelMapper.map(club, ClubDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ClubDTO> getClubById(int id) {
        return clubRepository.findById(id).map(c -> modelMapper.map(c, ClubDTO.class));
    }

    @Override
    public Optional<ClubDTO> getClubByName(String name) {
        return clubRepository.findByName(name).map(c -> modelMapper.map(c, ClubDTO.class));
    }

    @Override
    public boolean validateClubExists(String name) {
        return clubRepository.existsByName(name);
    }

    @Override
    public ClubDTO createClub(ClubDTO clubDTO) {
        Club club = modelMapper.map(clubDTO, Club.class);
        Club savedClub = clubRepository.save(club);
        return modelMapper.map(savedClub, ClubDTO.class);
    }
    
    @Override
    public ClubDTO updateClub(int id, ClubDTO clubDTO) {
        Club club = modelMapper.map(clubDTO, Club.class);
        club.set_Id(id);
        Club updatedClub = clubRepository.update(club);
        return modelMapper.map(updatedClub, ClubDTO.class);
    }
    
    @Override
    public void deleteClub(int id) {
        clubRepository.deleteById(id);
    }


    @Override
    public Map<String, Object> getClubStatistics(String name) {
        Map<String, Object> stats = new HashMap<>();
        if (!clubRepository.existsByName(name)) {
            stats.put("status", "club_not_found");
            return stats;
        }

        stats.put("clubName", name);

   
        try {
            // Assumes MemberService returns a Map like {"memberCount": 50}
            Map<String, Integer> memberStats = restClient.get()
                    .uri(memberServiceUrl + "/members/club/{clubName}/statistics", name)
                    .retrieve()
                    .body(Map.class);
            stats.put("memberCount", memberStats.getOrDefault("memberCount", 0));
        } catch (Exception e) {
            stats.put("memberCount", "Error: MemberService unavailable");
        }

        
        try {
            // Assumes EventService returns a Map like {"eventCount": 10}
            Map<String, Integer> eventStats = restClient.get()
                    .uri(eventServiceUrl + "/events/club/{clubName}/statistics", name)
                    .retrieve()
                    .body(Map.class);
            stats.put("eventCount", eventStats.getOrDefault("eventCount", 0));
        } catch (Exception e) {
            stats.put("eventCount", "Error: EventService unavailable");
        }
        
        stats.put("status", "data_retrieved");
        return stats;
    }
}