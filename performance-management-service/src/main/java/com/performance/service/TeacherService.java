package com.performance.service;

import java.util.Map;

import com.performance.persist.domain.Person;
import com.performance.persist.domain.TeacherPerformance;

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

    /**
     * 教师修改密码
     * */
    public String updatePassword(Map<String, Object> map);

    /**
     * 修改教师信息
     * */
    public String updateTeacherInfo(Person person);

    /**
     * 教师绩效录入
     * */
    public Boolean saveTeacherPerformance(TeacherPerformance teacherPerformance);

}
