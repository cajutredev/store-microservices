package com.store.user_service.service;

import com.store.user_service.dto.UserDto;
import com.store.user_service.entity.User;
import com.store.user_service.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public List<UserDto> getUsers() {

        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    public UserDto createUser(UserDto userDto) {
//        user.setId(UUID.randomUUID().toString().split("-")[0]);
//        users.add(user);

        User user = userRepository.save(modelMapper.map(userDto, User.class));
        return modelMapper.map(user, UserDto.class);
    }

    public UserDto findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        return modelMapper.map(user, UserDto.class);
    }

    public Boolean updateUser(UserDto userDto) {
//        return users.stream()
//                .filter(u -> u.getId().equals(userDto.getId()))
//                .findFirst()
//                .map(user1 -> {
//                    user1.setFirstName(userDto.getFirstName());
//                    user1.setLastName(userDto.getLastName());
//                    return user1;
//                });
        return userRepository.findById(userDto.getId())
                .map(user1 -> {
                        modelMapper.map(userDto, user1);
//                    user1.setLastName(userDto.getLastName());
//                    user1.setFirstName(userDto.getFirstName());
                    userRepository.save(user1);
                    return true;
                }).orElse(false);
    }
}
