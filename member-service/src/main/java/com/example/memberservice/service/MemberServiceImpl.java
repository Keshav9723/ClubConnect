package com.example.memberservice.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.example.memberservice.dto.MemberDTO;
import com.example.memberservice.model.Member;
import com.example.memberservice.repository.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;
    private final RestClient restClient;
    private final String clubServiceUrl = "http://localhost:8081";
    private final String registrationServiceUrl = "http://localhost:8084";

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository, ModelMapper modelMapper, RestClient restClient) {
        this.memberRepository = memberRepository;
        this.modelMapper = modelMapper;
        this.restClient = restClient;
    }

    private MemberDTO toDto(Member member) {
        return modelMapper.map(member, MemberDTO.class);
    }

    private Member toEntity(MemberDTO dto) {
        return modelMapper.map(dto, Member.class);
    }

    @Override
    public List<MemberDTO> getAllMembers() {
        return memberRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public Optional<MemberDTO> getMemberById(long id) {
        return memberRepository.findById(id).map(this::toDto);
    }

    @Override
    public Optional<MemberDTO> getMemberByEmail(String email) {
        return memberRepository.findByEmail(email).map(this::toDto);
    }

    @Override
    public List<MemberDTO> getMembersByClub(String clubName) {
        try {
            Map<String, Object> club = restClient.get()
                .uri(clubServiceUrl + "/clubs/name/" + clubName)
                .retrieve()
                .body(Map.class);
            
            if (club != null && club.containsKey("_Id")) {
                int clubId = (Integer) club.get("_Id");
                return memberRepository.findByClubId(clubId).stream().map(this::toDto).collect(Collectors.toList());
            }
        } catch (Exception e) {
            return Collections.emptyList();
        }
        return Collections.emptyList();
    }

    @Override
    public MemberDTO createMember(MemberDTO memberDTO) {
        return toDto(memberRepository.save(toEntity(memberDTO)));
    }

    @Override
    public MemberDTO updateMember(long id, MemberDTO memberDTO) {
        Member member = toEntity(memberDTO);
        member.set_Id(id);
        return toDto(memberRepository.update(member));
    }

    @Override
    public void deleteMember(long id) {
        memberRepository.deleteById(id);
    }

    @Override
    public Map<String, Object> getMemberStatistics(long id) {
        Map<String, Object> stats = new HashMap<>();
        Optional<Member> member = memberRepository.findById(id);

        if (member.isEmpty()) {
            stats.put("error", "Member not found");
            return stats;
        }
        stats.put("memberName", member.get().get_Name());
        
        try {
            // Assumes a GET endpoint like /registrations/member/{id}/statistics
            Map<String, Object> registrationStats = restClient.get()
                .uri(registrationServiceUrl + "/registrations/member/" + id + "/statistics")
                .retrieve()
                .body(Map.class);
            
            stats.put("registeredEventsCount", registrationStats.getOrDefault("count", 0));
        } catch (Exception e) {
            stats.put("registeredEventsCount", "Error fetching data");
        }
        
        return stats;
    }
}