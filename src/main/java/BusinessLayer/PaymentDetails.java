package BusinessLayer;
import java.time.LocalDate;
public class PaymentDetails {
        private final String creditCardNumber;
        private final int cvv;
        private final LocalDate expirationDate;

        public PaymentDetails(String creditCardNumber, int cvv, LocalDate expirationDate) {
            this.creditCardNumber = creditCardNumber;
            this.cvv = cvv;
            this.expirationDate = expirationDate;
        }

        public String getCreditCardNumber() {
            return creditCardNumber;
        }

        public int getCvv() {
            return cvv;
        }

        public LocalDate getExpirationDate() {
            return expirationDate;
        }
}
