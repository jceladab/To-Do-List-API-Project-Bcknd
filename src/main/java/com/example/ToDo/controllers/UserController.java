package com.example.ToDo.controllers;

import com.example.ToDo.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> postUser(@RequestBody Map<String,String> request){
        //asignamos los datos que llegan en el map desde la solicitud
        String name = request.get("name");
        String email = request.get("email");
        String password = request.get("password");

        //creamos el token el cual devolveremos
        String token = userService.registerUser(name,email,password);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/login")
    public ResponseEntity<?> postLogin(@RequestBody Map<String,String> request){
        //asignamos los datos que llegan en el map desde la solicitud
        String email = request.get("email");
        String password = request.get("password");

        String token = userService.loginUser(email, password);
        return ResponseEntity.ok(token);
    }

}
