package hexlet.code.service;

import hexlet.code.dto.dtoTaskStatus.TaskStatusCreateDTO;
import hexlet.code.dto.dtoTaskStatus.TaskStatusDTO;
import hexlet.code.dto.dtoTaskStatus.TaskStatusUpdateDTO;

import java.util.List;


public interface TaskStatusService {
    List<TaskStatusDTO> getAllTaskStatus();
    TaskStatusDTO createTaskStatus(TaskStatusCreateDTO taskStatusData);
    TaskStatusDTO getTaskStatusById(Long id);
    TaskStatusDTO updateTaskStatus(TaskStatusUpdateDTO taskStatusData, Long id);
    void deleteTaskStatus(Long id);
}
