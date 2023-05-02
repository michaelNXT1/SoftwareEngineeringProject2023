package org.example.ServiceLayer.DTOs;

import org.example.BusinessLayer.Member;

public class MemberDTO {
    private String username;
    private String email;
    private String hashedPassword;

    public MemberDTO(String username, String email, String hashedPassword) {
        this.username = username;
        this.email = email;
        this.hashedPassword = hashedPassword;
    }

    public static MemberDTO fromMemberToMemberDTO(Member member){
        return new MemberDTO(member.getUsername(),member.getEmail(), member.getPassword());
    }
}
