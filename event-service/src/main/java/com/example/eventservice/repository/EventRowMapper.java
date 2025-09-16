package com.example.eventservice.repository;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.eventservice.model.Event;

public class EventRowMapper implements RowMapper<Event> {
    @Override
    public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
        Event event = new Event();
        event.set_Id(rs.getLong("id"));
        event.set_Name(rs.getString("name"));
        event.set_Description(rs.getString("description"));
        event.set_Location(rs.getString("location"));
        event.set_DateTime(rs.getTimestamp("date_time").toLocalDateTime());
        event.set_ClubId(rs.getInt("club_id"));
        return event;
    }
}