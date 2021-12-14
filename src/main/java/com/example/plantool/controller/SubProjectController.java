package com.example.plantool.controller;

import com.example.plantool.model.SubProject;
import com.example.plantool.services.MemberService;
import com.example.plantool.services.SubProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;

@Controller
public class SubProjectController {
    SubProjectService subProjectService = new SubProjectService();
    MemberService memberService = new MemberService();


   @GetMapping("/viewsubproject")
    public String subProjectOverview(Model model, HttpSession session) throws SQLException {

       int projectId = (Integer) session.getAttribute("projectId");
       int memberLead = (Integer) session.getAttribute("boolean-leader");

        if(memberLead == 1){
            String mapping = memberService.inSession(model, session, "viewsubprojectleader");
            ArrayList<SubProject> subProjects = subProjectService.fetchAllSubProjectsFromProject(projectId);
            model.addAttribute("subprojects", subProjects);
            return mapping;
        }

        else {
            String mapping = memberService.inSession(model, session, "viewsubprojectmember");
            ArrayList<SubProject> subProjects = subProjectService.fetchAllSubProjectsFromProject(projectId);
            model.addAttribute("subprojects", subProjects);
            return mapping;
        }

    }
    @PostMapping("/viewsubproject")
    public String postSubProject(WebRequest wr, HttpSession session) throws SQLException{
        int leaderId = Integer.parseInt(session.getAttribute("userid").toString());
        int subProjectId = Integer.parseInt(wr.getParameter("subprojectId"));
        session.setAttribute("subprojectId", subProjectId);
        return "redirect:/viewtask";
    }





/*
   @GetMapping("/viewsubproject")
    public String subprojectView(Model model, HttpSession session) {
        int memberLead = (Integer) session.getAttribute("boolean-leader");
        int projectId = (Integer) session.getAttribute("projectId");
        System.out.println(projectId);

        ArrayList<SubProject> subProjectArray = subProjectService.fetchAllSubProjectsFromProject(projectId);

        model.addAttribute("subprojects", subProjectArray);


        if (memberLead == 1) {
            return "viewsubprojectleader";
        } else
            return "viewsubprojectmember";
    }

 */


}
