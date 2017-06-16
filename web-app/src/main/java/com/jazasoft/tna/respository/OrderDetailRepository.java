package com.jazasoft.tna.respository;

import com.jazasoft.tna.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by mtalam on 6/13/2017.
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long> {
}
