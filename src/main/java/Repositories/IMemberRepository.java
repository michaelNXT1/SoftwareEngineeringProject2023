package Repositories;

import BusinessLayer.Member;
import BusinessLayer.Position;

import java.util.List;
import java.util.Map;

public interface IMemberRepository {
    void addMember(Member member);
    void removeMember(Member member);
    Member getMember(String key);
    List<Member> getAllMember();

    // Add other methods as needed
}