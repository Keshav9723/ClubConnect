package com.example.memberservice.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.memberservice.dto.MemberDTO;

public interface MemberService {
    List<MemberDTO> getAllMembers();
    Optional<MemberDTO> getMemberById(long id);
    Optional<MemberDTO> getMemberByEmail(String email);
    List<MemberDTO> getMembersByClub(String clubName);
    MemberDTO createMember(MemberDTO memberDTO);
    MemberDTO updateMember(long id, MemberDTO memberDTO);
    void deleteMember(long id);
    Map<String, Object> getMemberStatistics(long id);
}