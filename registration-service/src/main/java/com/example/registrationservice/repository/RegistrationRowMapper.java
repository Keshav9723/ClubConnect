package com.example.registrationservice.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.registrationservice.model.Registration;

public class RegistrationRowMapper implements RowMapper<Registration> {
    @Override
    public Registration mapRow(ResultSet rs, int rowNum) throws SQLException {
        Registration registration = new Registration();
        registration.set_Id(rs.getLong("id"));
        registration.set_MemberId(rs.getLong("member_id"));
        registration.set_EventId(rs.getLong("event_id"));
        registration.set_RegistrationDate(rs.getTimestamp("registration_date").toLocalDateTime());
        registration.set_Status(rs.getString("status"));
        registration.set_MemberName(rs.getString("member_name"));
        registration.set_EventName(rs.getString("event_name"));
        return registration;
    }
}