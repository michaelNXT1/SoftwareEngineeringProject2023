package ServiceLayer.DTOs;

import BusinessLayer.Member;

public class MemberDTO {
    private String username;
    private String email;
    private String hashedPassword;

    public MemberDTO(Member member) {
        this.username = member.getUsername();
        this.email = member.getEmail();
        this.hashedPassword = member.getPassword();
    }
}
