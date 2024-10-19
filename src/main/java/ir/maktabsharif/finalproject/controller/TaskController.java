package ir.maktabsharif.finalproject.controller;

import ir.maktabsharif.finalproject.dto.TaskDto;
import ir.maktabsharif.finalproject.exception.TaskOperationException;
import ir.maktabsharif.finalproject.service.TaskService;
import ir.maktabsharif.finalproject.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final MapperUtil mapperUtil;

    @GetMapping("GET/tasks")
    public List<TaskDto> getTasks() throws TaskOperationException {
        return taskService.findAll().stream().map(mapperUtil::convertToDto).toList();
    }

    @PostMapping("/tasks")
    public TaskDto createTask(@RequestBody TaskDto taskDto) throws TaskOperationException {
        taskService.add(mapperUtil.convertToEntity(taskDto));
        return taskDto;
    }

    @GetMapping("/tasks/{taskName}")
    TaskDto findTaskByName(@PathVariable String taskName) throws TaskOperationException {
        return mapperUtil.convertToDto(taskService.findByName(taskName));
    }

    @DeleteMapping("/tasks/{taskName}")
    ResponseEntity<Void> deleteTask(@PathVariable String taskName) throws TaskOperationException {
        taskService.delete(taskService.findByName(taskName));
        return ResponseEntity.noContent().build();
    }
}
