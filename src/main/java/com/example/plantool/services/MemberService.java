package com.example.plantool.services;

import com.example.plantool.model.Member;
import com.example.plantool.repository.MemberRepo;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Author: Kevin Funch
 *
 * Member service for funktionality
 */

public class MemberService {
    MemberRepo memberRepo = new MemberRepo();

    // new instance of user
    public void createMember(String name, String email, String password){
        Member member = new Member(name, email, password);

        if (emailValidation(email) == false){
            System.out.println("Email not valid");
        }

        else if (doesEmailExist(email) == false){
            memberRepo.insertMemberToDB(member);
        }
        else {
            System.out.println("Emailen already exists");
        }

    }


    // checks if the email is already registered
    public boolean doesEmailExist(String email){

        for(int i = 0; i < memberRepo.findAllMembers().size(); i++){
            if(memberRepo.findAllMembers().get(i).getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }

    // email validation
    public boolean emailValidation (String email){
        if(email.contains(".") && email.contains("@")){
            return true;
        } else
            return false;
    }

    // checks password
    public boolean passwordValidation (Member member, String userInput){
        boolean passIsValid = false;

        if(userInput.equals(member.getPassword())){
            passIsValid = true;
        }

        return passIsValid;
    }

    // User login
    public boolean login(String email, String password) throws SQLException {
        boolean validLogin = false;

        if(doesEmailExist(email)){
            if(passwordValidation(memberRepo.findMember(email), password)){
                validLogin = true;
            }
            else{

            }
        }

        else{

        }
        return validLogin;
    }

    // get all members
    public ArrayList<Member> getAllMembers(){
        ArrayList<Member> members = memberRepo.findAllMembers();
        return members;
    }

    // finds member on email
    public Member findMember(String email) throws SQLException {
        return memberRepo.findMember(email);
    }

    // Finds  member by id
    public Member memberById(int id) throws SQLException {
        return memberRepo.findMemberById(id);
    }

    //Finds member by name
    public Member memberByName(String name) throws SQLException {
        return memberRepo.findMemberByName(name);
    }

    //Extracts if member is leader
    public void isLeaderBoolean(boolean isLeader, String email){
        memberRepo.isLeaderBoolean(isLeader, email);
    }


}
