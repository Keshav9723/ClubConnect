package com.example.memberservice.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.memberservice.model.Member;

public class MemberRowMapper implements RowMapper<Member> {
    @Override
    public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
        Member member = new Member();
        member.set_Id(rs.getLong("id"));
        member.set_Name(rs.getString("name"));
        member.set_Email(rs.getString("email"));
        member.set_Phone(rs.getString("phone"));
        member.set_ClubId(rs.getInt("club_id"));
        return member;
    }
}