package BusinessLayer;
import javax.persistence.*;
public class PaymentDetails {
    @Id
    @Column(name = "creditCardNumber")
    private final String creditCardNumber;
    @Column(name = "month")
    private final String month;
    @Column(name = "year")
    private final String year;
    @Column(name = "cvv")
    private final String cvv;
    @Column(name = "holder")
    private final String holder;
    @Column(name = "cardId")
    private final String cardId;



    public PaymentDetails(String cardNumber, String month, String year, String cvv, String holder, String cardId) {
        this.creditCardNumber = cardNumber;
        this.month = month;
        this.year = year;
        this.cvv = cvv;
        this.holder = holder;
        this.cardId = cardId;
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
