package com.example.plantool.model;

import java.util.ArrayList;
import java.util.Date;

public class SubProject extends Project {

    SubProject(int id, String name, Date startDate, Date endDate, Date deadline, ArrayList<ProjectMember> assignees, int hoursAllocated, int hoursUsed, ArrayList<Skill> skillsAllocated) {
        super(id, name, startDate, endDate, deadline, assignees, hoursAllocated, hoursUsed, skillsAllocated);
    }
}
