package BusinessLayer;

import DAOs.PurchaseDAO;
import DAOs.PurchaseProductDAO;
import Repositories.IPurchaseProductRepository;
import Repositories.IPurchaseRepository;
import Utils.Pair;
import jakarta.persistence.*;

//import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.atmosphere.annotation.AnnotationUtil.logger;

@Entity
@Table(name = "shopping_bag")
public class ShoppingBag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "shopping_bag_products" ,joinColumns = @JoinColumn(name = "shoppingBag_id"))
    @Column(name = "product_id")
    private Map<Integer,Integer> productListId = new HashMap<>();

    @Column(name="username")
    private String username;

    @Column
    private Long shoppingCartId;

    @Transient
    private IPurchaseRepository purchaseRepository = new PurchaseDAO();
    public ShoppingBag(Store store, Long shoppingCartID, String username) {
        this.shoppingCartId = shoppingCartID;
        this.store = store;
        this.username=username;
    }

    public ShoppingBag() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public void setProductListId(Map<Integer,Integer> productListId) {
        this.productListId = productListId;
    }

    public Long getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(Long shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public IPurchaseRepository getPurchaseRepository() {
        return purchaseRepository;
    }

    public void setPurchaseRepository(IPurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    //Use case 2.10 & Use case 2.12
    public void setProductQuantity(int productId, int quantity) throws Exception {
        Product p = store.getProduct(productId);
        if (quantity == 0)
            productListId.remove(productId);
        else if (p.getQuantity() >= quantity)
            productListId.put(productId,quantity);
        else {
            logger.error("Requested product quantity not available.");
            throw new Exception("Requested product quantity not available.");
        }
    }

    //Use case 2.13
    public void removeProduct(int productId) throws Exception {
        store.getProduct(productId);
        productListId.remove(productId);
    }

    //Use case 2.14
    public Pair<PurchaseProduct, Boolean> purchaseProduct(int productId, Purchase bagPurchase) {
        try {
            PurchaseProduct pp = store.subtractForPurchase(productId,productListId.get(store.getProduct(productId).getProductId()), Math.toIntExact(bagPurchase.getId()));
            double discountPercentage = store.getProductDiscountPercentage(productId,productListId);
            pp.setPrice(pp.getPrice() * (1.0 - discountPercentage));
            IPurchaseProductRepository ppr=new PurchaseProductDAO();
            ppr.addPurchaseProduct(pp);
            return new Pair<>(pp, true);
        } catch (Exception e) {
            return new Pair<>(null, false);
        }
    }

    public Pair<List<PurchaseProduct>, Boolean> purchaseShoppingBag(Purchase bagPurchase) throws Exception {
        if (!store.checkPoliciesFulfilled(productListId)) {
            logger.error("Store purchase policies are not fulfilled in this cart");
            throw new Exception("Store purchase policies are not fulfilled in this cart");
        }
        List<PurchaseProduct> retList = new ArrayList<>();
        for (Map.Entry<Integer, Integer> e : productListId.entrySet()) {
            Pair<PurchaseProduct, Boolean> ppp = purchaseProduct(e.getKey(),  bagPurchase);
            if (ppp.getSecond()) {
                retList.add(ppp.getFirst());
//                bagPurchase.addProduct(ppp.getFirst());
            } else {
                revertPurchase(retList);
                return new Pair<>(null, false);
            }
        }
//        store.addPurchase(bagPurchase);
        productListId.clear();
        return new Pair<>(retList, true);
    }

    public void revertPurchase(List<PurchaseProduct> retList) throws Exception {
        for (PurchaseProduct p : retList)
            store.addToProductQuantity(p.getProductId(), p.getQuantity());
    }

    public Store getStore() {
        return store;
    }

    public Map<Integer, Integer> getProductListId() {
        return productListId;
    }

    public boolean isEmpty() {
        return productListId.isEmpty();
    }

    public double getProductDiscountPercentageInCart(int productId) throws Exception {
        return store.getProductDiscountPercentage(productId, productListId);

    }
}
