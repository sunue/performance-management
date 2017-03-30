package com.performance.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.performance.persist.dao.PersonDao;
import com.performance.persist.domain.Person;
import com.performance.service.TeacherService;

public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private PersonDao personDao;

    @Override
    public Boolean saveRegisterPerson(Person person) {
        person.setGrade(2); //设置状态为：审核中
        person.setStatus("2"); //设置身份为：教师
        try {
            int result = personDao.insertSelective(person);
            if (1 == result) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {

        }
        return false;
    }

    @Override
    public Person teacherLogin(Map<String, Object> map) {
        try {
            Person person = personDao.selectByParams(map);
            return person;
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    public Person getTeacherInfo(Map<String, Object> map) {
        try {
            Person person = personDao.selectByParams(map);
            return person;
        } catch (Exception e) {

        }
        return null;
    }
}
