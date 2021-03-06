package com.example.plantool.services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Author: Michael Dyvad
 *
 * Test if email exist
 */

class DoesEmailExistTest {
    //Test if an email alreadt exist in DB / Successful test
    @Test
    void doesEmailExist() {
        //Arrange
        MemberService service = new MemberService();
        String email = "kevin@mail.com";
        String wrongMail = "wrong@mail.com";

        //Act
        boolean doesItExist = service.doesEmailExist(email);
        boolean doesItExistWrong = service.doesEmailExist(wrongMail);

        //Assert
        assertEquals(true, doesItExist);
        assertEquals(false, doesItExistWrong);

    }
}