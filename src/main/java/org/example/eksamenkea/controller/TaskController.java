package org.example.eksamenkea.controller;

import jakarta.servlet.http.HttpSession;
import org.example.eksamenkea.model.Role;
import org.example.eksamenkea.model.Task;
import org.example.eksamenkea.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.util.List;

@Controller
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/project-leader-tasks/{projectId}")
    public String getTasksForSpecificProject(@PathVariable int projectId, HttpSession session, Model model) throws SQLException {
        Role userRole = (Role) session.getAttribute("userRole");
        if (userRole != Role.PROJECTLEADER) {//hvis bruger ik har rolle som PL returnes error
            return "error/error";
        }
        List<Task> tasks = taskService.getTasksByProjectId(projectId);

        model.addAttribute("tasks", tasks);
        model.addAttribute("project_id", projectId);

        return "project-leader-task-overview";
    }


}
