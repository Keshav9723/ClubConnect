package com.clubconnect.registrationservice.repository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.clubconnect.registrationservice.model.Registration;

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
    public Registration findById(long id) {
        String sql = "SELECT * FROM registrations WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new RegistrationRowMapper(), id);
        } catch (Exception e) {
            System.out.println("Error fetching registration by ID " + id + ": " + e.getMessage());
            return null;
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
    public Integer GetRegistrationCount() {
        try {
            String sql = "SELECT COUNT(*) FROM registrations";
            return jdbcTemplate.queryForObject(sql, Integer.class);
        } catch (Exception e) {
            System.out.println("Error fetching registration count: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public Registration save(Registration reg) {
        long newId = this.GetRegistrationCount() + 1;
        reg.set_Id(newId);
        String sql = "INSERT INTO registrations (id, member_id, event_id, registration_date, status, member_name, event_name) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, reg.get_Id(), reg.get_MemberId(), reg.get_EventId(), reg.get_RegistrationDate(), reg.get_Status(), reg.get_MemberName(), reg.get_EventName());
        return reg;
    }

    @Override
    public boolean deleteById(long id) {
        String sql = "DELETE FROM registrations WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    @Override
    public boolean deleteByMemberIdAndEventId(long memberId, long eventId) {
        String sql = "DELETE FROM registrations WHERE member_id = ? AND event_id = ?";
        return jdbcTemplate.update(sql, memberId, eventId) > 0;
    }

    @Override
    public Registration updateStatus(long id, String status) {
        String sql = "UPDATE registrations SET status = ? WHERE id = ?";
        int updatedRows = jdbcTemplate.update(sql, status, id);
        return updatedRows > 0 ? this.findById(id) : null;
    }

    @Override
    public long count() {
        String sql = "SELECT count(*) FROM registrations";
        Long count = jdbcTemplate.queryForObject(sql, Long.class);
        return count != null ? count : 0;
    }
}