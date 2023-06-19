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
    private String userName;
    @Column
    private String address;
    @Column
    private String city;
    @Column
    private String country;
    @Column
    private String zip;
    public SupplyDetails(String name, String address, String city, String country, String zip,String userName) {
        this.userName = userName;
        this.name = name;
        this.address = address;
        this.city = city;
        this.country = country;
        this.zip = zip;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

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
    public SupplyDetails() {
    }
}
