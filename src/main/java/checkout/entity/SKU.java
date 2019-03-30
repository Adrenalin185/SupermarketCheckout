package checkout.entity;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class SKU {

    @NotBlank
    @Size(min = 1, max = 3)
    private String sKUID;

    @NotNull
    private double price;


    public String getsKUID() {
        return sKUID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
