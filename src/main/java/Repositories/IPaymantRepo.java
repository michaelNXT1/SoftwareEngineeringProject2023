package Repositories;

import BusinessLayer.Member;
import BusinessLayer.PaymentDetails;

import java.util.List;

public interface IPaymantRepo {
    void addPayment(PaymentDetails paymentDetails);
    void removePayment(PaymentDetails paymentDetails);

    PaymentDetails getPayment(String key);
    List<PaymentDetails> getAllPayment();

    void updatePayment(PaymentDetails paymentDetails);

    void clear();
}
