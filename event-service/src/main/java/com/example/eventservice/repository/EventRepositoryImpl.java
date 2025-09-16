package com.example.eventservice.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.eventservice.model.Event;

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
    public Optional<Event> findById(long id) {
        String sql = "SELECT * FROM events WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new EventRowMapper(), id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Event> findByClubId(int clubId) {
        String sql = "SELECT * FROM events WHERE club_id = ?";
        return jdbcTemplate.query(sql, new EventRowMapper(), clubId);
    }

    @Override
    public List<Event> findUpcoming() {
        // NOW() is a standard SQL function for the current timestamp
        String sql = "SELECT * FROM events WHERE date_time > NOW() ORDER BY date_time ASC";
        return jdbcTemplate.query(sql, new EventRowMapper());
    }

    @Override
    public Event save(Event event) {
        String sql = "INSERT INTO events (name, description, location, date_time, club_id) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, event.get_Name(), event.get_Description(), event.get_Location(), event.get_DateTime(), event.get_ClubId());
        return event;
    }

    @Override
    public Event update(Event event) {
        String sql = "UPDATE events SET name = ?, description = ?, location = ?, date_time = ?, club_id = ? WHERE id = ?";
        jdbcTemplate.update(sql, event.get_Name(), event.get_Description(), event.get_Location(), event.get_DateTime(), event.get_ClubId(), event.get_Id());
        return event;
    }

    @Override
    public void deleteById(long id) {
        String sql = "DELETE FROM events WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}