package com.clubconnect.memberservice.repository;

import java.util.List;
import java.util.Optional;

import com.clubconnect.memberservice.model.Member;

public interface MemberRepository {
    List<Member> findAll();
    Optional<Member> findById(long id);
    Optional<Member> findByEmail(String email);
    List<Member> findByClubId(int clubId);
    Member save(Member member);
    Member update(Member member);
    void deleteById(long id);
    Integer GetMemberCount(); 
}