package com.example.clubservice.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.clubservice.dto.ClubDTO;
import com.example.clubservice.model.Club;
import com.example.clubservice.repository.ClubRepository;

@Service
public class ClubServiceImpl implements ClubService {

    private final ClubRepository clubRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ClubServiceImpl(ClubRepository clubRepository, ModelMapper modelMapper) {
        this.clubRepository = clubRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ClubDTO> getAllClubs() {
        return clubRepository.findAll()
                .stream()
                .map(club -> modelMapper.map(club, ClubDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ClubDTO> getClubById(int id) {
        Optional<Club> club = clubRepository.findById(id);
        return club.map(c -> modelMapper.map(c, ClubDTO.class));
    }

    @Override
    public Optional<ClubDTO> getClubByName(String name) {
        Optional<Club> club = clubRepository.findByName(name);
        return club.map(c -> modelMapper.map(c, ClubDTO.class));
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
        // Ensure the ID from the path is set on the object to be updated
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
        // This is where you would use RestClient or WebClient to call other services
        // For now, we return a mock response.
        Map<String, Object> stats = new HashMap<>();
        if (clubRepository.existsByName(name)) {
            stats.put("clubName", name);
            stats.put("memberCount", 150); // Mock data from MemberService
            stats.put("eventCount", 12);  // Mock data from EventService
            stats.put("status", "data_retrieved");
        } else {
            stats.put("status", "club_not_found");
        }
        return stats;
    }
}