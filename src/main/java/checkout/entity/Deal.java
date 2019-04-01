package checkout.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Deal {

    @Id
    @GeneratedValue(generator = "deal_generator")
    @SequenceGenerator(name = "deal_generator", initialValue = 1000)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SKU")
    @NotNull
    private SKU sku;

    @NotNull
    private int productsRequired;

    @NotNull
    private double newPrice;

    public Deal (){}

    public Deal(@NotNull SKU sku, @NotNull int productsRequired, @NotNull double newPrice) {
        this.sku = sku;
        this.productsRequired = productsRequired;
        this.newPrice = newPrice;
    }

    public long getId() {
        return id;
    }

    public SKU getSku() {
        return sku;
    }

    public int getProductsRequired() {
        return productsRequired;
    }

    public void setProductsRequired(int productsRequired) {
        this.productsRequired = productsRequired;
    }

    public double getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(double newPrice) {
        this.newPrice = newPrice;
    }
}
