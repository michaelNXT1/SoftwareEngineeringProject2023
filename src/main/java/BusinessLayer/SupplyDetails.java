package BusinessLayer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class SupplyDetails {
    @Id
    @Column(name = "name", columnDefinition = "text")
    private String name;

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getZip() {
        return zip;
    }

    private final String address;
    private final String city;
    private final String country;
    private final String zip;
    public SupplyDetails(String name, String address, String city, String country, String zip) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.country = country;
        this.zip = zip;
    }

}
