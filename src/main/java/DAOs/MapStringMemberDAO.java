package DAOs;

import BusinessLayer.Member;
import BusinessLayer.Repositories.IMapStringMemberRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.HashMap;
import java.util.Map;

public class MapStringMemberDAO implements IMapStringMemberRepository {

    private Map<String, Member> memberMap;

    public MapStringMemberDAO(Map<String, Member> memberMap) {
        this.memberMap = memberMap;
    }

    @Override
    public void put(String key, Member member) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(member);
            transaction.commit();
            memberMap.put(key, member);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public Member get(String key) {
        return memberMap.get(key);
    }

    @Override
    public void remove(String key) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Member member = memberMap.get(key);
            if (member != null) {
                session.remove(member);
                memberMap.remove(key);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public boolean containsKey(String key) {
        return memberMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Member member) {
        return memberMap.containsValue(member);
    }

    @Override
    public Map<String, Member> getAllMembers() {
        return memberMap;
    }
}
