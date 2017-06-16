package com.jazasoft.tna.service;

import com.jazasoft.tna.dto.UserDto;
import com.jazasoft.tna.entity.User;
import com.jazasoft.tna.page.converter.UserConverter;
import com.jazasoft.tna.respository.UserRespository;
import java.util.List;
import java.util.stream.Collectors;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService {
    
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired UserRespository userRepository;
    
    @Autowired Mapper mapper;
    
    @Autowired UserConverter converter;

    public UserDto findOne(Long id) {
        logger.debug("findOne(): id = {}",id);
        return mapper.map(userRepository.findOne(id), UserDto.class);
    }

    public List<UserDto> findAll() {
        logger.debug("findAll()");

        return userRepository.findAll().stream()
                .map(user -> mapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }
    
    public Page<UserDto> findAllByPage(Pageable pageable){
        logger.debug("findAllByPage()");
        return userRepository.findAll(pageable).map(converter);
    }

    public UserDto findByEmail(String email) {
        logger.debug("findByEmail(): email = {}",email);
        return mapper.map(userRepository.findByEmail(email), UserDto.class);
    }
    
    public UserDto findByUsername(String username) {
        logger.debug("findByUsername(): username = " , username);
        return mapper.map(userRepository.findByUsername(username), UserDto.class);
    }

    public UserDto findByName(String name) {
        logger.debug("findByName(): name = " , name);
        return mapper.map(userRepository.findByName(name), UserDto.class);
    }

    public Boolean exists(Long id) {
        logger.debug("exists(): id = ",id);
        return userRepository.exists(id);
    }
    
    public Long count(){
        logger.debug("count()");
        return userRepository.count();
    }

    @Transactional
    public UserDto save(UserDto userDto) {
        logger.debug("save()");
        User user = mapper.map(userDto, User.class);
        user.setPassword(userDto.getMobile());
        user = userRepository.save(user);
        return mapper.map(user, UserDto.class);
    }

    @Transactional
    public UserDto update(UserDto userDto) {
        logger.debug("update()");
        User user = userRepository.findOne(userDto.getId());

        if (userDto.getEmail() != null) user.setEmail(userDto.getEmail());
        if (userDto.getName() != null) user.setName(userDto.getName());
        if (userDto.getMobile() != null) user.setMobile(userDto.getMobile());

        return mapper.map(user, UserDto.class);
    }
    
    @Transactional
    public void delete(Long id) {
        logger.debug("delete(): id = {}",id);
        userRepository.delete(id);
    }
    
}
