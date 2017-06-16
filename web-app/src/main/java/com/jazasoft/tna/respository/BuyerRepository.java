package com.jazasoft.tna.respository;

import com.jazasoft.tna.entity.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by mtalam on 6/13/2017.
 */
public interface BuyerRepository extends JpaRepository<Buyer,Long> {
    Buyer findByName(String name);

}
