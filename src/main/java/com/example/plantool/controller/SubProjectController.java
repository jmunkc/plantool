package com.example.plantool.controller;

import com.example.plantool.model.Project;
import com.example.plantool.model.SubProject;
import com.example.plantool.services.ProjectService;
import com.example.plantool.services.SessionService;
import com.example.plantool.services.SubProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Author: Jonas Munk
 *
 * Controller for subproject
 */

@Controller
public class SubProjectController {
    ProjectService projectService = new ProjectService();
    SubProjectService subProjectService = new SubProjectService();
    SessionService sessionService = new SessionService();


    // view end point for subproject
   @GetMapping("/viewsubproject")
    public String subProjectOverview(Model model, HttpSession session) throws SQLException {
       String mappingLeader = sessionService.inSession(model, session, "viewsubprojectleader");
       String mappingMember = sessionService.inSession(model, session, "viewsubprojectmember");
       int isLeader = sessionService.isLeaderSession(session);

       Project currentProject = (Project) session.getAttribute("currentProject");

       int projectId = currentProject.getId();

       model.addAttribute("currentProject", currentProject);

       ArrayList<SubProject> subProjects = subProjectService.fetchAllSubProjectsFromProject(projectId);
       model.addAttribute("subprojects", subProjects);
       session.setAttribute("subprojects", subProjects );
       if(isLeader == 1) {
           return mappingLeader;

       }else {
            return mappingMember;
        }
    }

    // postmapping for viewsubproject
    @PostMapping("/viewsubproject")
    public String postSubProject(WebRequest wr, HttpSession session) {
        // int leaderId = Integer.parseInt(session.getAttribute("userid").toString());
        if(wr.getParameter("subprojectId") != null) {
            int subProjectId = Integer.parseInt(wr.getParameter("subprojectId"));
            session.setAttribute("subprojectId", subProjectId);

        ArrayList<SubProject> subProjects = (ArrayList<SubProject>) session.getAttribute("subprojects");
        SubProject currentSubproject = new SubProject();

        for(SubProject subProject : subProjects){
            if(subProject.getId() == subProjectId){
                currentSubproject = subProject;
            }
        }
        session.setAttribute("currentSubproject", currentSubproject);
        return "redirect:/viewtask";
        }
        else{
            return "redirect:/viewsubproject";
        }
    }

    //Postmapping to create a new subproject
    @PostMapping("/viewsubproject/create")
    public String createSubProject(WebRequest wr, HttpSession session){

        Project currentProject = (Project) session.getAttribute("currentProject");
        int projectId = currentProject.getId();

        String name = wr.getParameter("name");
        LocalDate startDate = LocalDate.parse(wr.getParameter("startDate"));
        LocalDate deadline = LocalDate.parse(wr.getParameter("deadline"));
        int hoursAllocated = Integer.parseInt(wr.getParameter("hoursAllocated"));
        String projectDescription = wr.getParameter("description");

        SubProject newProject = new SubProject(name, startDate, deadline, deadline, hoursAllocated, projectDescription);
        subProjectService.addSubProjectToDb(newProject, projectId);
        return "redirect:/viewsubproject";
    }

    //Postmapping to modify project
    @PostMapping("/viewsubproject/modify")
    public String modifyProject(WebRequest wr, HttpSession session){

        int projectId = ((Project) session.getAttribute("currentProject")).getId();

        if(wr.getParameter("newname") != ""){
            projectService.updateProjectName(projectId, wr.getParameter("newname"));
        }
        if(wr.getParameter("newstartDate") != ""){
            projectService.updateProjectStartDate(projectId, LocalDate.parse(wr.getParameter("newdeadline")));
        }
        if(wr.getParameter("newdeadline") != ""){
            projectService.updateDeadline(projectId, LocalDate.parse(wr.getParameter("newdeadline")));
        }
        if(wr.getParameter("newhoursAllocated") != ""){
            projectService.updateHoursAllocated(projectId, Integer.parseInt(wr.getParameter("newhoursAllocated")));
        }
        if(wr.getParameter("newdescription") != ""){
            projectService.updateProjectDescription(projectId, wr.getParameter("newdescription"));
        }

        return "redirect:/viewproject";
    }

    //Delete project
    @PostMapping("/viewsubproject/delete")
    public String deleteProject(WebRequest wr, HttpSession session) throws SQLException {

        if(session.getAttribute("currentProject")  != null){
            int deleteId = Integer.parseInt(wr.getParameter("deleteId"));
            projectService.deleteProject(deleteId);
            return "redirect:/viewproject";

        }else{
            return "redirect:/viewsubproject";
        }
    }

}

