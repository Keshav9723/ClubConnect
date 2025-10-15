package com.clubconnect.memberservice.service;

import com.clubconnect.memberservice.dto.MemberDTO;
import java.util.List;
import java.util.Map;

public interface MemberService {

    List<MemberDTO> GetAllMembers();
    MemberDTO GetMemberById(long id);
    MemberDTO GetMemberByEmail(String email);
    List<MemberDTO> GetMembersByClub(String clubName);
    MemberDTO CreateMember(MemberDTO memberDTO);
    MemberDTO UpdateMember(long id, MemberDTO memberDTO);
    boolean DeleteMember(long id);
    Map<String, Object> GetMemberStatistics(long id);
}