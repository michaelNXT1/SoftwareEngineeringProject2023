package BusinessLayer.ExternalSystems;

public interface IPaymentSystem {
    /*This action type is used for check the availability of the external systems.
    action_type = handshake
    Additional Parameters: none
    Output: “OK” message to signify that the handshake has been successful*/
    boolean handshake();

    /*This action type is used for charging a payment for purchases.
    action_type = pay
    Additional Parameters: card_number, month, year, holder, ccv, id
    Output: transaction id - an integer in the range [10000, 100000] which indicates a
    transaction number if the transaction succeeds or -1 if the transaction has failed.*/
    int pay(String cardNumber, String expirationMonth, String expirationYear, String holder, String ccv, String cardOwnerId);

    /*This action type is used for cancelling a payment transaction.
    action_type = cancel_pay
    Additional Parameters: transaction_id - the id of the transaction id of the
    transaction to be canceled.
    Output: 1 if the cancelation has been successful or -1 if the cancelation has failed*/
    int cancelPay(int transactionId);
}
