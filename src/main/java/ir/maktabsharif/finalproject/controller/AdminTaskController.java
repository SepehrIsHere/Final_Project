package ir.maktabsharif.finalproject.controller;

import ir.maktabsharif.finalproject.dto.TaskDto;
import ir.maktabsharif.finalproject.exception.TaskOperationException;
import ir.maktabsharif.finalproject.service.TaskService;
import ir.maktabsharif.finalproject.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminTaskController {
    private final TaskService taskService;
    private final MapperUtil mapperUtil;

    @GetMapping("admin/tasks/displayAll")
//    @PreAuthorize("hasRole('ADMINISTRATOR')")
    List<TaskDto> displayTasks() throws TaskOperationException {
        return taskService.findAll().stream().map(mapperUtil::convertToDto).toList();
    }

    @PostMapping("admin/tasks/create")
//    @PreAuthorize("hasRole('ADMINISTRATOR')")
    TaskDto createTask(@RequestBody TaskDto taskDto) throws TaskOperationException {
        taskService.add(mapperUtil.convertToEntity(taskDto));
        return taskDto;
    }

    @PostMapping("admin/tasks/find/{taskName}")
//    @PreAuthorize("hasRole('ADMINISTRATOR')")
    TaskDto findByTaskName(@PathVariable String taskName) throws TaskOperationException {
        return mapperUtil.convertToDto(taskService.findByName(taskName));
    }

}
