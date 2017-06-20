package com.jazasoft.tna.respository;

import com.jazasoft.tna.entity.Activity;
import com.jazasoft.tna.entity.Label;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by mtalam on 6/13/2017.
 */
public interface ActivityRepository extends JpaRepository<Activity,Long> {
    List<Activity> findByLabel(Label label);
}
