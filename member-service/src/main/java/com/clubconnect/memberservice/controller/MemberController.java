package com.clubconnect.memberservice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.clubconnect.memberservice.dto.MemberDTO;
import com.clubconnect.memberservice.service.MemberService;

@RestController
public class MemberController {

    MemberService _MemberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this._MemberService = memberService;
    }

    @GetMapping("/")
    public Map<String, String> home() {
        try {
            Map<String, String> response = new HashMap<>();
            response.put("service", "Member Service");
            response.put("port", "8082");
            response.put("status", "running");
            return response;
        } catch (Exception e) {
            System.out.println("Error in home endpoint: " + e.getMessage());
            throw new RuntimeException("Error in home endpoint", e);
        }
    }

    @GetMapping("/members")
    public List<MemberDTO> getMembers() {
        try {
            return _MemberService.GetAllMembers();
        } catch (Exception e) {
            System.out.println("Error fetching all members: " + e.getMessage());
            throw new RuntimeException("Error fetching all members", e);
        }
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<MemberDTO> getMemberById(@PathVariable("id") long id) {
        try {
            MemberDTO member = _MemberService.GetMemberById(id);
            if (member != null) {
                return ResponseEntity.ok(member);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.out.println("Error fetching member by ID: " + e.getMessage());
            throw new RuntimeException("Error fetching member by ID", e);
        }
    }

    @GetMapping("/members/email/{email}")
    public ResponseEntity<MemberDTO> getMemberByEmail(@PathVariable("email") String email) {
        try {
            MemberDTO member = _MemberService.GetMemberByEmail(email);
            if (member != null) {
                return ResponseEntity.ok(member);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.out.println("Error fetching member by email: " + e.getMessage());
            throw new RuntimeException("Error fetching member by email", e);
        }
    }

    @GetMapping("/members/club/{clubName}")
    public List<MemberDTO> getMembersByClub(@PathVariable("clubName") String clubName) {
        try {
            return _MemberService.GetMembersByClub(clubName);
        } catch (Exception e) {
            System.out.println("Error fetching members by club: " + e.getMessage());
            throw new RuntimeException("Error fetching members by club", e);
        }
    }

    @PostMapping("/members")
    public MemberDTO createMember(@RequestBody MemberDTO memberDTO) {
        try {
            return _MemberService.CreateMember(memberDTO);
        } catch (Exception e) {
            System.out.println("Error creating member: " + e.getMessage());
            throw new RuntimeException("Error creating member", e);
        }
    }

    @PutMapping("/members/{id}")
    public MemberDTO updateMember(@PathVariable("id") long id, @RequestBody MemberDTO memberDTO) {
        try {
            return _MemberService.UpdateMember(id, memberDTO);
        } catch (Exception e) {
            System.out.println("Error updating member: " + e.getMessage());
            throw new RuntimeException("Error updating member", e);
        }
    }

    @DeleteMapping("/members/{id}")
    public Map<String, Object> deleteMember(@PathVariable("id") long id) {
        try {
            boolean deleted = _MemberService.DeleteMember(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", deleted);
            response.put("message", deleted ? "Member deleted successfully" : "Member not found or could not be deleted");
            return response;
        } catch (Exception e) {
            System.out.println("Error deleting member: " + e.getMessage());
            throw new RuntimeException("Error deleting member", e);
        }
    }

    @GetMapping("/members/{id}/statistics")
    public Map<String, Object> getMemberStatistics(@PathVariable("id") long id) {
        try {
            return _MemberService.GetMemberStatistics(id);
        } catch (Exception e) {
            System.out.println("Error fetching member statistics: " + e.getMessage());
            throw new RuntimeException("Error fetching member statistics", e);
        }
    }
}