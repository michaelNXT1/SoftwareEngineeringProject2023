package BusinessLayer;

public class PaymentDetails {
    private final String creditCardNumber;
    private final String month;
    private final String year;
    private final String cvv;
    private final String holder;
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
