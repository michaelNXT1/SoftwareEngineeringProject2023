package DAOs;

import BusinessLayer.Member;
import BusinessLayer.SystemManager;
import Repositories.IMapStringMemberRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public Member get(String key) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Member member = null;
        try {
            if(memberMap.containsKey(key)){
                return memberMap.get(key);
            }
            member = session.get(Member.class,key);
        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
        return member;
    }

    @Override
    public void remove(String key) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        Member member = null;
        try {
            transaction = session.beginTransaction();
            member = get(key);
            if (member != null) {
                session.remove(member);
                memberMap.remove(key);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void logout(String key) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Member member = null;
        try {
            member = get(key);
            if (member != null) {
                memberMap.remove(key);
            }
        } catch (Exception e) {
            throw e;
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
        Session session = HibernateUtil.getSessionFactory().openSession();
        Map<String, Member> members = null;
        try {
            members = session.createQuery("FROM Member", Member.class).getResultStream()
                    .collect(Collectors.toMap(Member::getUsername, Function.identity()));
        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
        return members;
    }
    public void clear() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM Member").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }
}
