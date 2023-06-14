package BusinessLayer;

import DAOs.PurchaseDAO;
import DAOs.PurchaseProductDAO;
import Repositories.IPurchaseRepository;
import Utils.Pair;
import jakarta.persistence.*;
import org.hibernate.engine.internal.Cascade;

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
    @ElementCollection
    @CollectionTable(name = "shopping_bag_products" ,joinColumns = @JoinColumn(name = "shoppingBag_id"))
    @Column(name = "product_id")
    private List<Integer> productListId = new ArrayList<>();

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "purchase_id")
    private Purchase bagPurchase;

    @Column
    private Long shoppingCartId;

    @Transient
    private IPurchaseRepository purchaseRepository = new PurchaseDAO();
    public ShoppingBag(Store store, Long shoppingCartID) {
        this.shoppingCartId = shoppingCartID;
        this.store = store;
        bagPurchase = new Purchase(new PurchaseProductDAO());
        purchaseRepository.savePurchase(bagPurchase);
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

    public void setProductListId(List<Integer> productListId) {
        this.productListId = productListId;
    }

    public Purchase getBagPurchase() {
        return bagPurchase;
    }

    public void setBagPurchase(Purchase bagPurchase) {
        this.bagPurchase = bagPurchase;
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
        else if (p.getAmount() >= quantity)
            productListId.add(productId);
        else {
            logger.error("Requested product quantity not available.");
            throw new Exception("Requested product quantity not available.");
        }
    }

    //Use case 2.13
    public void removeProduct(int productId) throws Exception {
        Product p = store.getProduct(productId);
        productListId.remove(p);
    }

    //Use case 2.14
    public Pair<PurchaseProduct, Boolean> purchaseProduct(int productId) {
        try {
            PurchaseProduct pp = store.subtractForPurchase(productId,store.getProduct(productId).getAmount());
            Map<Integer,Integer> productToAmount = new HashMap<>();
            for(Integer pId :this.productListId){
                Product product = store.getProduct(pId);
                productToAmount.put(pId,product.getAmount());
            }
            double discountPercentage = store.getProductDiscountPercentage(productId,productToAmount);
            pp.setPrice(pp.getPrice() * (1.0 - discountPercentage));
            this.productListId.remove(productId);
            return new Pair<>(pp, true);
        } catch (Exception e) {
            return new Pair<>(null, false);
        }
    }

    public Pair<List<PurchaseProduct>, Boolean> purchaseShoppingBag() throws Exception {
        Map<Integer,Integer> productToAmount = new HashMap<>();
        for(Integer pId :this.productListId){
            Product product = store.getProduct(pId);
            productToAmount.put(pId,product.getAmount());
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

    public List<Integer> getProductListId() {
        return productListId;
    }

    public List<Product> getProductList() {
        List<Product> products = new ArrayList<>();
        for(Integer pId : this.productListId){
            try {
                products.add(store.getProduct(pId));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return products;
    }
    public boolean isEmpty() {
        return productListId.isEmpty();
    }

    public double getProductDiscountPercentageInCart(int productId) throws Exception {
        Map<Integer,Integer> productToAmount = new HashMap<>();
        for(Integer pId :this.productListId){
            Product product = store.getProduct(pId);
            productToAmount.put(product.getProductId(),product.getAmount());
        }
        return store.getProductDiscountPercentage(productId, productToAmount);
    }
}
