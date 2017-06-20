package com.jazasoft.tna.respository;

import com.jazasoft.tna.entity.Buyer;
import com.jazasoft.tna.entity.Label;
import com.jazasoft.tna.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by mtalam on 6/13/2017.
 */
public interface OrderRepository extends JpaRepository<Order,Long>{

    List<Order> findByLabel(Label label);
}
