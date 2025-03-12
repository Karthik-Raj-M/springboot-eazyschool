package com.eazybytes.eazyschool.rest;

import com.eazybytes.eazyschool.bean.ContactBean;
import com.eazybytes.eazyschool.bean.Response;
import com.eazybytes.eazyschool.constants.EazySchoolConstants;
import com.eazybytes.eazyschool.repository.ContactRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping(path = "/api/contact", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
@CrossOrigin(origins = "*")
public class ContactRestController {

    @Autowired
    ContactRepository contactRepository;

    @GetMapping("/getMessagesByStatus")
    @ResponseBody
    List<ContactBean> getMessagesByStatus(@RequestParam("status") String status){
        return contactRepository.findByStatus(status);
    }

    @GetMapping("/getAllMessagesByStatus")
    //@ResponseBody
    List<ContactBean> getAllMessagesByStatus(@RequestBody ContactBean contactBean){
        if (contactBean!=null && contactBean.getStatus()!=null){
            return contactRepository.findByStatus(contactBean.getStatus());
        }
        else {
            return List.of();
        }
    }

    @PostMapping("/saveMsg")
    public ResponseEntity<Response> saveMsg(@RequestHeader("invocationFrom") String invocationFrom, @Valid @RequestBody ContactBean contactBean){
        log.info(String.format("Header invocationFrom = %s",invocationFrom));
        contactRepository.save(contactBean);
        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMsg("Message saved successfully");
        return ResponseEntity.status(HttpStatus.CREATED).header("isMsgSaved","true").body(response);
    }

    @DeleteMapping("/deleteMsg")
    public ResponseEntity<Response> deleteMsg(RequestEntity<ContactBean> requestEntity){
        HttpHeaders headers = requestEntity.getHeaders();
        headers.forEach((key, value)->log.info(String.format("Headers '%s' = %s",key,value.stream().collect(Collectors.joining("|")))));
        ContactBean contactBean = requestEntity.getBody();
        contactRepository.deleteById(contactBean.getContactId());
        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMsg("Message deleted successfully!!!");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/closeMsg")
    public ResponseEntity<Response> closeMsg(@RequestBody ContactBean contactReq){
        Optional<ContactBean> contact = contactRepository.findById(contactReq.getContactId());
        Response response = new Response();
        if(contact.isPresent()) {
            contact.get().setStatus(EazySchoolConstants.CLOSE);
            contactRepository.save(contact.get());
        }
        else{
            response.setStatusCode("400");
            response.setStatusMsg("Invalid Contact Id entered!!!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        response.setStatusCode("200");
        response.setStatusMsg("Status updated successfully!!!");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }
}
