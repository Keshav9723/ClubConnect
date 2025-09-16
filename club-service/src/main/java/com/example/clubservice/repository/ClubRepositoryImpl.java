package com.example.clubservice.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.clubservice.model.Club;

@Repository
public class ClubRepositoryImpl implements ClubRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ClubRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Club> findAll() {
        String sql = "SELECT * FROM clubs";
        return jdbcTemplate.query(sql, new ClubRowMapper());
    }

    @Override
    public Optional<Club> findById(int id) {
        String sql = "SELECT * FROM clubs WHERE id = ?";
        try {
            Club club = jdbcTemplate.queryForObject(sql, new ClubRowMapper(), id);
            return Optional.ofNullable(club);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Club> findByName(String name) {
        String sql = "SELECT * FROM clubs WHERE name = ?";
        try {
            Club club = jdbcTemplate.queryForObject(sql, new ClubRowMapper(), name);
            return Optional.ofNullable(club);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean existsByName(String name) {
        String sql = "SELECT count(*) FROM clubs WHERE name = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, name);
        return count != null && count > 0;
    }

    @Override
    public Club save(Club club) {
        String sql = "INSERT INTO clubs (name, description, category) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, club.get_Name(), club.get_Description(), club.get_Category(),  club.get_Id());
        return club;
    }

    @Override
    public Club update(Club club) {
        String sql = "UPDATE clubs SET name = ?, description = ?, category = ? WHERE id = ?";
        jdbcTemplate.update(sql, club.get_Name(), club.get_Description(), club.get_Category(), club.get_Id());
        return club;
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM clubs WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}