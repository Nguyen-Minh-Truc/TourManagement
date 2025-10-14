package com.J2EE.TourManagement.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.J2EE.TourManagement.Model.User;
import com.J2EE.TourManagement.Service.UserSer;
import com.J2EE.TourManagement.Util.annotation.ApiMessage;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/v1")
public class UserController {
    private static UserSer userSer;
    public UserController(UserSer userSer){
        this.userSer = userSer;
    }

    @PostMapping("/users/create")
    @ApiMessage("Thêm người dùng thành công.")
    public ResponseEntity<User> postNewUser(@RequestBody @Valid User newUser) {
        User user = this.userSer.handleSaveUser(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
    
}
