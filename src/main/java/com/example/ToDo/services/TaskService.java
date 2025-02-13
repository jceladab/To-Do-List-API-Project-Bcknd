package com.example.ToDo.services;

import com.example.ToDo.domain.Task;
import com.example.ToDo.domain.User;
import com.example.ToDo.repository.TaskRepository;
import com.example.ToDo.repository.UserRepository;
import com.example.ToDo.security.Jwt;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    //Inyección de dependencias por constructor
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final Jwt jwt;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository, Jwt jwt) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.jwt = jwt;
    }

    public Task createTask(String title, String description, String token){
        //obtener correo del usuario
        String userEmail = jwt.extractEmail(token);


        //verificar que el token sea válido
        if(userEmail==null|| !jwt.validateToken(token,userEmail)){
            throw new RuntimeException("Unauthorized");
        }

        //verificar que exista el usuario
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuarion no encontrado"));

        //crea la tarea
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setUser(user);

        return taskRepository.save(task);
    }

    public Task uptadeTaks(Long id, String title, String description, String token){
        //obtener correo del usuario
        String userEmail = jwt.extractEmail(token);
        //verificar que el token sea válido
        if(userEmail==null|| !jwt.validateToken(token,userEmail)){
            throw new RuntimeException("Unauthorized");
        }

        //verifica que exista la tarea
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada")) ;

        //verifica que el correo del usuario que está logeado coincida con el del creador de la tarea
        if(!task.getUser().getEmail().equals(userEmail)){
            throw new RuntimeException("Forbidden");
        }

        //actualiza la tarea
        task.setTitle(title);
        task.setDescription(description);

        return taskRepository.save(task);
    }

    public void popTask(Long id, String token){
        //obtener correo del usuario
        String userEmail = jwt.extractEmail(token);
        //verificar que el token sea válido
        if(userEmail==null|| !jwt.validateToken(token,userEmail)){
            throw new RuntimeException("Unauthorized");
        }

        //verifica que exista la tarea
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada")) ;

        //verifica que el correo del usuario que está logeado coincida con el del creador de la tarea
        if(!task.getUser().getEmail().equals(userEmail)){
            throw new RuntimeException("Forbidden");
        }

        taskRepository.delete(task);

    }

    public Page<Task> requestTasks(String token, int page,int limit){
        //obtener el correo del usuario
        String userEmail = jwt.extractEmail(token);
        //verificar que el token sea válido
        if(userEmail==null|| !jwt.validateToken(token,userEmail)){
            throw new RuntimeException("Unauthorized");
        }

        //verificamos que el usuario exista
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Pageable pageable = PageRequest.of(page - 1, limit);
        return taskRepository.findByUser(user, pageable);

    }





}
