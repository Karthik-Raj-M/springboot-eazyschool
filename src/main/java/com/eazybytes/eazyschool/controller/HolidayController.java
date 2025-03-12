package com.eazybytes.eazyschool.controller;

import com.eazybytes.eazyschool.repository.HolidaysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import com.eazybytes.eazyschool.bean.HolidayBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class HolidayController {

    @Autowired
    public HolidaysRepository holidaysRepository;

    @GetMapping("/holidays/{display}")
    public String holidaysList(@PathVariable String display, Model model){

        if(null!=display && display.equals("all")) {
            model.addAttribute("festival", true);
            model.addAttribute("federal", true);
        }
        else if(null!=display && display.equals("festival")) {
            model.addAttribute("festival", true);
            model.addAttribute("federal", false);
        }
        else if(null!=display && display.equals("federal")) {
            model.addAttribute("festival", false);
            model.addAttribute("federal", true);
        }

        Iterable<HolidayBean> holidays = holidaysRepository.findAll();
        List<HolidayBean> holidaysList = StreamSupport.stream(holidays.spliterator(), false).collect(Collectors.toList());
        HolidayBean.Type[] types = HolidayBean.Type.values();
        for (HolidayBean.Type type : types) {
            model.addAttribute(type.toString(),
                    (holidaysList.stream().filter(holiday -> holiday.getType().equals(type)).collect(Collectors.toList())));
        }
        return "holidays.html";
    }
}
