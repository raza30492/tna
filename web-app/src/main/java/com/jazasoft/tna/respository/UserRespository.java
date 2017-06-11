package com.jazasoft.tna.respository;

import com.jazasoft.tna.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRespository extends JpaRepository<User, Long>{
    
    User findByEmail(String email);
    
    User findByName(String name);

    User findByUsername(String username);
}
