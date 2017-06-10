package com.jazasoft.tna.dozerconverter;

import com.jazasoft.tna.entity.Role;
import org.dozer.DozerConverter;

public class RoleConverter extends DozerConverter<String, Role>{

    public RoleConverter() {
        super(String.class, Role.class);
    }
    
    @Override
    public Role convertTo(String source, Role destination) {
        if(source == null) return null;
        return Role.parse("ROLE_"+source);
    }

    @Override
    public String convertFrom(Role source, String destination) {
        if(source == null) return null;
        return source.name();
    }
    
}
