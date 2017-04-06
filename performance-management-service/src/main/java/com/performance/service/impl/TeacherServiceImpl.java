package com.performance.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.performance.persist.dao.PersonDao;
import com.performance.persist.dao.TeacherPerformanceDao;
import com.performance.persist.domain.Person;
import com.performance.persist.domain.TeacherPerformance;
import com.performance.service.TeacherService;

public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private PersonDao personDao;

    @Autowired
    private TeacherPerformanceDao teacherPerformanceDao;

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
    public Person idVilidate(Long id) {
        Person person = personDao.selectByPrimaryKey(id);
        return person;
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

    @Override
    public String updatePassword(Map<String, Object> map) {
        Long id = (Long) map.get("id");
        Person person = personDao.selectByPrimaryKey(id);
        if (null == person) {
            return "该用户不存在";
        }
        Person temp = new Person();
        temp.setId(id);
        temp.setPassword((String) map.get("password"));
        int result = personDao.updateByPrimaryKeySelective(temp);
        if (1 == result) {
            return "修改成功";
        }
        return "修改失败";
    }

    @Override
    public String updateTeacherInfo(Person person) {
        Long id = person.getId();
        Person temp = personDao.selectByPrimaryKey(id);
        if (null == temp) {
            return "该用户不存在";
        }
        int result = personDao.updateByPrimaryKeySelective(person);
        if (1 == result) {
            return "修改成功";
        }
        return "修改失败";
    }

    @Override
    public Boolean saveTeacherPerformance(TeacherPerformance teacherPerformance) {
        teacherPerformance.setStatus("2"); //审核中
        int result = teacherPerformanceDao.insertSelective(teacherPerformance);
        if (1 == result) {
            return true;
        }
        return false;
    }

    @Override
    public Map<String, Object> totalRank(Long id, int pageSize, int pageNum) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("firstdata", (pageSize - 1) * pageNum);
        map.put("nums", pageNum);
        List<Person> personList = personDao.selectListByTotalRank(map);
        int rank = personDao.selectRankByTotal(id);
        int count = personDao.selectCount(map);

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("personList", personList);
        resultMap.put("rank", rank);
        resultMap.put("count", count);
        return resultMap;
    }

    @Override
    public Map<String, Object> scientificResearchRank(Long id, int pageSize, int pageNum) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("firstdata", (pageSize - 1) * pageNum);
        map.put("nums", pageNum);
        List<Person> personList = personDao.selectListByScientificResearch(map);
        int rank = personDao.selectRankByScientificResearch(id);
        int count = personDao.selectCount(map);

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("personList", personList);
        resultMap.put("rank", rank);
        resultMap.put("count", count);
        return resultMap;
    }

    @Override
    public Map<String, Object> teachingResearchRank(Long id, int pageSize, int pageNum) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("firstdata", (pageSize - 1) * pageNum);
        map.put("nums", pageNum);
        List<Person> personList = personDao.selectListByTeachingResearch(map);
        int rank = personDao.selectRankByTeachingResearch(id);
        int count = personDao.selectCount(map);

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("personList", personList);
        resultMap.put("rank", rank);
        resultMap.put("count", count);
        return resultMap;
    }

    @Override
    public Map<String, Object> getCheckPerformance(Long id) {
        Map map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("status", "2");
        List<TeacherPerformance> teacherPerformanceList = teacherPerformanceDao
            .selectListByParams(map);
        int count = teacherPerformanceDao.selectCount(map);

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("teacherPerformanceList", teacherPerformanceList);
        resultMap.put("count", count);
        return resultMap;
    }

}
