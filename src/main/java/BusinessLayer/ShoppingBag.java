package BusinessLayer;

import DAOs.MapIntegerIntegerDAO;
import DAOs.PurchaseProductDAO;
import Repositories.IMapIntegerIntegerRepository;
import Utils.Pair;
import jakarta.persistence.*;

//import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    @Transient
    private IMapIntegerIntegerRepository productList;

    @OneToOne
    @JoinColumn(name = "purchase_id")
    private Purchase bagPurchase;


    public ShoppingBag(Store store) {
        this.id = 0L; // Initializing with a default value
        this.store = store;
        this.productList = new MapIntegerIntegerDAO(new ConcurrentHashMap<>());
        bagPurchase = new Purchase(new PurchaseProductDAO());
    }

    public ShoppingBag() {

    }

    //Use case 2.10 & Use case 2.12
    public void setProductQuantity(int productId, int quantity) throws Exception {
        if (store.getProducts().getAllProducts().get(productId).getAmount() >= quantity)
            productList.put(productId, quantity);
        else {
            logger.error("Requested product quantity not available.");
            throw new Exception("Requested product quantity not available.");
        }
    }

    //Use case 2.13
    public void removeProduct(int productId) throws Exception {
        store.getProduct(productId);
        productList.remove(productId);
    }

    //Use case 2.14
    public Pair<PurchaseProduct, Boolean> purchaseProduct(int productId) {
        try {
            PurchaseProduct pp = store.subtractForPurchase(productId, productList.get(productId));
            double discountPercentage = store.getProductDiscountPercentage(productId, productList.getAllItems());
            pp.setPrice(pp.getPrice() * (1.0 - discountPercentage));
            this.productList.remove(productId);
            return new Pair<>(pp, true);
        } catch (Exception e) {
            return new Pair<>(null, false);
        }
    }

    public Pair<List<PurchaseProduct>, Boolean> purchaseShoppingBag() throws Exception {
        if (!store.checkPoliciesFulfilled(productList.getAllItems())) {
            logger.error("Store purchase policies are not fulfilled in this cart");
            throw new Exception("Store purchase policies are not fulfilled in this cart");
        }
        store.checkPoliciesFulfilled(productList.getAllItems());
        List<PurchaseProduct> retList = new ArrayList<>();
        for (Map.Entry<Integer, Integer> e : productList.getAllItems().entrySet()) {
            Pair<PurchaseProduct, Boolean> ppp = purchaseProduct(e.getKey());
            if (ppp.getSecond()) {
                retList.add(ppp.getFirst());
                bagPurchase.addProduct(ppp.getFirst());
            } else {
                revertPurchase(retList);
                return new Pair<>(null, false);
            }
        }
        store.addPurchase(bagPurchase);
        return new Pair<>(retList, true);
    }

    public void revertPurchase(List<PurchaseProduct> retList) throws Exception {
        for (PurchaseProduct p : retList)
            store.addToProductQuantity(p.getProductId(), p.getQuantity());
    }

    public Store getStore() {
        return store;
    }

    public IMapIntegerIntegerRepository getProductList() {
        return productList;
    }
    public void setProductList(IMapIntegerIntegerRepository productList) {
        this.productList.getAllItems().clear(); // Clear existing items in the in-memory map
        this.productList.getAllItems().putAll(productList.getAllItems()); // Copy items from the new map
    }

    public boolean isEmpty() {
        return productList.getAllItems().values().stream().allMatch(integer -> integer == 0);
    }
}
