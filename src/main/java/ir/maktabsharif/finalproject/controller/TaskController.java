package ir.maktabsharif.finalproject.controller;

import ir.maktabsharif.finalproject.dto.TaskDto;
import ir.maktabsharif.finalproject.exception.TaskOperationException;
import ir.maktabsharif.finalproject.service.TaskService;
import ir.maktabsharif.finalproject.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final MapperUtil mapperUtil;

    @GetMapping("admin/find-all/tasks")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public List<TaskDto> getTasks() throws TaskOperationException {
        return taskService.findAll().stream().map(mapperUtil::convertToDto).toList();
    }

    @PostMapping("admin/create/tasks")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public TaskDto createTask(@RequestBody TaskDto taskDto) throws TaskOperationException {
        taskService.add(mapperUtil.convertToEntity(taskDto));
        return taskDto;
    }

    @GetMapping("admin/tasks/{taskName}")
    TaskDto findTaskByName(@PathVariable String taskName) throws TaskOperationException {
        return mapperUtil.convertToDto(taskService.findByName(taskName));
    }

    @DeleteMapping("admin/remove/tasks/{taskName}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    ResponseEntity<String> deleteTask(@PathVariable String taskName) throws TaskOperationException {
        taskService.delete(taskService.findByName(taskName));
        return ResponseEntity.ok("Task deleted successfully");
    }
}
