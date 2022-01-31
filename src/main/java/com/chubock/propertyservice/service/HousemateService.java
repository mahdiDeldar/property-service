package com.chubock.propertyservice.service;

import com.chubock.propertyservice.entity.User;
import com.chubock.propertyservice.exception.UserNotFoundException;
import com.chubock.propertyservice.model.ModelFactory;
import com.chubock.propertyservice.model.UserModel;
import com.chubock.propertyservice.model.UserSearchModel;
import com.chubock.propertyservice.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@Service
public class HousemateService {

    private final UserRepository userRepository;

    private final UserService userService;

    public HousemateService(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Transactional(readOnly = true)
    public Page<UserModel> getHousemates(UserSearchModel model, Pageable pageable) {
        return userRepository.findAll(model, pageable)
                .map(UserModel::of);
    }

    @Transactional(readOnly = true)
    public UserModel getHousemate(String id) {
        User user = userService.get(id);
        UserModel model = ModelFactory.of(user, UserModel.class);
        model.setLanguages(new HashSet<>(user.getLanguages()));
        return model;
    }
}
