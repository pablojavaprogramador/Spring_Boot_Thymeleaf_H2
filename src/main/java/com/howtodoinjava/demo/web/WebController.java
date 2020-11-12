package com.howtodoinjava.demo.web;

import com.howtodoinjava.demo.exception.RecordNotFoundException;
import com.howtodoinjava.demo.model.EmployeeEntity;
import com.howtodoinjava.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class WebController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/search")
    public String searchEmployees(Model model,  HttpServletRequest request, HttpServletResponse response){
        System.out.println(request.getMethod());
        System.out.println(request.getRequestURL());
        System.out.println(request.getRequestURI());
        System.out.println(request.getUserPrincipal());

        List<EmployeeEntity> list = employeeService.getAllEmployees();
        model.addAttribute("employees", list);

        return "list-employees";
    }

    @PostMapping("/createEmployee")
    public String createEmployee(@ModelAttribute EmployeeEntity employee){

        System.out.println(employee);
        try {
            employeeService.createOrUpdateEmployee(employee);
        } catch (RecordNotFoundException e) {
            e.printStackTrace();
        }
        return "done";
    }

    @GetMapping("/edit")
    public String updateEmployees(Model model){
        List<EmployeeEntity> list = employeeService.getAllEmployees();
        EmployeeEntity employee =  new EmployeeEntity();
        employee.setId((new Long(list.size() + 1)));

        model.addAttribute("employee", employee);
        return "add-edit-employee";
    }

    @GetMapping("/edit/{id}")
    public String updateSingleEmployee(@PathVariable String id, Model model){
        System.out.println("id -> " + id);
        EmployeeEntity employee = null;
        try {
            employee = employeeService.getEmployeeById(new Long(id));
        } catch (RecordNotFoundException e) {
            e.printStackTrace();
        }
        model.addAttribute("employee", employee);
        return "add-edit-employee";
    }

    @GetMapping("/delete/{id}")
    public String deleteSingleEmployee(@PathVariable String id, Model model){
        System.out.println("id -> " + id);

        try {
            employeeService.deleteEmployeeById(new Long(id));
        } catch (RecordNotFoundException e) {
            e.printStackTrace();
        }

        List<EmployeeEntity> list = employeeService.getAllEmployees();
        model.addAttribute("employees", list);
        return "list-employees";
    }


}
