package com.performance.service;

import java.util.Map;

import com.performance.persist.domain.Person;

public interface TeacherService {

    /**
     * 教师注册信息
     * */
    public Boolean saveRegisterPerson(Person person);

    /**
     * 教师登录
     * */
    public Person teacherLogin(Map<String, Object> map);

    /**
     * 查看教师信息
     * */
    public Person getTeacherInfo(Map<String, Object> map);
}
