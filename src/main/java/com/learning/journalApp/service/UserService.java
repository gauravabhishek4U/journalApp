package com.learning.journalApp.service;

import com.learning.journalApp.entity.JournalEntry;
import com.learning.journalApp.entity.User;
import com.learning.journalApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // method to create user
    public void createUser(User user){
        try{
            userRepository.save(user);
        } catch (Exception e) {
            log.error("Exception in creating new user :: ", e);
        }
    }

    // method to fetch all users
    public List<User> getAllUsers(){
        List<User> allUsers = userRepository.findAll();
        return allUsers;
    }

    //method to fetch user by Id
    public Optional<User>  getUserById(ObjectId userId){
        return Optional.ofNullable(userRepository.findById(userId).orElse(null));
    }

    // method to update user
    public void updateUser(User user){
        userRepository.save(user);
    }

    // method to delete user by id
    public void deleteUserById(ObjectId id){
        userRepository.deleteById(id);
    }

    // method to find user by userName
    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }
}
