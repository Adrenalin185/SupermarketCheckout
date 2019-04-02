package checkout.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Receipt {

    @Id
    @GeneratedValue(generator = "receipt_generator")
    @SequenceGenerator(name = "receipt_generator", initialValue = 1000)
    private long id;

    @OneToMany
    private List<BasketItem> basketItems;

    public long getId() {
        return id;
    }

    public List<BasketItem> getbasketItems() {
        return basketItems;
    }

    public void setbasketItems(List<BasketItem> shopping) {
        this.basketItems = shopping;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "id=" + id +
                ", basketItems=" + basketItems +
                '}';
    }
}
