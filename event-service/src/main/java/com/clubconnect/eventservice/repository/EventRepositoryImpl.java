package com.clubconnect.eventservice.repository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.clubconnect.eventservice.model.Event;

@Repository
public class EventRepositoryImpl implements EventRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EventRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Event> findAll() {
        String sql = "SELECT * FROM events";
        return jdbcTemplate.query(sql, new EventRowMapper());
    }

    @Override
    public Event findById(long id) {
        String sql = "SELECT * FROM events WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new EventRowMapper(), id);
        } catch (Exception e) {
            System.out.println("Error fetching event by ID " + id + ": " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Event> findByClubId(int clubId) {
        String sql = "SELECT * FROM events WHERE club_id = ?";
        return jdbcTemplate.query(sql, new EventRowMapper(), clubId);
    }

    @Override
    public List<Event> findUpcoming() {
        String sql = "SELECT * FROM events WHERE date_time > NOW() ORDER BY date_time ASC";
        return jdbcTemplate.query(sql, new EventRowMapper());
    }

    @Override
    public Event save(Event event) {
        long newId = this.GetEventCount() + 1;
        event.set_Id(newId);

        String sql = "INSERT INTO events (id, name, description, location, date_time, club_id) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, event.get_Id(), event.get_Name(), event.get_Description(), event.get_Location(), event.get_DateTime(), event.get_ClubId());
        
        return event;
    }

    @Override
    public Integer GetEventCount() {
        try {
            String sql = "SELECT COUNT(*) FROM events";
            return jdbcTemplate.queryForObject(sql, Integer.class);
        } catch (Exception e) {
            System.out.println("Error fetching event count: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public Event update(Event event) {
        String sql = "UPDATE events SET name = ?, description = ?, location = ?, date_time = ?, club_id = ? WHERE id = ?";
        jdbcTemplate.update(sql, event.get_Name(), event.get_Description(), event.get_Location(), event.get_DateTime(), event.get_ClubId(), event.get_Id());
        return event;
    }

    @Override
    public boolean deleteById(long id) {
        String sql = "DELETE FROM events WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }
}