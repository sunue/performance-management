package com.performance.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.performance.persist.domain.Person;
import com.performance.service.PersonService;

@Controller
@RequestMapping(value = "/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @RequestMapping("/register")
    public ResponseEntity<Boolean> personRegister(String personJSON) {
        if (personJSON == null) {

        } else {
            Person person = JSON.parseObject("personJSON", Person.class);
            Boolean result = personService.saveRegisterPerson(person);

        }

        return null;

    }
}
