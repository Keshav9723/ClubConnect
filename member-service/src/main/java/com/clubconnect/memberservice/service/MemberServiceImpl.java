package com.clubconnect.memberservice.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.clubconnect.memberservice.dto.MemberDTO;
import com.clubconnect.memberservice.model.Member;
import com.clubconnect.memberservice.repository.MemberRepository;

@Service(value = "MemberService")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class MemberServiceImpl implements MemberService {

    MemberRepository _MemberRepository;
    ModelMapper _ModelMapper;
    RestClient _RestClient;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository, ModelMapper modelMapper, RestClient restClient) {
        this._MemberRepository = memberRepository;
        this._ModelMapper = modelMapper;
        this._RestClient = restClient;
    }

    @Override
    public List<MemberDTO> GetAllMembers() {
        List<Member> members = _MemberRepository.findAll();
        List<MemberDTO> memberDtos = new ArrayList<>();
        for (Member member : members) {
            memberDtos.add(_ModelMapper.map(member, MemberDTO.class));
        }
        return memberDtos;
    }

    @Override
    public MemberDTO GetMemberById(long id) {
        Member member = _MemberRepository.findById(id);
        if (member != null) {
            return _ModelMapper.map(member, MemberDTO.class);
        }
        return null;
    }

    @Override
    public MemberDTO GetMemberByEmail(String email) {
        Member member = _MemberRepository.findByEmail(email);
        if (member != null) {
            return _ModelMapper.map(member, MemberDTO.class);
        }
        return null;
    }

    @Override
    public List<MemberDTO> GetMembersByClub(String clubName) {
        try {
            Map<String, Object> club = _RestClient.get()
                    .uri("lb://club-service/clubs/name/{clubName}", clubName)
                    .retrieve()
                    .body(Map.class);
            
            if (club != null && club.containsKey("id")) {
                int clubId = (Integer) club.get("id");
                List<Member> members = _MemberRepository.findByClubId(clubId);
                List<MemberDTO> memberDtos = new ArrayList<>();
                for (Member member : members) {
                    memberDtos.add(_ModelMapper.map(member, MemberDTO.class));
                }
                return memberDtos;
            }
        } catch (Exception e) {
            return Collections.emptyList();
        }
        return Collections.emptyList();
    }

    @Override
    public MemberDTO CreateMember(MemberDTO memberDTO) {
        Member member = _ModelMapper.map(memberDTO, Member.class);
        Member savedMember = _MemberRepository.save(member);
        return _ModelMapper.map(savedMember, MemberDTO.class);
    }

    @Override
    public MemberDTO UpdateMember(long id, MemberDTO memberDTO) {
        Member member = _ModelMapper.map(memberDTO, Member.class);
        member.set_Id(id);
        Member updatedMember = _MemberRepository.update(member);
        return _ModelMapper.map(updatedMember, MemberDTO.class);
    }

    @Override
    public boolean DeleteMember(long id) {
        return _MemberRepository.deleteById(id);
    }

    @Override
    public Map<String, Object> GetMemberStatistics(long id) {
        Map<String, Object> stats = new HashMap<>();
        Member member = _MemberRepository.findById(id);

        if (member == null) {
            stats.put("error", "Member not found");
            return stats;
        }
        stats.put("memberName", member.get_Name());
        
        try {
            Map<String, Object> registrationStats = _RestClient.get()
                    .uri("lb://registration-service/registrations/member/{id}/statistics", id)
                    .retrieve()
                    .body(Map.class);
            
            stats.put("registeredEventsCount", registrationStats.getOrDefault("count", 0));
        } catch (Exception e) {
            stats.put("registeredEventsCount", "Error fetching data");
        }
        
        return stats;
    }
}