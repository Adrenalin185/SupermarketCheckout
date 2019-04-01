package checkout.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class SKU {

    @Id
    @NotBlank
    @Size(min = 1, max = 1)
    @Column(unique = true)
    private String sKUID;

    @NotNull
    private double price;

    public SKU(){}

    public SKU(@NotBlank @Size(min = 1, max = 1) String sKUID, @NotNull double price) {
        this.sKUID = sKUID;
        this.price = price;
    }

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
