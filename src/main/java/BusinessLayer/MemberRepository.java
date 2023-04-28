package BusinessLayer;

public interface MemberRepository {

    Member getUserById(int userId);
    void updateUser(Member user);

}
