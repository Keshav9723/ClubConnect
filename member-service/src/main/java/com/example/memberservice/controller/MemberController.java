package com.example.memberservice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.memberservice.dto.MemberDTO;
import com.example.memberservice.service.MemberService;

@RestController
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/")
    public Map<String, String> home() {
        Map<String, String> response = new HashMap<>();
        response.put("service", "Member Service");
        response.put("port", "8082");
        response.put("status", "running");
        return response;
    }

    @GetMapping("/members")
    public List<MemberDTO> getMembers() {
        return memberService.getAllMembers();
    }

    @GetMapping("/members/{id}")
    public MemberDTO getMemberById(@PathVariable long id) {
        return memberService.getMemberById(id).orElse(null);
    }

    @GetMapping("/members/email/{email}")
    public MemberDTO getMemberByEmail(@PathVariable String email) {
        return memberService.getMemberByEmail(email).orElse(null);
    }

    @GetMapping("/members/club/{clubName}")
    public List<MemberDTO> getMembersByClub(@PathVariable String clubName) {
        return memberService.getMembersByClub(clubName);
    }

    @PostMapping("/members")
    public MemberDTO createMember(@RequestBody MemberDTO memberDTO) {
        return memberService.createMember(memberDTO);
    }



    @PutMapping("/members/{id}")
    public MemberDTO updateMember(@PathVariable long id, @RequestBody MemberDTO memberDTO) {
        return memberService.updateMember(id, memberDTO);
    }

    @DeleteMapping("/members/{id}")
    public Map<String, String> deleteMember(@PathVariable long id) {
        memberService.deleteMember(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Member deleted successfully");
        return response;
    }

    @GetMapping("/members/{id}/statistics")
    public Map<String, Object> getMemberStatistics(@PathVariable long id) {
        return memberService.getMemberStatistics(id);
    }
}