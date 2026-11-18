package az.khazar.ustatap.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("MASTER")
public class Master extends User {

    private String profession;
    private String district;
    private Double price;
    private String description;

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}