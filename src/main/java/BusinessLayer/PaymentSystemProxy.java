package BusinessLayer;

import BusinessLayer.ExternalSystems.IPaymentSystem;

public class PaymentSystemProxy implements IPaymentSystem {

    private IPaymentSystem paymentSystem = null;
    public static boolean succeedPayment = true; //for tests
    private static int fakeTransactionId = 10000;


    @Override
    public boolean handshake() {
        if (paymentSystem == null) return true;
        return paymentSystem.handshake();
    }

    public int pay(String cardNumber, String expirationMonth, String expirationYear, String holder, String ccv, String cardId) {
        if (paymentSystem == null)
            return succeedPayment ? fakeTransactionId++ : -1;
        if(paymentSystem.handshake())
            return paymentSystem.pay(cardNumber, expirationMonth, expirationYear, holder, ccv, cardId);
        return -1;
    }

    public int cancelPay(int transactionId) {
        if (paymentSystem == null) return 1;
        if (paymentSystem.handshake())
            return paymentSystem.cancelPay(transactionId);
        return -1;
    }

    public void setPaymentSystem(IPaymentSystem paymentSystem) {
        this.paymentSystem = paymentSystem;
    }
}
