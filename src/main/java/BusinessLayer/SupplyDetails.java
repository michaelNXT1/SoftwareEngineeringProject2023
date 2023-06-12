package BusinessLayer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class SupplyDetails {
    @Id
    @Column
    private String name;
@Column
    public String getName() {
        return name;
    }
    @Column

    public String getAddress() {
        return address;
    }
    @Column

    public String getCity() {
        return city;
    }
    @Column

    public String getCountry() {
        return country;
    }
    @Column

    public String getZip() {
        return zip;
    }

    private String address;
    private String city;
    private String country;
    private String zip;
    public SupplyDetails(String name, String address, String city, String country, String zip) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.country = country;
        this.zip = zip;
    }

    public SupplyDetails() {
    }
}
