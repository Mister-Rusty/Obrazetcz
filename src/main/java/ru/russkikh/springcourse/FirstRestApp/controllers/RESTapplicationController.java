package ru.russkikh.springcourse.FirstRestApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.russkikh.springcourse.FirstRestApp.security.PersonDetails;
import ru.russkikh.springcourse.FirstRestApp.services.AdminService;


@RestController // @Controller + @ResponseBody над каждым методом
@RequestMapping("/api")
public class RESTapplicationController {

      private final AdminService adminService;

      @Autowired
      public RESTapplicationController(AdminService adminService) {
            this.adminService = adminService;
        }

      @GetMapping("/hello")
      public String sayHello() {
            return "hello";
        }

      @GetMapping("/showUserInfo")
      public String showUserInfo() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
            System.out.println(personDetails.getPerson());
            return "hello";
      }

      @GetMapping("/admin")
      public String adminPage(){
            adminService.doAdminStuff();
            return "admin";
      }

}
