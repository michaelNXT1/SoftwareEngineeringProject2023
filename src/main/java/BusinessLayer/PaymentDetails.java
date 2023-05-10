package BusinessLayer;

import java.time.LocalDate;

public class PaymentDetails {
    private final String creditCardNumber;
    private final String month;
    private final String year;
    private final String cvv;


    public PaymentDetails(String cardNumber, String month, String year, String cvv) {
        this.creditCardNumber = cardNumber;
        this.month = month;
        this.year = year;
        this.cvv = cvv;
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
}
