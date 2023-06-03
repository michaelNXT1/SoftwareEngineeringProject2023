package DAOs;

import BusinessLayer.Member;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            // register annotated classes
            configuration.addAnnotatedClass(Member.class);
            configuration.addAnnotatedClass(BusinessLayer.Store.class);
            configuration.addAnnotatedClass(BusinessLayer.StoreManager.class);
            configuration.addAnnotatedClass(BusinessLayer.Product.class);
            configuration.addAnnotatedClass(BusinessLayer.StoreFounder.class);
            configuration.addAnnotatedClass(BusinessLayer.StoreOwner.class);
            configuration.addAnnotatedClass(BusinessLayer.Purchase.class);
            configuration.addAnnotatedClass(BusinessLayer.ShoppingCart.class);
            configuration.addAnnotatedClass(BusinessLayer.PurchaseProduct.class);
            configuration.addAnnotatedClass(BusinessLayer.PaymentDetails.class);
            configuration.addAnnotatedClass(BusinessLayer.SupplyDetails.class);
            configuration.addAnnotatedClass(BusinessLayer.PurchaseType.class);
            configuration.addAnnotatedClass(BusinessLayer.SystemManager.class);
            configuration.addAnnotatedClass(BusinessLayer.Policies.PurchasePolicies.BasePurchasePolicy.class);
            sessionFactory = configuration.configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}