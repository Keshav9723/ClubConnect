package com.clubconnect.memberservice.repository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.clubconnect.memberservice.model.Member;

@Repository
public class MemberRepositoryImpl implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MemberRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Member> findAll() {
        String sql = "SELECT * FROM members";
        return jdbcTemplate.query(sql, new MemberRowMapper());
    }

    @Override
    public Member findById(long id) {
        String sql = "SELECT * FROM members WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new MemberRowMapper(), id);
        } catch (Exception e) {
            System.out.println("Error fetching member by ID " + id + ": " + e.getMessage());
            return null;
        }
    }

    @Override
    public Member findByEmail(String email) {
        String sql = "SELECT * FROM members WHERE email = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new MemberRowMapper(), email);
        } catch (Exception e) {
            System.out.println("Error fetching member by email " + email + ": " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Member> findByClubId(int clubId) {
        String sql = "SELECT * FROM members WHERE club_id = ?";
        return jdbcTemplate.query(sql, new MemberRowMapper(), clubId);
    }

    @Override
    public Member save(Member member) {
        long newId = this.GetMemberCount() + 1;
        member.set_Id(newId);

        String sql = "INSERT INTO members (id, name, email, phone, club_id) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, member.get_Id(), member.get_Name(), member.get_Email(), member.get_Phone(), member.get_ClubId());
        
        return member;
    }

    @Override
    public Integer GetMemberCount() {
        try {
            String sql = "SELECT COUNT(*) FROM members";
            return jdbcTemplate.queryForObject(sql, Integer.class);
        } catch (Exception e) {
            System.out.println("Error fetching member count: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public Member update(Member member) {
        String sql = "UPDATE members SET name = ?, email = ?, phone = ?, club_id = ? WHERE id = ?";
        jdbcTemplate.update(sql, member.get_Name(), member.get_Email(), member.get_Phone(), member.get_ClubId(), member.get_Id());
        return member;
    }

    @Override
    public boolean deleteById(long id) {
        String sql = "DELETE FROM members WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }
}