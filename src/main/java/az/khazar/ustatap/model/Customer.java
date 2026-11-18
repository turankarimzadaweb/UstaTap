package az.khazar.ustatap.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("CUSTOMER")
public class Customer extends User {

    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}