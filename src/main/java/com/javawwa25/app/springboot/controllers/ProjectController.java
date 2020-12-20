package com.javawwa25.app.springboot.controllers;

import com.javawwa25.app.springboot.models.Employee;
import com.javawwa25.app.springboot.models.Project;
import com.javawwa25.app.springboot.services.EmployeeService;
import com.javawwa25.app.springboot.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Controller
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private EmployeeService employeeService;

    // display list of employees
    @GetMapping("/project")
    public String viewHomePage(Model model) {
        return findPaginated(1, "projectName", "asc", model);
    }

    @GetMapping("/showNewProjectForm/project")
    public String showNewProjectForm(Model model) {
        // create model attribute to bind form data
        Project project = new Project();
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        model.addAttribute("project", project);

        return "new_project";
    }

    @PostMapping("/saveProject/project")
    public String saveProject(@ModelAttribute("project") Project project) {
        projectService.saveProject(project);
        return "redirect:/project/project-list";   // CHECK REDIRECT !!!!!!!!!!!!
    }


    @GetMapping("/showFormForUpdate/project/{id}")
    public String showFormForUpdate(@PathVariable( value = "id") long id, Model model) {

        // get project from the service
        Project project = projectService.getProjectById(id);

        // set project as a model attribute to pre-populate the form
        model.addAttribute("project", project);
        return "update_project";
    }

    @GetMapping("/deleteProject/project/{id}")
    public String deleteProject(@PathVariable (value = "id") long id) {

        // call delete project method
        this.projectService.deleteProjectById(id);
        return "redirect:/project/project-list";   // CHECK REDIRECT !!!!!!!!!!!!
    }


    // list of projects OLD
    @GetMapping("/project/{pageNo}")
    public String findPaginated(@PathVariable (value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 5;

        Page<Project> page = projectService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Project> projectList = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("projectList", projectList);
        return "/project/project-list";
    }

    @GetMapping("/project/project-list")
    public String showProjectList(Model model) {
        List<Project> projectList = projectService.getAllProjects();
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        model.addAttribute("projectList", projectList);
        return "/project/project-list";
    }

    @GetMapping("/project/{id}")
    public String findProjectTasksById(@PathVariable( value = "id") long id, Model model) {
        Project project = projectService.getProjectById(id);
        List<Project> projectList = projectService.getAllProjects();
        model.addAttribute("projectList", projectList);
        model.addAttribute("project",project);
        return "project/{id}/task-list";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(       Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, 10));
    }

}
