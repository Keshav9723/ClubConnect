package com.clubconnect.memberservice.repository;

import com.clubconnect.memberservice.model.Member;
import java.util.List;

public interface MemberRepository {

    List<Member> findAll();
    Member findById(long id);
    Member findByEmail(String email);
    List<Member> findByClubId(int clubId);
    Member save(Member member);
    Integer GetMemberCount();
    Member update(Member member);
    boolean deleteById(long id);
}