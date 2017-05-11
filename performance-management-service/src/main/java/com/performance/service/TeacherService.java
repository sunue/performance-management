package com.performance.service;

import java.util.List;
import java.util.Map;

import com.performance.persist.domain.Person;
import com.performance.persist.domain.TeacherPerformance;

public interface TeacherService {

    /**
     * 教师注册信息
     * */
    public Boolean saveRegisterPerson(Person person);

    /**
     * 验证注册id是否已存在
     * */
    public Person idVilidate(Long id);

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

    /**
     * 教师查看待审核绩效
     * */
    public Map<String, Object> getCheckPerformance(Long id, int pageSize, int pageNum);

    /**
     * 按总分排名
     * */
    public Map<String, Object> totalRank(Long id, int pageSize, int pageNum);

    /**
     * 按科研总分排名
     * */
    public Map<String, Object> scientificResearchRank(Long id, int pageSize, int pageNum);

    /**
     * 按教研总分排名
     * */
    public Map<String, Object> teachingResearchRank(Long id, int pageSize, int pageNum);

    /**
     * 获取科研内容
     * */
    public List<String> getSciContent();

    /**
     * 获取教研内容
     * */
    public List<String> getTeaContent();

    /**
     * 根据科研内容获取科研具体工作
     * */
    public List<String> getSciProjectBySciContent(Map<String, Object> map);

    /**
     * 根据教研内容获取教研具体工作
     * */
    public List<String> getTeaProjectByTeaContent(Map<String, Object> map);

    /**
     * 根据科研内容和科研具体工作获取科研等级
     * */
    public List<String> getSciGradeBySciContentAndSciProject(Map<String, Object> map);

    /**
     * 根据教研内容和教研具体工作获取教研等级
     * */
    public List<String> getTeaGradeByTeaContentAndTeaProject(Map<String, Object> map);

    /**
     * 获取教师姓名
     * */
    public String getTeacherName(Map<String, Object> map);

    public int deleteCheckPerformance(List<Long> idList);
}
