package ir.maktabsharif.finalproject.controller;

import ir.maktabsharif.finalproject.dto.*;
import ir.maktabsharif.finalproject.enumerations.Role;
import ir.maktabsharif.finalproject.exception.CustomerOperationException;
import ir.maktabsharif.finalproject.exception.SpecialistOperationException;
import ir.maktabsharif.finalproject.exception.UserOperationException;
import ir.maktabsharif.finalproject.repository.CustomerRepository;
import ir.maktabsharif.finalproject.repository.SpecialistRepository;
import ir.maktabsharif.finalproject.repository.UsersRepository;
import ir.maktabsharif.finalproject.service.AdminDisplayService;
import ir.maktabsharif.finalproject.service.AdminService;
import ir.maktabsharif.finalproject.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    private final AdminDisplayService adminDisplayService;
    private final MapperUtil mapperUtil;

    @PatchMapping("PATCH/admin/specialist/{specialistFirstName}/{specialistLastName}")
    ResponseEntity<Void> approveSpecialist(@PathVariable String specialistFirstName, @PathVariable String specialistLastName) throws SpecialistOperationException {
        adminService.approveSpecialist(specialistFirstName, specialistLastName);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("GET/admin/user/firstnameASC")
    List<UserDto> displayByFirstNameASC() throws UserOperationException {
        return adminDisplayService.findAllUserByLastNameAsc().stream().map(mapperUtil::convertToDto).toList();
    }

    @GetMapping("GET/admin/user/firstnameDESC")
    List<UserDto> displayByLastnameASC() throws UserOperationException {
        return adminDisplayService.findAllUserByLastNameAsc().stream().map(mapperUtil::convertToDto).toList();
    }

    @GetMapping("GET/admin/customer/creditASC")
    List<CustomerDto> displayByCreditASC() throws CustomerOperationException {
        return adminDisplayService.displayByCreditASC().stream().map(mapperUtil::convertToDto).toList();
    }

    @GetMapping("GET/admin/customer/creditDESC")
    List<CustomerDto> displayByCreditDESC() throws CustomerOperationException {
        return adminDisplayService.displayByCreditDESC().stream().map(mapperUtil::convertToDto).toList();
    }

    @GetMapping("GET/admin/specialist/scoreASC")
    List<SpecialistDto> displayByScoreASC() throws SpecialistOperationException, CustomerOperationException {
        return adminDisplayService.displayByScoreASC().stream().map(mapperUtil::convertToDto).toList();
    }

    @GetMapping("GET/admin/specialist/scoreDESC")
    List<SpecialistDto> displayByScoreDESC() throws SpecialistOperationException {
        return adminDisplayService.displayByScoreDESC().stream().map(mapperUtil::convertToDto).toList();
    }

    @GetMapping("GET/admin/user/emailASC")
    List<UserDto> displayByEmailASC() throws UserOperationException {
        return adminDisplayService.findAllUserByEmailAsc().stream().map(mapperUtil::convertToDto).toList();
    }

    @GetMapping("GET/admin/user/emailDESC")
    List<UserDto> displayByEmailDESC() throws UserOperationException {
        return adminDisplayService.findAllUserByEmailDesc().stream().map(mapperUtil::convertToDto).toList();
    }

    @GetMapping("GET/admin/user/role?customer")
    List<UserDto> displayByCustomerRole() {
        return adminDisplayService.searchUsers(null, null, null, null, null, Role.CUSTOMER, null, null)
                .stream()
                .map(mapperUtil::convertToDto)
                .toList();
    }

    @GetMapping("GET/admin/user/role?specialist")
    List<UserDto> displayBySpecialistRole() {
        return adminDisplayService.searchUsers(null, null, null, null, null, Role.SPECIALIST, null, null)
                .stream()
                .map(mapperUtil::convertToDto)
                .toList();
    }

    @GetMapping("GET/admin/specialist/ASC/{subTaskName}")
    List<SpecialistDto> displayBySubTaskAndScoreASC(@PathVariable String subTaskName) {
        return adminDisplayService.searchSpecialists(null, null, null, null, 0, subTaskName, "score", true)
                .stream()
                .map(mapperUtil::convertToDto)
                .toList();
    }

    @GetMapping("GET/admin/specialist/DESC/{subTaskName}")
    List<SpecialistDto> displayBySubTaskAndScoreDESC(@PathVariable String subTaskName) {
        return adminDisplayService.searchSpecialists(null, null, null, null, 0, subTaskName, "score", false)
                .stream()
                .map(mapperUtil::convertToDto)
                .toList();
    }

    @GetMapping()
    List<CustomerDto> displayByCustomerAndScoreASC() {
        return adminDisplayService.searchCustomers(null, null, null, null, 0, "credit", true)
                .stream()
                .map(mapperUtil::convertToDto)
                .toList();
    }

    @GetMapping()
    List<CustomerDto> displayByCustomerAndScoreDESC() {
        return adminDisplayService.searchCustomers(null, null, null, null, 0, "credit", false)
                .stream()
                .map(mapperUtil::convertToDto)
                .toList();
    }
}
