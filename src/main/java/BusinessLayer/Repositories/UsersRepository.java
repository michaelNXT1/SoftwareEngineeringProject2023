package BusinessLayer.Repositories;

import BusinessLayer.Member;

import java.util.Map;

public class UsersRepository {
    public UsersRepository(Map<String, Member> users) {
        this.users = users;
    }

    public Map<String, Member> getUsers() {
        return users;
    }

    public void addUser(String name, Member member) {
        this.users.put(name, member);
    }

    private Map<String, Member> users;


}
