package BusinessLayer.Repositories;

import BusinessLayer.Member;

import java.util.Map;

public interface IMapStringMemberRepository {
    void put(String key, Member member);
    Member get(String key);
    void remove(String key);
    boolean containsKey(String key);
    boolean containsValue(Member member);
    Map<String, Member> getAllMembers();
}
