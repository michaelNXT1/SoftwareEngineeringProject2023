package BusinessLayer;

import BusinessLayer.Logger.SystemLogger;

import CommunicationLayer.NotificationBroker;
import CommunicationLayer.NotificationController;
import DAOs.MemberDAO;
import DAOs.NotificationDAO;
import DAOs.PositionDAO;
import Repositories.INotificationRepository;
import DAOs.*;
import Repositories.IPaymantRepo;
import Repositories.IPositionRepository;
import Repositories.ISupplyRepo;
import Security.SecurityUtils;
import ServiceLayer.DTOs.StoreDTO;


import Notification.Notification;
import jakarta.persistence.*;

//import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.atmosphere.annotation.AnnotationUtil.logger;

@Entity
@Table(name = "members")
public class Member extends Guest {
    @Transient
    private INotificationRepository notificationRepository = new NotificationDAO();
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "member_notifications" ,joinColumns = @JoinColumn(name = "member_name"))
    @Column
    private List<Notification> notifications;
    @Transient
    private NotificationBroker notificationBroker;
    @Transient
    private IPaymantRepo paymantRepo = new PaymentDAO();
    @Transient
    private ISupplyRepo supplyRepo = new SupplyDAO();
    @Id
    @Column
    private String username;

    @Column
    private String hashedPassword;

    @Transient
    private IPositionRepository positions = new PositionDAO();//all the positions of this member, note that position act as a state
    @Transient
    private SystemLogger logger = new SystemLogger();

    public Member(String username, String hashedPassword) {
        super();
        this.notifications = new ArrayList<>();
        this.username = username;
        this.hashedPassword = hashedPassword;
    }

    public Member() {

    }
    @Override
    public void addPaymentMethod(String cardNumber, String month, String year, String cvv, String holder, String cardId) {
        paymentDetails = new PaymentDetails(cardNumber, month, year, cvv, holder, cardId,this.username);
        paymantRepo.addPayment(paymentDetails);

    }
    @Override
    public void addSupplyDetails(String name , String address, String city, String country, String zip) {
        supplyDetails = new SupplyDetails(name, address, city, country, zip,this.username);
        supplyRepo.addSupply(supplyDetails);
    }
    @Override
    public void addShoppingCart(){
        shoppingCart = new ShoppingCart(this.username);
        shoppingCartRepo.addShoppingCart(shoppingCart);
    }
    @Override
    public ShoppingCart displayShoppingCart(Long id) {  //2.11
        List<ShoppingCart> myShoppingCart = shoppingCartRepo.getAllShoppingCart().stream().filter(sc->sc.getUserName().equals(this.username)).toList();
        if(!myShoppingCart.isEmpty())
            return myShoppingCart.get(0);
        return null;
    }
    @Override
    public PaymentDetails getPaymentDetails() {
        List<PaymentDetails> myPd = paymantRepo.getAllPayment().stream().filter(pd->pd.getUserName().equals(this.username)).toList();
        if(!myPd.isEmpty())
            return myPd.get(0);
        return null;
    }
    @Override
    public SupplyDetails getSupplyDetails() {
        List<SupplyDetails> mySd = supplyRepo.getAllSupply().stream().filter(sd->sd.getUserName().equals(this.username)).toList();
        if(!mySd.isEmpty())
            return mySd.get(0);
        return null;
    }
    @Override
    public void addProductToShoppingCart(Store s, int productId, int itemsAmount) throws Exception { //2.10
        shoppingCart.setProductQuantity(s, productId, itemsAmount);
    }
    // getter, setter
    public void setNotificationBroker(NotificationBroker notificationBroker){
        this.notificationBroker = notificationBroker;
    }
    public void setPosition(Position newPosition) {
        boolean found = false;
        for (Position p : positions.getAllPositions()) {
            if (p.getStore().getStoreId()==newPosition.getStore().getStoreId()) {
                positions.removePosition(p);
                positions.addPosition(newPosition);
                found = true;
            }
            if (!found) {
                positions.addPosition(newPosition);
            }
        }
    }
    @Override
    public ShoppingCart getShoppingCart() {
        return shoppingCartRepo.getAllShoppingCart().stream().filter(sc->sc.getUserName().equals(this.getUsername())).toList().get(0);
    }
    public String getPassword() {
        return hashedPassword;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return username;
    }

    public void logout() {
        SecurityUtils.logout();
        notificationBroker = null;

    }

    public Position getStorePosition(Store store) {
        synchronized (positions) {
            for (Position position : positions.getAllPositions()) {
                if (position.getStore().getStoreName().equals(store.getStoreName()) && position.getPositionMember().username.equals(this.username)) {
                    return position;
                }
            }
            return null;
        }
    }


    public void setToStoreManager(Store store, Member assigner) throws Exception {
        if (getStorePosition(store) != null) {
            logger.error(String.format("the member is already have a different position in this store : %s", store.getStoreName()));
            throw new Exception("the member is already have a different position in this store");
        } else {
            positions.addPosition(new StoreManager(store, assigner,this));
        }
    }

    public void sendRealTimeNotification(){
        boolean isCraashed = false;
        if(Market.users.isLoggedIn(this)){
            this.notificationBroker = new NotificationController();
        }
        if(notificationBroker!= null && !(notifications == null || notifications.isEmpty())) {
            for (Notification notification : notifications) {
                try {
                    notificationBroker.sendRealTimeNotification(notification, this.username);
                }
                catch (Exception e){
                    isCraashed = true;
                }
            }
            if(!isCraashed)
                notifications.clear();
        }
        new MemberDAO().updateMember(this);
    }

    public void sendNotification(Notification shopNotification) {
        boolean isCraashed = false;
        if(Market.users.isLoggedIn(this)){
            this.notificationBroker = new NotificationController();
        }
        if (this.notificationBroker != null) {
            try {
                notificationBroker.sendRealTimeNotification(shopNotification, this.username);
            }
            catch (Exception e){
                isCraashed = true;
            }
        }else {
            this.notifications.add(shopNotification);
            new NotificationDAO().addNotification(shopNotification);
            new MemberDAO().updateMember(this);
        }
        if(isCraashed){
            this.notifications.add(shopNotification);
            new NotificationDAO().addNotification(shopNotification);
            new MemberDAO().updateMember(this);
        }
    }

    public void setToStoreOwner(Store store, Member assigner) throws Exception {
        if (getStorePosition(store) != null) {
            logger.error(String.format("the member is already have a different position in this store : %s", store.getStoreName()));
            throw new Exception("the member is already have a different position in this store");
        } else {
            logger.info(String.format("%s promote to be the owner of %s", getUsername(), store.getStoreName()));
            positions.addPosition(new StoreOwner(store, assigner,this));
        }
    }

    @Override
    public Store openStore(String name, int storeID) {
        Store newStore = new Store(storeID, name, this);
        StoreFounder newStoreFounder = new StoreFounder(newStore,this);
        StoreOwner newStoreOwner = new StoreOwner(newStore,null,this);
        try {
            positions.addPosition(newStoreFounder);
        } catch (Exception e) {
            // Rollback if either operation fails
            throw e;
        }
        return newStore;
    }

    public boolean hasPositions() {
        return !positions.getAllPositions().stream().filter(p -> p.getPositionMember().getUsername().equals(this.username)).toList().isEmpty();
    }

    public boolean notBeingStoreOwner(Guest m, Store store) throws Exception {
        Position storeOwnerP = null;
        for (Position p : positions.getAllPositions())
            if (p instanceof StoreOwner && p.getStore().getStoreId()==store.getStoreId() && p.getPositionMember().username.equals(this.username))
                storeOwnerP = p;
        if (storeOwnerP == null) {
            logger.error(String.format("%s is not a store owner", username));
            throw new Exception(String.format("%s is not a store owner", username));
        }
        if (!storeOwnerP.getAssigner().getUsername().equals(m.getUsername())) {
            logger.error(String.format("%s is not the assigner of %s", m.getUsername(), getUsername()));
            throw new Exception("can remove only store owner assigned by him");
        }
        positions.removePosition(storeOwnerP);
        logger.info(String.format("remove %s from being store owner", getUsername()));
        return true;
    }

    public List<Position> getPositions() {
        List<Position> thisMemberPosition = new LinkedList<>();
        for (Position p:positions.getAllPositions()
        ) {
            if (p.getPositionMember().getUsername().equals(this.username))
                thisMemberPosition.add(p);
        }
        return thisMemberPosition;
    }

    public void setPositions(IPositionRepository positions) {
        this.positions = positions;
    }


    @Override
    public boolean isLoggedIn() {
        return true;
    }

    public List<StoreDTO> getResponsibleStores() {
        List<StoreDTO> ret = new ArrayList<>();
        List<Position> positionList=positions.getPositionsByMember(username);
        for (Position p : positionList) {
            ret.add(new StoreDTO(p.getStore()));
        }
        return ret;
    }

    @Override
    public void makeOffer(Store s, int productId, Double pricePerItem, Integer quantity) throws Exception {
        s.makeOffer(this, productId, pricePerItem, quantity);
    }

    @Override
    public void bid(Store s, int productId, double price) throws Exception {
        s.bid(this, productId, price);
    }
    @Override
    public Purchase purchaseShoppingCart() throws Exception {    //2.14
        Purchase p=new Purchase(new ArrayList<>(), getUsername());
        purchaseHistory.savePurchase(p);
        if (paymentDetails == null) {
            logger.error("no payment details exist");
            throw new Exception("no payment details exist");
        }
        if (supplyDetails == null) {
            logger.error("no supply details exist");
            throw new Exception("no supply details exist");
        }
        p = shoppingCart.purchaseShoppingCart(p);
        purchaseHistory.savePurchase(p);
        return p;
    }

    public boolean removePosition(Position userP) {
        positions.removePosition(userP);
        logger.info(String.format("remove %s from being %s duo to the remove foo his assigner", getUsername(),userP.getClass()));
        return true;
    }

    public void addPosition(Position p) {
        positions.addPosition(p);
    }
}
