package com.jazasoft.tna.respository;

import com.jazasoft.tna.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by mtalam on 6/13/2017.
 */
public interface OrderRepository extends JpaRepository<Order,Long>{
}
