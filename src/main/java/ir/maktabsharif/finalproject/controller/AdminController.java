package ir.maktabsharif.finalproject.controller;

import ir.maktabsharif.finalproject.dto.*;
import ir.maktabsharif.finalproject.exception.CustomerOperationException;
import ir.maktabsharif.finalproject.exception.SpecialistOperationException;
import ir.maktabsharif.finalproject.exception.UserOperationException;
import ir.maktabsharif.finalproject.service.AdminService;
import ir.maktabsharif.finalproject.service.CustomerDisplayService;
import ir.maktabsharif.finalproject.service.SpecialistDisplayService;
import ir.maktabsharif.finalproject.service.UserDisplayService;
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
    private final SpecialistDisplayService specialistDisplayService;
    private final AdminService adminService;
    private final CustomerDisplayService customerDisplayService;
    private final UserDisplayService userDisplayService;
    private final MapperUtil mapperUtil;

    @PatchMapping("PATCH/admin/specialist/{specialistFirstName}/{specialistLastName}")
    ResponseEntity<Void> approveSpecialist(@PathVariable String specialistFirstName,@PathVariable String specialistLastName) throws SpecialistOperationException {
        adminService.approveSpecialist(specialistFirstName,specialistLastName);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("GET/admin/user/firstnameASC")
    List<UserDto> displayByFirstNameASC() throws UserOperationException {
        return userDisplayService.findAllUserByLastNameAsc().stream().map(mapperUtil::convertToDto).toList();
    }

    @GetMapping("GET/admin/user/firstnameDESC")
    List<UserDto> displayByLastnameASC() throws UserOperationException {
        return userDisplayService.findAllUserByLastNameAsc().stream().map(mapperUtil::convertToDto).toList();
    }

    @GetMapping("GET/admin/customer/creditASC")
    List<CustomerDto> displayByCreditASC() throws CustomerOperationException {
        return customerDisplayService.displayByCreditASC().stream().map(mapperUtil::convertToDto).toList();
    }

    @GetMapping("GET/admin/customer/creditDESC")
    List<CustomerDto> displayByCreditDESC() throws CustomerOperationException {
        return customerDisplayService.displayByCreditDESC().stream().map(mapperUtil::convertToDto).toList();
    }

    @GetMapping("GET/admin/specialist/scoreASC")
    List<SpecialistDto> displayByScoreASC() throws SpecialistOperationException {
        return specialistDisplayService.displayByCreditASC().stream().map(mapperUtil::convertToDto).toList();
    }

    @GetMapping("GET/admin/specialist/scoreDESC")
    List<SpecialistDto> displayByScoreDESC() throws SpecialistOperationException {
        return specialistDisplayService.displayByCreditDESC().stream().map(mapperUtil::convertToDto).toList();
    }

    @GetMapping("GET/admin/user/emailASC")
    List<UserDto> displayByEmailASC() throws UserOperationException {
        return userDisplayService.findAllUserByEmailAsc().stream().map(mapperUtil::convertToDto).toList();
    }

    @GetMapping("GET/admin/user/emailDESC")
    List<UserDto> displayByEmailDESC() throws UserOperationException {
        return userDisplayService.findAllUserByEmailDesc().stream().map(mapperUtil::convertToDto).toList();
    }

}
