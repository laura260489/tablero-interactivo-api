package com.laurarojas.tablerointeractivoapi.controller;

import com.laurarojas.tablerointeractivoapi.dto.CreateTaskDTO;
import com.laurarojas.tablerointeractivoapi.dto.ResponseMessageDTO;
import com.laurarojas.tablerointeractivoapi.dto.TaskWithProjectInfoDTO;
import com.laurarojas.tablerointeractivoapi.dto.UpdateTaskStatusDTO;
import com.laurarojas.tablerointeractivoapi.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/project/{projectName}")
    public ResponseEntity<List<TaskWithProjectInfoDTO>> getTasksByProjectName(@PathVariable String projectName) {
        List<TaskWithProjectInfoDTO> tasks = taskService.getTasksByProjectName(projectName);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping
    public ResponseEntity<ResponseMessageDTO> createTask(@RequestBody CreateTaskDTO createTaskDTO) {
        ResponseMessageDTO response = taskService.createTask(createTaskDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskWithProjectInfoDTO> getTaskById(@PathVariable String taskId) {
        TaskWithProjectInfoDTO task = taskService.getTaskById(taskId);
        return ResponseEntity.ok(task);
    }

    @PutMapping("/{taskId}/status")
    public ResponseEntity<ResponseMessageDTO> updateTaskStatus(@PathVariable String taskId, 
                                                             @RequestBody UpdateTaskStatusDTO updateTaskStatusDTO) {
        ResponseMessageDTO response = taskService.updateTaskStatus(taskId, updateTaskStatusDTO);
        return ResponseEntity.ok(response);
    }
}