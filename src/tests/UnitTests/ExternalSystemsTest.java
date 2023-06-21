package UnitTests;

import BusinessLayer.ExternalSystems.PaymentSystem;
import BusinessLayer.ExternalSystems.SupplySystem;
import BusinessLayer.Market;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;

public class ExternalSystemsTest extends TestCase {
    Market market = new Market(null,true);
    PaymentSystem paymentSystem;
    SupplySystem supplySystem;

    @Before
    public void setUp() {
        paymentSystem = new PaymentSystem();
        supplySystem = new SupplySystem();
    }
    @AfterEach
    public void tearDown() {
        market.clearAllData();
    }
    @Test
    public void testHandShake() {
        assertTrue(paymentSystem.handshake());
    }

    @Test
    public void testAttemptPurchaseSuccess() {
        int transactionId = paymentSystem.pay("12345678", "04", "2021", "me", "777", "123123123");
        assertTrue(transactionId > 0);
    }

    @Test
    public void testRequestRefundSuccess() {
        int transactionId = paymentSystem.pay("12345678", "04", "2021", "me", "777", "123123123");
        assertEquals(paymentSystem.cancelPay(transactionId), 1);
    }

    @Test
    public void testRequestSupplySuccess() {
        int transactionId = supplySystem.supply("Michael", "1725 Slough Avenue", "Scranton", "PA, United States", "12345");
        assertNotSame(transactionId, 1);
    }

    @Test
    public void testCancelSupplySuccess() {
        int transactionId = supplySystem.supply("Michael", "1725 Slough Avenue", "Scranton", "PA, United States", "12345");
        assertEquals(supplySystem.cancelSupply(transactionId), 1);
    }

    @Test
    public void testPaymentContactFailureNoContact() {
        PaymentSystem.loseContact = true;
        assertFalse(paymentSystem.handshake());
        assertEquals(paymentSystem.pay("12345678", "04", "2021", "me", "777", "123123123"), -1);
        assertEquals(paymentSystem.cancelPay(123), -1);
        PaymentSystem.loseContact = false;
    }

    @Test
    public void testSupplyContactFailureNoContact() {
        SupplySystem.loseContact = true;
        assertFalse(supplySystem.handshake());
        assertEquals(supplySystem.supply("Michael", "1725 Slough Avenue", "Scranton", "PA, United States", "12345"), -1);
        assertEquals(supplySystem.cancelSupply(123), -1);
        SupplySystem.loseContact = false;
    }

}
