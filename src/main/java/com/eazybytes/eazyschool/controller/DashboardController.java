package com.eazybytes.eazyschool.controller;

import com.eazybytes.eazyschool.bean.Person;
import com.eazybytes.eazyschool.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class DashboardController {

    @Value("${eazyschool.pagesize}")
    private int defaultPageSize;
    @Value("${eazyschool.contact.sucessmsg}")
    private String message;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    Environment environment;

    @RequestMapping("/dashboard")
    public String displayDashboard(Model model, Authentication authentication, HttpSession session){
        Person person = personRepository.readByEmail(authentication.getName());
        model.addAttribute("username", person.getName());
        model.addAttribute("roles", authentication.getAuthorities().toString());
        if (null!= person.getEazyClass() && null!= person.getEazyClass().getName()){
            model.addAttribute("enrolledClass", person.getEazyClass().getName());
        }
        session.setAttribute("loggedInPerson",person);
        logMessages();
        return "dashboard.html";
    }

    private void logMessages(){
        log.error("Error message from the Dashboard page");
        log.warn("Warning message from the Dashboard page");
        log.info("Info message from the Dashboard page");
        log.debug("Debug message from the Dashboard page");
        log.trace("Trace message from the Dashboard page");

        log.error("Error message from the Dashboard page "+ defaultPageSize);
        log.error("Error message from the Dashboard page "+ message);

        log.error("Size Dashboard page "+ environment.getProperty("eazyschool.pageSize"));
        log.error("Message of Dashboard page "+ environment.getProperty("eazyschool.contact.sucessMsg"));
        log.error("environment variable of Java Home"+ environment.getProperty("JAVA_HOME"));

    }
}
