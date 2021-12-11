package com.example.plantool.repository;

import com.example.plantool.model.Skill;
import com.example.plantool.utility.DatabaseConnector;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SkillRepo {

    // virker - æmdret SQL-statement fra skill til skills
    public void insertSkillToDB (String skillName){

        try{
            PreparedStatement stmt =
                    DatabaseConnector.getConnection().prepareStatement("INSERT INTO skills(skillname) VALUES(?) ");
            stmt.setString(1, skillName);
            stmt.executeUpdate();
            System.out.println("Skill Insert complete");
        } catch (SQLException e){
            System.out.println("Something went wrong");
            e.printStackTrace();
        }
    }

    // virker
    public void assignSkillToMember(int memberId, int skillId){

        try{
            PreparedStatement stmt =
                    DatabaseConnector.getConnection().prepareStatement(
                            "INSERT INTO userskills(userid, skillid) VALUES(?,?) ");
            stmt.setInt(1, memberId);
            stmt.setInt(2, skillId);
            stmt.executeUpdate();
            System.out.println("Skill to Member Insert complete");
        } catch (SQLException e){
            System.out.println("Something went wrong");
            e.printStackTrace();
        }
    }

    // virker
    public void assignSkillToProject(int projectId, int skillId){

        try{
            PreparedStatement stmt =
                    DatabaseConnector.getConnection().prepareStatement(
                            "INSERT INTO projectskills(projectid, skillid) VALUES(?,?) ");
            stmt.setInt(1, projectId);
            stmt.setInt(2, skillId);
            stmt.executeUpdate();
            System.out.println("Insert complete");
        } catch (SQLException e){
            System.out.println("Something went wrong");
            e.printStackTrace();
        }
    }



    // virker
    public ArrayList<Skill> findAllSkills (){

        ArrayList<Skill> skills = new ArrayList<>();

        try{
            PreparedStatement stmt =
                    DatabaseConnector.getConnection().prepareStatement("SELECT * FROM skills");

            ResultSet result = stmt.executeQuery();

            while(result.next()){
                Skill tmpSkill = new Skill(result.getInt(1), result.getString(2));
                skills.add(tmpSkill);

            }
        } catch (SQLException e){
            System.out.println("Something went wrong");
            e.printStackTrace();
        }
        return skills;
    }

    // virker
    public Skill findSkillById(int skillId) {
        Skill skill = new Skill();
        try{
            PreparedStatement stmt = DatabaseConnector.getConnection().prepareStatement("SELECT skills.skillid, skillname FROM skills WHERE skillid ='" + skillId + "'");

            ResultSet set = stmt.executeQuery();
            while(set.next()) {
                skill.setSkillId(set.getInt(1));
                skill.setSkillName(set.getString(2));
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return skill;
    }


    // virker
    public Skill findSkillByName(String skillName) {
        Skill skill = new Skill();

        try {
            PreparedStatement stmt = DatabaseConnector.getConnection().prepareStatement("SELECT skills.skillid, skillname FROM skills WHERE skillname ='" + skillName + "'");


            ResultSet set = stmt.executeQuery();
            while (set.next()) {
                skill.setSkillId(set.getInt(1));
                skill.setSkillName(set.getString(2));

            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return skill;
    }


    // virker
    public ArrayList<Skill> findMemberSkills(int memberId){

        ArrayList<Skill> memberSkills = new ArrayList<>();

        try{
            PreparedStatement stmt =
                    DatabaseConnector.getConnection().prepareStatement(
                            "SELECT userskills.userid, userskills.skillid, skillname FROM userskills JOIN skills on skills.skillid=userskills.skillid WHERE userid ="+memberId+"");

            ResultSet result = stmt.executeQuery();

            while(result.next()){
                Skill tmpSkill = new Skill();
                tmpSkill.setSkillId(result.getInt(2));
                tmpSkill.setSkillName(result.getString(3));
                memberSkills.add(tmpSkill);
            }
        } catch (SQLException e){
            System.out.println("Something went wrong");
            e.printStackTrace();
        }
        return memberSkills;
    }

    // virker
    public ArrayList<Skill> findProjectSkills(int projectId){

        ArrayList<Skill> projectSkills = new ArrayList<>();

        try{
            PreparedStatement stmt =
                    DatabaseConnector.getConnection().prepareStatement("SELECT projectskills.projectid, projectskills.skillid, skillname FROM projectskills JOIN skills ON skills.skillid=projectskills.skillid WHERE projectid ="+projectId+"");

            ResultSet result = stmt.executeQuery();

            while(result.next()){
                Skill tmpSkill = new Skill(result.getInt(2), result.getString(3));
                projectSkills.add(tmpSkill);

            }
        } catch (SQLException e){
            System.out.println("Something went wrong");
            e.printStackTrace();
        }
        return projectSkills;
    }
}