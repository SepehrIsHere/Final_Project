package ir.maktabsharif.finalproject.controller;

import ir.maktabsharif.finalproject.dto.CommentDto;
import ir.maktabsharif.finalproject.dto.OrderDto;
import ir.maktabsharif.finalproject.dto.SpecialistDto;
import ir.maktabsharif.finalproject.dto.SuggestionDto;
import ir.maktabsharif.finalproject.exception.*;
import ir.maktabsharif.finalproject.service.*;
import ir.maktabsharif.finalproject.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("GET/specialists")
    List<SpecialistDto> getAllSpecialists() throws SpecialistOperationException {
        return specialistService.findAll().stream().map(mapperUtil::convertToDto).toList();
    }

    @PostMapping("POST/specialist")
    SpecialistDto createSpecialist(@RequestBody SpecialistDto specialistDto) throws SpecialistOperationException {
        specialistService.signUp(specialistDto);
        return specialistDto;
    }

    @DeleteMapping("DELETE/specialist/{firstname}/{lastname}")
    ResponseEntity<Void> deleteSpecialist(@PathVariable String firstname, @PathVariable String lastname) throws SpecialistOperationException {
        specialistService.delete(specialistService.findByFirstNameAndLastName(firstname, lastname));
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("PATCH/specialists/{firstname}/{lastname}/new/{name}")
    ResponseEntity<Void>  addSpecialistToSubTask(@PathVariable String firstname, @PathVariable String lastname, @PathVariable String name) throws SpecialistOperationException, SubTaskOperationException {
        adminService.addSubTaskToSpecialist(firstname,lastname,name);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("PATCH/specialists/{firstname}/{lastname}/remove/{name}")
    ResponseEntity<Void>  removeSpecialistFromSubTask(@PathVariable String firstname, @PathVariable String lastname, @PathVariable String name) throws SpecialistOperationException, SubTaskOperationException {
        adminService.removeSubTaskFromSpecialist(firstname,lastname,name);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("GET/orders/waiting-for-selection")
    List<OrderDto> findWaitingForSelectionOrders() throws OrderOperationException {
        return orderService.findWaitingForSelectionOrders().stream().map(mapperUtil::convertToDto).toList();
    }

    @PostMapping("POST/specialist/suggestion")
    SuggestionDto sendSuggestion(@RequestBody SuggestionDto suggestionDto) throws SuggestionOperationException, InvalidFieldValueException, SpecialistOperationException, SubTaskOperationException {
        return specialistSuggestionService.createSuggestionForOrder(suggestionDto);
    }

    @GetMapping("GET/comments/{specialistFirstName}/{specialistLastName}")
    List<CommentDto> displayComments(@PathVariable String specialistFirstName,@PathVariable String specialistLastName){
        return commentService.findBySpecialist(specialistFirstName, specialistLastName)
                .stream()
                .map(mapperUtil::convertToDto)
                .toList();
    }
}
