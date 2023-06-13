package BusinessLayer;

import DAOs.MapIntegerIntegerDAO;
import DAOs.PurchaseProductDAO;
import Repositories.IMapIntegerIntegerRepository;
import Repositories.IProductRepository;
import Utils.Pair;
import jakarta.persistence.*;

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

    @Transient
    private IProductRepository productList;

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
        Product p = store.getProduct(productId);
        if (quantity == 0)
            productList.deleteProduct(p);
        else if (productList.getProductById(productId).getAmount() >= quantity)
            productList.saveProduct(p);
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
            PurchaseProduct pp = store.subtractForPurchase(productId, productList.getProductById(productId).getAmount());
            double discountPercentage = store.getProductDiscountPercentage(productId, productList.getAllProducts());
            pp.setPrice(pp.getPrice() * (1.0 - discountPercentage));
            return new Pair<>(pp, true);
        } catch (Exception e) {
            return new Pair<>(null, false);
        }
    }

    public Pair<List<PurchaseProduct>, Boolean> purchaseShoppingBag() throws Exception {
        if (!store.checkPoliciesFulfilled(productList.getAllProducts())) {
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

    public IMapIntegerIntegerRepository getProductList() {
        return productList;
    }

    public boolean isEmpty() {
        return productList.getAllProducts().values().stream().allMatch(integer -> integer == 0);
    }

    public double getProductDiscountPercentageInCart(int productId) throws Exception {
        return store.getProductDiscountPercentage(productId, productList);
    }
}
