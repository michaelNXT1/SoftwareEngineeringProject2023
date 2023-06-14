package BusinessLayer.Repositories;

import BusinessLayer.Member;
import BusinessLayer.Repositories.Interfaces.IMemberRepository;

import java.util.HashMap;
import java.util.Map;

public class MemberRepository implements IMemberRepository {
    private final Map<String, Member> users;

    public MemberRepository() {
        this.users = new HashMap<>();
    }

    @Override
    public void addMember(String key, Member member) {
        users.put(key, member);
    }

    @Override
    public void removeMember(String key) {
        users.remove(key);
    }

    @Override
    public Member getMember(String key) {
        return users.get(key);
    }

    @Override
    public Map<String, Member> getAllMembers() {
        return users;
    }
}
