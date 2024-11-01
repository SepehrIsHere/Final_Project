package ir.maktabsharif.finalproject.controller;

import ir.maktabsharif.finalproject.dto.SubTaskDto;
import ir.maktabsharif.finalproject.entities.SubTask;
import ir.maktabsharif.finalproject.entities.Task;
import ir.maktabsharif.finalproject.exception.SubTaskOperationException;
import ir.maktabsharif.finalproject.exception.TaskOperationException;
import ir.maktabsharif.finalproject.service.SubTaskService;
import ir.maktabsharif.finalproject.service.TaskService;
import ir.maktabsharif.finalproject.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SubTaskController {
    private final SubTaskService subTaskService;
    private final TaskService taskService;
    private final MapperUtil mapperUtil;

    @GetMapping("admin/all/subtasks")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    List<SubTaskDto> getSubTasks() throws SubTaskOperationException {
        return subTaskService.findAll().stream().map(mapperUtil::convertToDto).toList();
    }

    @PostMapping("admin/add/subtasks")
    ResponseEntity<Void> createSubTask(@RequestBody SubTaskDto subTaskDto) throws SubTaskOperationException, TaskOperationException {
       Task task = taskService.findByName(subTaskDto.getTaskName());
        SubTask subTask = mapperUtil.convertToEntity(subTaskDto);
        subTask.setTask(task);
        subTaskService.add(subTask);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("admin/subtasks/related/{taskName}")
    List<SubTask> getRelatedSubTasks(@PathVariable String taskName) throws SubTaskOperationException {
        return subTaskService.findSubTaskByTask(taskName);
    }

    @GetMapping("admin/subtasks/by-name/{subTaskName}")
    SubTaskDto findBySubTaskName(@PathVariable String subTaskName) throws SubTaskOperationException {
        return mapperUtil.convertToDto(subTaskService.findByName(subTaskName));
    }

    @DeleteMapping("admin/DELETE/subtasks/{subTaskName}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    ResponseEntity<Void> deleteSubTask(@PathVariable String subTaskName) throws SubTaskOperationException {
        subTaskService.delete(subTaskService.findByName(subTaskName));
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("admin/edit-price/{subTaskName}/basePrice/{basePrice}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    ResponseEntity<Void> changeBasePrice(@PathVariable String subTaskName, @PathVariable Double basePrice) throws SubTaskOperationException {
        subTaskService.changeBasePrice(subTaskName, basePrice);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("admin/edit-description/{subTaskName}/description/{description}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    ResponseEntity<Void> changeDescription(@PathVariable String subTaskName, @PathVariable String description) throws SubTaskOperationException {
        subTaskService.changeDescription(subTaskName, description);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("admin/task/related-subtasks/{taskName}")
    List<SubTaskDto> findRelatedSubTasks(@PathVariable String taskName) throws SubTaskOperationException {
        return subTaskService.findSubTaskByTask(taskName)
                .stream()
                .map(mapperUtil::convertToDto)
                .toList();
    }
}
