package com.jazasoft.tna.respository;

import com.jazasoft.tna.entity.Buyer;
import com.jazasoft.tna.entity.Label;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by mtalam on 6/13/2017.
 */
public interface LabelRepository extends JpaRepository<Label,Long> {
    List<Label> findByBuyer(Buyer buyer);
}
