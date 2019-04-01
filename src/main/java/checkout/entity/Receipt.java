package checkout.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Receipt {

    @Id
    @GeneratedValue(generator = "receipt_generator")
    @SequenceGenerator(name = "receipt_generator", initialValue = 1000)
    private long id;

    @NotNull
    @OneToMany(fetch = FetchType.EAGER)
    private List<SKU> skus;

    public long getId() {
        return id;
    }

    public List<SKU> getSkus() {
        return skus;
    }

    public void setSkus(List<SKU> shopping) {
        this.skus = shopping;
    }
}
