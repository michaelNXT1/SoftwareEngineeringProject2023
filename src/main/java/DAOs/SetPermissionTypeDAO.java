package DAOs;

import BusinessLayer.StoreManager;
import BusinessLayer.Repositories.ISetPermissionTypeRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Set;

public class SetPermissionTypeDAO implements ISetPermissionTypeRepository {

    private Set<StoreManager.permissionType> permissions;

    public SetPermissionTypeDAO(Set<StoreManager.permissionType> permissions) {
        this.permissions = permissions;
    }

    @Override
    public void addPermission(StoreManager.permissionType permission) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(permission);
            transaction.commit();
            permissions.add(permission);
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
    public void removePermission(StoreManager.permissionType permission) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (permissions.contains(permission)) {
                session.remove(permission);
                permissions.remove(permission);
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
    public Set<StoreManager.permissionType> getAllPermissions() {
        return permissions;
    }

    @Override
    public boolean contains(StoreManager.permissionType permission) {
        return permissions.contains(permission);
    }

}
