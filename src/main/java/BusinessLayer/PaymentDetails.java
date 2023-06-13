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


    public PaymentDetails(String cardNumber, String month, String year, String cvv, String holder, String cardId) {
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
