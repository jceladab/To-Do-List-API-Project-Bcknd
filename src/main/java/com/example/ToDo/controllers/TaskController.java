package com.example.ToDo.controllers;

import com.example.ToDo.domain.Task;
import com.example.ToDo.services.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/todos")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<?> postTask(@RequestBody Map<String,String> request,
            @RequestHeader("Authorization") String authHeader){

        //extraer el token del header
        String token = authHeader.replace("Bearer ", "");

        //asignamos los datos que llegan en el map desde la solicitud
        String title = request.get("title");
        String description = request.get("description");

        //creamos la tarea
        Task task = taskService.createTask(title,description,token);
        return ResponseEntity.ok(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putTask(
            @PathVariable Long id,
            @RequestBody Map<String,String> request,
            @RequestHeader("Authorization") String authHeader){

        //extraemos el token del header
        String token = authHeader.replace("Bearer ","").trim();

        //asignamos los datos que llegan en el map de la solicitud
        String title = request.get("title");
        String description = request.get("description");

        //llamamos al servicio para actualizar la tarea
        Task task = taskService.uptadeTaks(id, title, description, token);
        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader){

        //obtenemos el token del header
        String token = authHeader.replace("Bearer ", "").trim();

        //llamamos al servicio para eliminar la tarea
        taskService.popTask(id,token);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<?> getTasks(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit){

        //obtenemos el token del header
        String token = authHeader.replace("Bearer ", "").trim();

        //llamamos al método que obtiene las tareas
        Page<Task> taskPage = taskService.requestTasks(token, page, limit);

        Map<String,Object> response = Map.of(
                "data", taskPage.getContent(),
                "page", page,
                "limit", limit,
                "total", taskPage.getTotalElements()
        );

        return ResponseEntity.ok(response);
    }
}
