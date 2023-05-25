package BusinessLayer;
import javax.persistence.*;

@Embeddable
public class DeliveryDetails {

    @Column
    private String street;

    @Column
    private int number;

    @Column
    private String zipcode;

    public DeliveryDetails(String street, int number, String zipcode) {
        this.street = street;
        this.number = number;
        this.zipcode = zipcode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}
