package ir.maktabsharif.finalproject.controller;

import ir.maktabsharif.finalproject.dto.UserDto;
import ir.maktabsharif.finalproject.exception.UserOperationException;
import ir.maktabsharif.finalproject.service.UserEditService;
import ir.maktabsharif.finalproject.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserEditService userEditService;
    private final MapperUtil mapperUtil;

    @PatchMapping("PATCH/users/firstname/{firstName}/{lastName}/new/{newFirstName}")
    ResponseEntity<Void> changeFirstName(@PathVariable String firstName,
                                         @PathVariable String lastName,
                                         @PathVariable String newFirstName) throws UserOperationException {
        userEditService.changeFirstName(firstName, lastName, newFirstName);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("PATCH/users/lastname/{firstName}/{lastName}/new/{newLastName}")
    ResponseEntity<Void> changeLastName(@PathVariable String firstName,
                                        @PathVariable String lastName,
                                        @PathVariable String newLastName) throws UserOperationException {
        userEditService.changeLastName(firstName, lastName, newLastName);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("PATCH/users/username/{firstName}/{lastName}/new/{newUsername}")
    ResponseEntity<Void> changeUsername(@PathVariable String firstName,
                                        @PathVariable String lastName,
                                        @PathVariable String newUsername) throws UserOperationException {
        userEditService.changeUsername(firstName, lastName, newUsername);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("PATCH/users/password/{firstName}/{lastName}/new/{newPassword}")
    ResponseEntity<Void> changePassword(@PathVariable String firstName,
                                            @PathVariable String lastName,
                                            @PathVariable String newPassword) throws UserOperationException {
        userEditService.changePassword(firstName, lastName, newPassword);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("PATCH/users/email/{firstName}/{lastName}/new/{newEmail}")
    ResponseEntity<Void> changeEmail(@PathVariable String firstName,
                                     @PathVariable String lastName,
                                     @PathVariable String newEmail) throws UserOperationException {
        userEditService.changeEmail(firstName, lastName, newEmail);
        return ResponseEntity.noContent().build();
    }
}
