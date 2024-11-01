package ir.maktabsharif.finalproject.controller;

import ir.maktabsharif.finalproject.dto.CommentDto;
import ir.maktabsharif.finalproject.dto.OrderDto;
import ir.maktabsharif.finalproject.dto.SpecialistDto;
import ir.maktabsharif.finalproject.dto.SuggestionDto;
import ir.maktabsharif.finalproject.entities.Specialist;
import ir.maktabsharif.finalproject.exception.*;
import ir.maktabsharif.finalproject.service.*;
import ir.maktabsharif.finalproject.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SpecialistController {
    private final SpecialistService specialistService;
    private final SpecialistSuggestionService specialistSuggestionService;
    private final OrderService orderService;
    private final CommentService commentService;
    private final AdminService adminService;
    private final MapperUtil mapperUtil;

    @GetMapping("admin/GET/specialists")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    List<SpecialistDto> getAllSpecialists() throws SpecialistOperationException {
        return specialistService.findAll().stream().map(mapperUtil::convertToDto).toList();
    }

    @PostMapping("register-specialist")
    SpecialistDto createSpecialist(@RequestBody SpecialistDto specialistDto) throws SpecialistOperationException {
        specialistService.signUp(specialistDto);
        return specialistDto;
    }

    @DeleteMapping("admin/delete/specialist/{firstname}/{lastname}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    ResponseEntity<Void> deleteSpecialist(@PathVariable String firstname, @PathVariable String lastname) throws SpecialistOperationException {
        specialistService.delete(specialistService.findByFirstNameAndLastName(firstname, lastname));
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("admin/specialists/{firstname}/{lastname}/add/{name}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    ResponseEntity<String> addSpecialistToSubTask(@PathVariable String firstname, @PathVariable String lastname, @PathVariable String name) throws SpecialistOperationException {
        adminService.addSubTaskToSpecialist(firstname, lastname, name);
        return ResponseEntity.ok("Specialist successfully added ");
    }

    @PatchMapping("admin/specialists/{firstname}/{lastname}/remove/{name}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    ResponseEntity<String> removeSpecialistFromSubTask(@PathVariable String firstname, @PathVariable String lastname, @PathVariable String name) throws SpecialistOperationException {
        adminService.removeSubTaskFromSpecialist(firstname, lastname, name);
        return ResponseEntity.ok("Specialist have been removed from subtask");
    }

    @GetMapping("specialist/GET/orders/waiting-for-selection")
    @PreAuthorize("hasRole('SPECIALIST')")
    List<OrderDto> findWaitingForSelectionOrders() throws OrderOperationException {
        return orderService.findWaitingForSelectionOrders().stream().map(mapperUtil::convertToDto).toList();
    }

    @PostMapping("specialist/send/suggestion")
    @PreAuthorize("hasRole('SPECIALIST')")
    SuggestionDto sendSuggestion(@RequestBody SuggestionDto suggestionDto) throws SuggestionOperationException, InvalidFieldValueException, SpecialistOperationException, SubTaskOperationException {
        return specialistSuggestionService.createSuggestionForOrder(suggestionDto);
    }

    @GetMapping("specialist/GET/score/{specialistFirstName}/{specialistLastName}")
    String displaySpecialistScore(@PathVariable String specialistFirstName,@PathVariable String specialistLastName) throws SpecialistOperationException {
        Specialist specialist = specialistService.findByFirstNameAndLastName(specialistFirstName,specialistLastName);
        return specialist.getScore().toString();
    }

    @GetMapping("specialist/GET/comments/{specialistFirstName}/{specialistLastName}")
    @PreAuthorize("hasRole('SPECIALIST')")
    List<CommentDto> displayComments(@PathVariable String specialistFirstName, @PathVariable String specialistLastName) {
        return commentService.findBySpecialist(specialistFirstName, specialistLastName)
                .stream()
                .map(mapperUtil::convertToDto)
                .toList();
    }
}
