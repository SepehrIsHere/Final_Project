package ir.maktabsharif.finalproject.controller;

import ir.maktabsharif.finalproject.dto.TaskDto;
import ir.maktabsharif.finalproject.exception.TaskOperationException;
import ir.maktabsharif.finalproject.service.TaskService;
import ir.maktabsharif.finalproject.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminTaskController {
    private final TaskService taskService;
    private final MapperUtil mapperUtil;

    @GetMapping("GET/admin/tasks")
    List<TaskDto> displayTasks() throws TaskOperationException {
        return taskService.findAll().stream().map(mapperUtil::convertToDto).toList();
    }

    @PostMapping("POST/admin/tasks")
    TaskDto createTask(@RequestBody TaskDto taskDto) throws TaskOperationException {
        taskService.add(mapperUtil.convertToEntity(taskDto));
        return taskDto;
    }

    @PostMapping("POST/admin/tasks/{taskName}")
    TaskDto findByTaskName(@PathVariable String taskName) throws TaskOperationException {
        return mapperUtil.convertToDto(taskService.findByName(taskName));
    }

}