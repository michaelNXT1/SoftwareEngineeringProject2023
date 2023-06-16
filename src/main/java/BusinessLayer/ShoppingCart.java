package BusinessLayer;

import BusinessLayer.Logger.SystemLogger;
import DAOs.ShoppingBagDAO;
import Repositories.IShoppingBagRepository;
import Utils.Pair;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Entity
public class ShoppingCart {
    @Transient
    public IShoppingBagRepository shoppingBags = new ShoppingBagDAO();
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String userName;
    @Transient
    private List<ShoppingBag> shoppingBags2;
    @Transient

    private SystemLogger logger;

    public ShoppingCart(String userName) {
        this.userName = userName;
        shoppingBags2 = shoppingBags.getAllShoppingBags().stream().filter(sb -> sb.getId().equals(this.id)).toList();
        this.logger = new SystemLogger();
    }

    public ShoppingCart() {
    }

    //Use case 2.10
    //Use case 2.12
    @Transactional
    public void setProductQuantity(Store s, int productId, int quantity) throws Exception {
        //TODO: add purchase type check
        if (quantity < 0) {
            logger.error(String.format("this quantity %d is less then 0", quantity));
            throw new Exception("cannot change quantity to less then 0");
        }
        ShoppingBag shoppingBag = getShoppingBag(s);
        shoppingBag.setProductQuantity(productId, quantity);
        shoppingBags.addShoppingBag(shoppingBag);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void String(String userName) {
        this.userName = userName;
    }

    public IShoppingBagRepository getShoppingBags() {
        return shoppingBags;
    }

    public void setShoppingBags(IShoppingBagRepository shoppingBags) {
        this.shoppingBags = shoppingBags;
    }

    public List<ShoppingBag> getShoppingBags2() {
        return shoppingBags2;
    }

    public void setShoppingBags2(List<ShoppingBag> shoppingBags2) {
        this.shoppingBags2 = shoppingBags2;
    }

    public SystemLogger getLogger() {
        return logger;
    }

    public void setLogger(SystemLogger logger) {
        this.logger = logger;
    }

    //Use case 2.13
    public void removeProduct(Store s, int productId) throws Exception {
        List<ShoppingBag> allShoppingBags = shoppingBags.getAllShoppingBags();
        ShoppingBag shoppingBag = allShoppingBags.stream()
                .filter(sb -> sb.getStore().getStoreId() == s.getStoreId())
                .findFirst()
                .orElse(null);
        if (shoppingBag == null) {
            logger.error(String.format("This store %s is not existing in this cart", s.getStoreName()));
            throw new Exception("Store doesn't exist in cart");
        }
        shoppingBag.removeProduct(productId);
        shoppingBags.updateShoppingBag(shoppingBag);
    }

    //Use case 2.14
    public Purchase purchaseShoppingCart() throws Exception {
        if (isEmpty()) {
            logger.error("Purchase failed, cart is empty");
            throw new Exception("Purchase failed, cart is empty");
        }
        Map<ShoppingBag, List<PurchaseProduct>> shoppingBagMap = new HashMap<>();
        for (ShoppingBag sb : shoppingBags.getAllShoppingBags().stream().filter(fb -> fb.getShoppingCartId().equals(this.id)).toList()) {
            Pair<List<PurchaseProduct>, Boolean> sbp = sb.purchaseShoppingBag();
            if (sbp.getSecond()) {
                shoppingBagMap.put(sb, sbp.getFirst());
            } else {
                for (Map.Entry<ShoppingBag, List<PurchaseProduct>> entry : shoppingBagMap.entrySet())
                    entry.getKey().revertPurchase(entry.getValue());
                logger.error("Purchase failed, not all products were in stock as requested.");
                throw new Exception("Purchase failed, not all products were in stock as requested.");
            }
        }
        shoppingBags.clearShoppingBags();
        List<PurchaseProduct> flattenedList = shoppingBagMap.values()
                .stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
        logger.info("This shopping bag has been cleaned");

        return new Purchase(flattenedList);
    }


    private ShoppingBag getShoppingBag(Store s) {
        List<ShoppingBag> shoppingBags1 = shoppingBags.getAllShoppingBags().stream()
                .filter(sb -> sb.getStore().getStoreId()==s.getStoreId()).toList();
        ShoppingBag shoppingBag;
        if (shoppingBags1.isEmpty()) {
            shoppingBag = new ShoppingBag(s, this.id);
        } else {
            shoppingBag = shoppingBags1.get(0);
        }
        return shoppingBag;

    }

    private boolean isEmpty() {
        return shoppingBags.getAllShoppingBags().stream().allMatch(ShoppingBag::isEmpty);
    }


    public void revertPurchase(Purchase purchase) {
    }

    public double getProductDiscountPercentageInCart(Store s, int productId) throws Exception {
        ShoppingBag shoppingBag = shoppingBags.getAllShoppingBags().stream().filter(sb -> sb.getStore().getStoreId()==s.getStoreId()).findFirst().orElse(null);
        if (shoppingBag == null) {
            logger.error(String.format("this store %s is not existing in this cart", s.getStoreName()));
            throw new Exception("store doesn't exist in cart");
        }
        return shoppingBag.getProductDiscountPercentageInCart(productId);
    }
}
