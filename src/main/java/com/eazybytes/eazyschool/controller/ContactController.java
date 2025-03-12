package com.eazybytes.eazyschool.controller;

import com.eazybytes.eazyschool.bean.ContactBean;
import com.eazybytes.eazyschool.service.ContactService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@Controller
public class ContactController {


    @Autowired
    ContactService contactService;

    @RequestMapping("/contact")
    public String displayContactPage(Model model){
        model.addAttribute("contact", new ContactBean());
        return "contact.html";
    }

/*    @RequestMapping(value="/saveMsg", method = POST)
    public ModelAndView saveMessage(@RequestParam String name, @RequestParam String mobileNum, @RequestParam String email,
                                    @RequestParam String subject, @RequestParam String message){

        log.info("Name: "+name);
        log.info("Mobile Number: "+mobileNum);
        log.info("Email: "+email);
        log.info("Subject: "+subject);
        log.info("message: "+message);
        return new ModelAndView("redirect:/contact");
    }
 */

    @RequestMapping(value="/saveMsg", method = POST)
    public String saveMessage(@Valid @ModelAttribute("contact") ContactBean contact, Errors errors){

        if (errors.hasErrors()){
            log.error("Contact form validation failed due to "+errors.toString());
            return "contact.html";
        }

        contactService.SaveAllMessage(contact);
//        contactService.setCounter(contactService.getCounter()+1);
//        log.info("Number of times contact is getting saved is "+contactService.getCounter());
        return "redirect:/contact";
    }

    @RequestMapping("/displayMessages/page/{pageNum}")
    public ModelAndView displayMessages(Model model, @PathVariable("pageNum") int pageNum,@RequestParam("sortField")String sortField,
                                        @RequestParam("sortDir") String sortDir){
        Page<ContactBean> msgPage = contactService.findMsgsWithOpenStatus(pageNum,sortField,sortDir);
        List<ContactBean> contactMsgs = msgPage.getContent();
        ModelAndView modelAndView = new ModelAndView("messages.html");
        modelAndView.addObject("currentPage", pageNum);
        modelAndView.addObject("totalPages",msgPage.getTotalPages());
        modelAndView.addObject("totalMsgs",msgPage.getTotalElements());
        modelAndView.addObject("sortField",sortField);
        modelAndView.addObject("sortDir ",sortDir);
        modelAndView.addObject("reverseSortDir",sortDir.equals("asc")? "desc":"asc");
        modelAndView.addObject("contactMsgs", contactMsgs);
        return modelAndView;
    }

    @RequestMapping(value = "/closeMsg",method = GET)
    public String closeMsg(@RequestParam int id) {
        contactService.updateMsgStatus(id);
        return "redirect:/displayMessages/page/1?sortField=name&sortDir=desc";
    }
}
