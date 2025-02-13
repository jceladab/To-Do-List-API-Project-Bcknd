package com.example.ToDo.services;

import com.example.ToDo.domain.User;
import com.example.ToDo.repository.UserRepository;
import com.example.ToDo.security.Jwt;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    //Inyección de dependencias por constructor
    private final UserRepository userRepository;
    private final Jwt jwt;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, Jwt jwt, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwt = jwt;
        this.passwordEncoder = passwordEncoder;
    }

    //Creación de usuario
    public String registerUser(String name, String email, String password){
        //verificar si el usuario ya existe
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()){
            throw new RuntimeException("El email del usuario ya se encuentra registrado");
        }

        //hashear la contraseña
        String hashedPassword = passwordEncoder.encode(password);

        //crear el usuario en la bd
        User user = new User();
        user.setName(name);
        user.setPassword(hashedPassword);
        user.setEmail(email);
        userRepository.save(user);

        //generar y devolver el token
        return jwt.generateToken(email);
    }

    //Verificación del usuario (Login)
    public String loginUser(String email, String password){
        //verifica el correo
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Credenciales"));

        //verifica la contraseña
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new BadCredentialsException("Credenciales incorrecta");
        }

        return jwt.generateToken(email);
    }


}
