package BusinessLayer;

import DAOs.MapIntegerIntegerDAO;
import DAOs.PurchaseProductDAO;
import Repositories.IMapIntegerIntegerRepository;
import Utils.Pair;
import jakarta.persistence.*;

//import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
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

    @ManyToOne
    @JoinColumn(name = "product_id")
    private List<Product> productList = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "purchase_id")
    private Purchase bagPurchase;


    public ShoppingBag(Store store) {
        this.id = 0L; // Initializing with a default value
        this.store = store;
        bagPurchase = new Purchase(new PurchaseProductDAO());
    }

    public ShoppingBag() {

    }

    //Use case 2.10 & Use case 2.12
    public void setProductQuantity(int productId, int quantity) throws Exception {
        Product p = store.getProduct(productId);
        if (quantity == 0)
            productList.remove(p);
        else if (p.getAmount() >= quantity)
            productList.add(p);
        else {
            logger.error("Requested product quantity not available.");
            throw new Exception("Requested product quantity not available.");
        }
    }

    //Use case 2.13
    public void removeProduct(int productId) throws Exception {
        Product p = store.getProduct(productId);
        productList.remove(p);
    }

    //Use case 2.14
    public Pair<PurchaseProduct, Boolean> purchaseProduct(int productId) {
        try {
            PurchaseProduct pp = store.subtractForPurchase(productId,store.getProduct(productId).getAmount());
            Map<Integer,Integer> productToAmount = new HashMap<>();
            for(Product p :this.productList){
                productToAmount.put(p.getProductId(),p.getAmount());
            }
            double discountPercentage = store.getProductDiscountPercentage(productId,productToAmount);
            pp.setPrice(pp.getPrice() * (1.0 - discountPercentage));
            this.productList.remove(productId);
            return new Pair<>(pp, true);
        } catch (Exception e) {
            return new Pair<>(null, false);
        }
    }

    public Pair<List<PurchaseProduct>, Boolean> purchaseShoppingBag() throws Exception {
        Map<Integer,Integer> productToAmount = new HashMap<>();
        for(Product p :this.productList){
            productToAmount.put(p.getProductId(),p.getAmount());
        }
        if (!store.checkPoliciesFulfilled(productToAmount)) {
            logger.error("Store purchase policies are not fulfilled in this cart");
            throw new Exception("Store purchase policies are not fulfilled in this cart");
        }
        store.checkPoliciesFulfilled(productToAmount);
        List<PurchaseProduct> retList = new ArrayList<>();
        for (Map.Entry<Integer, Integer> e : productToAmount.entrySet()) {
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
        productList.clear();
        return new Pair<>(retList, true);
    }

    public void revertPurchase(List<PurchaseProduct> retList) throws Exception {
        for (PurchaseProduct p : retList)
            store.addToProductQuantity(p.getProductId(), p.getQuantity());
    }

    public Store getStore() {
        return store;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public boolean isEmpty() {
        return productList.isEmpty();
    }

    public double getProductDiscountPercentageInCart(int productId) throws Exception {
        Map<Integer,Integer> productToAmount = new HashMap<>();
        for(Product p :this.productList){
            productToAmount.put(p.getProductId(),p.getAmount());
        }
        return store.getProductDiscountPercentage(productId, productToAmount);
    }
}
