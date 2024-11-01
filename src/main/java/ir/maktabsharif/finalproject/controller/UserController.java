package ir.maktabsharif.finalproject.controller;

import ir.maktabsharif.finalproject.entities.Users;
import ir.maktabsharif.finalproject.exception.UserOperationException;
import ir.maktabsharif.finalproject.service.UserEditService;
import ir.maktabsharif.finalproject.service.UsersService;
import ir.maktabsharif.finalproject.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserEditService userEditService;
    private final MapperUtil mapperUtil;
    private final UsersService usersService;

    @GetMapping("/me")
    public ResponseEntity<Users> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users currentUser = (Users) authentication.getPrincipal();
        return ResponseEntity.ok(currentUser);
    }

    @GetMapping("/")
    public ResponseEntity<List<Users>> allUsers() throws UserOperationException {
        List <Users> users = usersService.findAll();
        return ResponseEntity.ok(users);
    }

    @PatchMapping("PATCH/users/firstname/{firstName}/{lastName}/new/{newFirstName}")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR','CUSTOMER','SPECIALIST')")
    ResponseEntity<String> changeFirstName(@PathVariable String firstName,
                                           @PathVariable String lastName,
                                           @PathVariable String newFirstName) throws UserOperationException {
        userEditService.changeFirstName(firstName, lastName, newFirstName);
        return ResponseEntity.ok("First name changed successfully");
    }

    @PatchMapping("PATCH/users/lastname/{firstName}/{lastName}/new/{newLastName}")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR','CUSTOMER','SPECIALIST')")
    ResponseEntity<String> changeLastName(@PathVariable String firstName,
                                          @PathVariable String lastName,
                                          @PathVariable String newLastName) throws UserOperationException {
        userEditService.changeLastName(firstName, lastName, newLastName);
        return ResponseEntity.ok("Last name changed successfully");
    }

    @PatchMapping("PATCH/users/username/{firstName}/{lastName}/new/{newUsername}")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR','CUSTOMER','SPECIALIST')")
    ResponseEntity<String> changeUsername(@PathVariable String firstName,
                                          @PathVariable String lastName,
                                          @PathVariable String newUsername) throws UserOperationException {
        userEditService.changeUsername(firstName, lastName, newUsername);
        return ResponseEntity.ok("Username changed successfully");
    }

    @PatchMapping("PATCH/users/password/{firstName}/{lastName}/new/{newPassword}")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR','CUSTOMER','SPECIALIST')")
    ResponseEntity<String> changePassword(@PathVariable String firstName,
                                          @PathVariable String lastName,
                                          @PathVariable String newPassword) throws UserOperationException {
        userEditService.changePassword(firstName, lastName, newPassword);
        return ResponseEntity.ok("Password changed successfully");
    }

    @PatchMapping("PATCH/users/email/{firstName}/{lastName}/new/{newEmail}")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR','CUSTOMER','SPECIALIST')")
    ResponseEntity<String> changeEmail(@PathVariable String firstName,
                                       @PathVariable String lastName,
                                       @PathVariable String newEmail) throws UserOperationException {
        userEditService.changeEmail(firstName, lastName, newEmail);
        return ResponseEntity.ok("Email changed successfully");
    }
}
