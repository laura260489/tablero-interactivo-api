package com.laurarojas.tablerointeractivoapi.service;

import com.laurarojas.tablerointeractivoapi.dto.CreateTaskDTO;
import com.laurarojas.tablerointeractivoapi.dto.ResponseMessageDTO;
import com.laurarojas.tablerointeractivoapi.dto.TaskWithProjectInfoDTO;
import com.laurarojas.tablerointeractivoapi.dto.UpdateTaskStatusDTO;
import com.laurarojas.tablerointeractivoapi.entity.BoardEntity;
import com.laurarojas.tablerointeractivoapi.entity.TaskEntity;
import com.laurarojas.tablerointeractivoapi.entity.UserEntity;
import com.laurarojas.tablerointeractivoapi.exception.ApiException;
import com.laurarojas.tablerointeractivoapi.repository.BoardRepository;
import com.laurarojas.tablerointeractivoapi.repository.TaskRepository;
import com.laurarojas.tablerointeractivoapi.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, 
                      BoardRepository boardRepository,
                      UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }

    public List<TaskWithProjectInfoDTO> getTasksByProjectName(String projectName) {
        List<TaskEntity> tasks = taskRepository.findTasksByProjectName(projectName);
        
        if (tasks.isEmpty()) {
            throw new ApiException(
                    String.format("No se encontraron tareas para el proyecto: %s", projectName),
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase()
            );
        }

        return tasks.stream()
                .map(this::convertToTaskWithProjectInfoDTO)
                .collect(Collectors.toList());
    }

    private TaskWithProjectInfoDTO convertToTaskWithProjectInfoDTO(TaskEntity task) {
        TaskWithProjectInfoDTO dto = new TaskWithProjectInfoDTO();
        
        dto.setTaskId(task.getId());
        dto.setTaskName(task.getName());
        dto.setPriority(task.getPriority());
        dto.setEstimation(task.getEstimation());
        dto.setStartDate(task.getStartDate());
        dto.setEndDate(task.getEndDate());
        dto.setStatus(task.getStatus());
        dto.setCreatedAt(task.getCreatedAt());
        dto.setUpdatedAt(task.getUpdateAt());
        
        dto.setBoardId(task.getBoard().getId());
        dto.setBoardName(task.getBoard().getName());
        
        dto.setProjectId(task.getBoard().getProject().getId());
        dto.setProjectName(task.getBoard().getProject().getName());
        
        dto.setUserId(task.getUser().getId());
        dto.setUserFirstName(task.getUser().getFirstName());
        dto.setUserLastName(task.getUser().getLastName());
        dto.setUserEmail(task.getUser().getEmail());
        
        return dto;
    }

    public ResponseMessageDTO createTask(CreateTaskDTO createTaskDTO) {
        BoardEntity board = boardRepository.findById(createTaskDTO.getBoardId())
                .orElseThrow(() -> new ApiException(
                        String.format("Tablero no encontrado con ID: %s", createTaskDTO.getBoardId()),
                        HttpStatus.NOT_FOUND.value(),
                        HttpStatus.NOT_FOUND.getReasonPhrase()
                ));

        UserEntity user = userRepository.findById(createTaskDTO.getUserId())
                .orElseThrow(() -> new ApiException(
                        String.format("Usuario no encontrado con ID: %s", createTaskDTO.getUserId()),
                        HttpStatus.NOT_FOUND.value(),
                        HttpStatus.NOT_FOUND.getReasonPhrase()
                ));

        TaskEntity task = new TaskEntity();
        task.setName(createTaskDTO.getName());
        task.setPriority(createTaskDTO.getPriority());
        task.setEstimation(createTaskDTO.getEstimation());
        task.setStartDate(createTaskDTO.getStartDate());
        task.setEndDate(createTaskDTO.getEndDate());
        task.setStatus(createTaskDTO.getStatus() != null ? createTaskDTO.getStatus() : "TODO");
        task.setBoard(board);
        task.setUser(user);

        taskRepository.save(task);

        return new ResponseMessageDTO("Tarea creada exitosamente", HttpStatus.CREATED.value());
    }

    public ResponseMessageDTO updateTaskStatus(String taskId, UpdateTaskStatusDTO updateTaskStatusDTO) {
        TaskEntity task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ApiException(
                        String.format("Tarea no encontrada con ID: %s", taskId),
                        HttpStatus.NOT_FOUND.value(),
                        HttpStatus.NOT_FOUND.getReasonPhrase()
                ));

        task.setStatus(updateTaskStatusDTO.getStatus());
        taskRepository.save(task);

        return new ResponseMessageDTO("Estado de la tarea actualizado exitosamente", HttpStatus.OK.value());
    }

    public TaskWithProjectInfoDTO getTaskById(String taskId) {
        TaskEntity task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ApiException(
                        String.format("Tarea no encontrada con ID: %s", taskId),
                        HttpStatus.NOT_FOUND.value(),
                        HttpStatus.NOT_FOUND.getReasonPhrase()
                ));

        return convertToTaskWithProjectInfoDTO(task);
    }
}