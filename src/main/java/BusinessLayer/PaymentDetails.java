package BusinessLayer;

import jakarta.persistence.*;
@Entity
@Table(name = "PaymentDetails")
public class PaymentDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creditCardNumber")
    private String creditCardNumber;

    @Column(name = "month")
    private String month;

    @Column(name = "year")
    private String year;

    @Column(name = "cvv")
    private String cvv;

    @Column(name = "holder")
    private String holder;

    @Column(name = "cardId")
    private String cardId;
    @Column
    private String userName;

    public PaymentDetails(String cardNumber, String month, String year, String cvv, String holder, String cardId,String userName) {
        this.userName = userName;
        this.creditCardNumber = cardNumber;
        this.month = month;
        this.year = year;
        this.cvv = cvv;
        this.holder = holder;
        this.cardId = cardId;
    }

    public PaymentDetails() {
        // JPA requires a default constructor
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public String getCvv() {
        return cvv;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }

    public String getHolder() { return holder;}

    public String getCardId() { return cardId;}
}
