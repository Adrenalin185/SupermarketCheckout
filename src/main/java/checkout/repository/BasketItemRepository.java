package checkout.repository;

import checkout.entity.BasketItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BasketItemRepository extends JpaRepository<BasketItem, Long> {

    List<BasketItem> findAllItemsByReceiptId(long receiptId);
}
