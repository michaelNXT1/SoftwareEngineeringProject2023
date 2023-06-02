package DAOs;

import BusinessLayer.Member;
import Repositories.IMemberRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class MemberDAO implements IMemberRepository {
    @Override
    public void addMember(Member memberDTO) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(memberDTO);
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
}
