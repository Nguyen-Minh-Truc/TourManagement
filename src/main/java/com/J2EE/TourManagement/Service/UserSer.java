package com.J2EE.TourManagement.Service;

import org.springframework.stereotype.Service;

import com.J2EE.TourManagement.Model.User;
import com.J2EE.TourManagement.Repository.UserRep;

@Service
public class UserSer {
    private static UserRep userRep;
    
    public UserSer(UserRep userRep){
        this.userRep = userRep;
    }

    public User handleSaveUser(User user){
        return this.userRep.save(user);
    }
}
