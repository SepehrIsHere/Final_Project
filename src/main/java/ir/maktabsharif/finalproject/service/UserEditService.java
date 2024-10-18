package ir.maktabsharif.finalproject.service;

import ir.maktabsharif.finalproject.exception.UserOperationException;

public interface UserEditService {
    void changeFirstName(String firstName, String lastName, String newFirstName)throws UserOperationException;

    void changeLastName(String firstName, String lastName, String newLastName)throws UserOperationException;

    void changeUsername(String firstName,String lastName,String newUserName)throws UserOperationException;

    void changePassword(String firstName, String lastName, String newPassword)throws UserOperationException;

    void changeEmail(String firstName, String lastName, String newEmail)throws UserOperationException;

}
