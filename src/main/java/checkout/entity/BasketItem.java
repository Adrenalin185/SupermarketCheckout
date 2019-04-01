package checkout.entity;

import javax.persistence.*;

@Entity
public class BasketItem {

    @Id
    @GeneratedValue(generator = "basketitem_generator")
    @SequenceGenerator(name = "basketitem_generator", initialValue = 1000)
    private long id;

    @ManyToOne
    private Receipt receipt;

    @ManyToOne
    private SKU sku;

    public BasketItem(){ }

    public BasketItem(Receipt receipt, SKU sku) {
        this.receipt = receipt;
        this.sku = sku;
    }

    public long getId() {
        return id;
    }

    public Receipt getReceipt() {
        return receipt;
    }

    public SKU getSku() {
        return sku;
    }
}
