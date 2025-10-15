package com.clubconnect.clubservice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.clubconnect.clubservice.dto.ClubDTO;
import com.clubconnect.clubservice.model.Club;
import com.clubconnect.clubservice.repository.ClubRepository;

@Service(value = "ClubService")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class ClubServiceImpl implements ClubService {

    ClubRepository _ClubRepository;
    ModelMapper _ModelMapper;
    RestClient _RestClient;

    @Autowired
    public ClubServiceImpl(ClubRepository clubRepository, ModelMapper modelMapper, RestClient restClient) {
        this._ClubRepository = clubRepository;
        this._ModelMapper = modelMapper;
        this._RestClient = restClient;
    }

    @Override
    public List<ClubDTO> GetAllClubs() {
        List<Club> clubs = _ClubRepository.findAll();
        List<ClubDTO> clubDtos = new ArrayList<>();
        for (Club club : clubs) {
            clubDtos.add(_ModelMapper.map(club, ClubDTO.class));
        }
        return clubDtos;
    }


    @Override
    public ClubDTO GetClubById(int id) {
        Club club = _ClubRepository.findById(id);
        if (club != null) {
            return _ModelMapper.map(club, ClubDTO.class);
        }
        return null;
    }


    @Override
    public ClubDTO GetClubByName(String name) {
     
        Club club = _ClubRepository.findByName(name);
        if (club != null) {
            return _ModelMapper.map(club, ClubDTO.class);
        }
        return null;
    }

    @Override
    public boolean ValidateClubExists(String name) {
        return _ClubRepository.existsByName(name);
    }

    @Override
    public ClubDTO CreateClub(ClubDTO clubDTO) {
        Club club = _ModelMapper.map(clubDTO, Club.class);
        Club savedClub = _ClubRepository.save(club);
        return _ModelMapper.map(savedClub, ClubDTO.class);
    }

    @Override
    public ClubDTO UpdateClub(int id, ClubDTO clubDTO) {
        Club club = _ModelMapper.map(clubDTO, Club.class);
        club.set_Id(id);
        Club updatedClub = _ClubRepository.update(club);
        return _ModelMapper.map(updatedClub, ClubDTO.class);
    }

    @Override
    public boolean DeleteClub(int id) {
        return _ClubRepository.deleteById(id);
    }

    @Override
    public Map<String, Object> GetClubStatistics(String name) {
        Map<String, Object> stats = new HashMap<>();
        if (!_ClubRepository.existsByName(name)) {
            stats.put("status", "club_not_found");
            return stats;
        }

        stats.put("clubName", name);
        
        FetchStat(stats, "lb://member-service", "members", "memberCount", name);
        FetchStat(stats, "lb://event-service", "events", "eventCount", name);

        stats.put("status", "data_retrieved");
        return stats;
    }

    private void FetchStat(Map<String, Object> stats, String serviceName, String path, String key, String clubName) {
        try {
            String url = serviceName + "/" + path + "/club/{clubName}/statistics";
            
            Map<String, Integer> response = _RestClient.get()
                    .uri(url, clubName)
                    .retrieve()
                    .body(Map.class);
            
            stats.put(key, response != null ? response.getOrDefault(key, 0) : 0);

        } catch (Exception e) {
            stats.put(key, "Error: " + serviceName + " unavailable");
        }
    }
}