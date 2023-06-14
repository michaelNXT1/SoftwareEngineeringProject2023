package BusinessLayer.Repositories.Interfaces;

import BusinessLayer.Member;

import java.util.Map;

public interface IMemberRepository {
    void addMember(String key, Member member);
    void removeMember(String key);
    Member getMember(String key);
    Map<String, Member> getAllMembers();
    // Add other methods as needed
}
