package hexlet.code.service;

import hexlet.code.dto.dtoTask.TaskCreateDTO;
import hexlet.code.dto.dtoTask.TaskDTO;
import hexlet.code.dto.dtoTask.TaskParamsDTO;
import hexlet.code.dto.dtoTask.TaskUpdateDTO;

import java.util.List;


public interface TaskService {
    List<TaskDTO> getAllTasks(TaskParamsDTO params);
    TaskDTO createTask(TaskCreateDTO taskData);
    TaskDTO getTaskById(Long id);
    TaskDTO updateTask(TaskUpdateDTO taskData, Long id);
    void deleteTask(Long id);
}
