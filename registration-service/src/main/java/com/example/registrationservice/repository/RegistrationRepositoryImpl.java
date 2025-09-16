package com.example.registrationservice.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.registrationservice.model.Registration;

@Repository
public class RegistrationRepositoryImpl implements RegistrationRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RegistrationRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Registration> findAll() {
        String sql = "SELECT * FROM registrations";
        return jdbcTemplate.query(sql, new RegistrationRowMapper());
    }

    @Override
    public Optional<Registration> findById(long id) {
        String sql = "SELECT * FROM registrations WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new RegistrationRowMapper(), id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Registration> findByMemberId(long memberId) {
        String sql = "SELECT * FROM registrations WHERE member_id = ?";
        return jdbcTemplate.query(sql, new RegistrationRowMapper(), memberId);
    }

    @Override
    public List<Registration> findByEventId(long eventId) {
        String sql = "SELECT * FROM registrations WHERE event_id = ?";
        return jdbcTemplate.query(sql, new RegistrationRowMapper(), eventId);
    }

    @Override
    public Registration save(Registration reg) {
        String sql = "INSERT INTO registrations (member_id, event_id, registration_date, status, member_name, event_name) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, reg.get_MemberId(), reg.get_EventId(), reg.get_RegistrationDate(), reg.get_Status(), reg.get_MemberName(), reg.get_EventName());
        return reg;
    }

    @Override
    public void deleteById(long id) {
        String sql = "DELETE FROM registrations WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public int deleteByMemberIdAndEventId(long memberId, long eventId) {
        String sql = "DELETE FROM registrations WHERE member_id = ? AND event_id = ?";
        return jdbcTemplate.update(sql, memberId, eventId);
    }

    @Override
    public Optional<Registration> updateStatus(long id, String status) {
        String sql = "UPDATE registrations SET status = ? WHERE id = ?";
        int updatedRows = jdbcTemplate.update(sql, status, id);
        return updatedRows > 0 ? findById(id) : Optional.empty();
    }

    @Override
    public long count() {
        String sql = "SELECT count(*) FROM registrations";
        Long count = jdbcTemplate.queryForObject(sql, Long.class);
        return count != null ? count : 0;
    }
}