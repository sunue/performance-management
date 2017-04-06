package com.performance.service;

import java.util.List;
import java.util.Map;

import com.performance.persist.domain.Person;
import com.performance.persist.domain.ScientificResearch;
import com.performance.persist.domain.TeacherPerformance;
import com.performance.persist.domain.TeachingResearch;

public interface AdministratorService {

    /**
     * 管理员登录
     * */
    public Boolean administratorLogin(Map<String, Object> map);

    /**
     * 教师注册审核人数
     * */
    public int teacherRegisterCheckSum();

    /**
     * 教师注册审核
     * */
    public List<Person> teacherRegisterCheck();

    /**
     * 同意职工注册
     * */
    public int teacherRegisterAgree(Long id);

    /**
     * 拒绝职工注册
     * */
    public int teacherRegisterFail(Long id);

    /**
     * 增加教师
     * */
    public int addTeacher(Person person);

    /**
     * 删除教师
     * */
    public int deleteTeacher(List<Long> idList);

    /**
     * 查询教师
     * */
    public Map<String, Object> getTeacher(Map<String, Object> map);

    /**
     * 修改教师信息 
     * */
    public int updateTeacher(Person person);

    /**
     * 获取管理员信息
     * */
    public Person getAdministratorInfo(Long id);

    /**
     * 修改管理员信息
     * */
    public int updateAdministratorInfo(Person person);

    /**
     * 修改管理员密码
     * */
    public int updatePassword(Long id, String password);

    /**
     * 教师绩效待审核数
     * */
    public int teacherPerformanceCheckSum();

    /**
     * 待审核教师绩效
     * */
    public List<TeacherPerformance> teacherPerformanceCheck();

    /**
     * 同意教师绩效录入
     * */
    public int teacherPerformanceAgree(Long id);

    /**
     * 拒绝教师绩效录入
     * */
    public int teacherPerformanceFail(Long id);

    /**
     * 增加科研基础选项
     * */
    public int addScientificResearchPerformance(ScientificResearch scientificResearch);

    /**
     * 增加教研基础选项
     * */
    public int addTeachingResearchPerformance(TeachingResearch teachingResearch);

    /**
     * 删除科研基础选项
     * */
    public int deleteScientificResearchPerformance(List<Long> idList);

    /**
     * 删除教研基础选项
     * */
    public int deteleTeachingResearchPerformance(List<Long> idList);

    /**
     * 修改科研基础选项
     * */
    public int updateScientificResearchPerformance(ScientificResearch scientificResearch);

    /**
     * 修改教研基础选项
     * */
    public int updateTeachingResearchPerformance(TeachingResearch teachingResearch);

    /**
     * 获取科研基础选项
     * */
    public Map<String, Object> getScientificResearchPerformance(int pageSize, int pageNum);

    /**
     * 获取教研基础选项
     * */
    public Map<String, Object> getTeachingResearchPerformance(int pageSize, int pageNum);

}
