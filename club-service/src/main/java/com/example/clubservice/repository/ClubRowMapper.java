package com.example.clubservice.repository;


import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.clubservice.model.Club;

public class ClubRowMapper implements RowMapper<Club> {
    @Override
    public Club mapRow(ResultSet rs, int rowNum) throws SQLException {
        Club club = new Club();
        club.set_Id(rs.getInt("id"));
        club.set_Name(rs.getString("name"));
        club.set_Description(rs.getString("description"));
        club.set_Category(rs.getString("category"));
        return club;
    }
}
