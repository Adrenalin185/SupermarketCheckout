package checkout.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Receipt {

    @Id
    @GeneratedValue(generator = "receipt_generator")
    @SequenceGenerator(name = "receipt_generator", initialValue = 1000)
    private long id;

    @NotNull
    private List<SKU> shopping;

    public long getId() {
        return id;
    }

    public List<SKU> getShopping() {
        return shopping;
    }

    public void setShopping(List<SKU> shopping) {
        this.shopping = shopping;
    }
}
