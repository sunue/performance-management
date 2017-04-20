package com.performance.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.performance.persist.dao.PersonDao;
import com.performance.persist.dao.ScientificResearchDao;
import com.performance.persist.dao.TeacherPerformanceDao;
import com.performance.persist.dao.TeachingResearchDao;
import com.performance.persist.domain.Person;
import com.performance.persist.domain.ScientificResearch;
import com.performance.persist.domain.TeacherPerformance;
import com.performance.persist.domain.TeachingResearch;
import com.performance.service.AdministratorService;

@Service
public class AdministratorServiceImpl implements AdministratorService {

    @Autowired
    private PersonDao personDao;

    @Autowired
    private TeacherPerformanceDao teacherPerformanceDao;

    @Autowired
    private ScientificResearchDao scientificResearchDao;

    @Autowired
    private TeachingResearchDao teachingResearchDao;

    @Override
    public Boolean administratorLogin(Map<String, Object> map) {
        map.put("status", "0"); //正常
        map.put("grade", 1); //管理员
        try {
            Person person = personDao.selectByParams(map);
            if (null != person) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int teacherRegisterCheckSum() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("grade", 2); //教师
        map.put("status", "2"); //审核中
        Integer sum = null;
        try {
            sum = personDao.selectCount(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sum;
    }

    @Override
    public List<Person> teacherRegisterCheck() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("grade", 2); //教师
        map.put("status", "2"); //审核中
        List<Person> teacherList = null;
        try {
            teacherList = personDao.selectListByParams(map);
            if (teacherList != null && teacherList.size() > 0) {
                return teacherList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int teacherRegisterAgree(Long id) {
        Person person = new Person();
        person.setId(id);
        person.setStatus("0"); //职工状态：正常
        person.setScientificResearchScore(0);
        person.setTeachingResearchScore(0);
        try {
            int result = personDao.updateByPrimaryKeySelective(person);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int teacherRegisterFail(Long id) {
        Person person = new Person();
        person.setId(id);
        person.setStatus("3"); //职工状态:未通过
        try {
            int result = personDao.updateByPrimaryKeySelective(person);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int addTeacher(Person person) {
        person.setGrade(2); //教师
        person.setStatus("0"); //正常
        try {
            int result = personDao.insertSelective(person);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int deleteTeacher(List<Long> idList) {
        try {
            int result = personDao.deleteByIdList(idList);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Map<String, Object> getTeacher(Map<String, Object> map) {
        try {
            List<Person> personList = personDao.selectListByParams(map);
            int count = personDao.selectCount(map);
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("personList", personList);
            resultMap.put("count", count);
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int updateTeacher(Person person) {
        try {
            int result = personDao.updateByPrimaryKeySelective(person);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Person getAdministratorInfo(Long id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("grade", 1); //管理员
        map.put("status", "0"); //正常
        try {
            Person person = personDao.selectByParams(map);
            return person;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int updateAdministratorInfo(Person person) {
        try {
            int result = personDao.updateByPrimaryKeySelective(person);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int updatePassword(Long id, String password) {
        Person person = new Person();
        person.setId(id);
        person.setPassword(password);
        try {
            int result = personDao.updateByPrimaryKeySelective(person);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int teacherPerformanceCheckSum() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", "2");
        int sum = 0;
        try {
            sum = teacherPerformanceDao.selectCount(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sum;
    }

    @Override
    public List<TeacherPerformance> teacherPerformanceCheck() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", "2");
        try {
            List<TeacherPerformance> teacherPerformanceList = teacherPerformanceDao
                .selectListByParams(map);
            return teacherPerformanceList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int teacherPerformanceAgree(Long id) {
        TeacherPerformance teacherPerformance = new TeacherPerformance();
        teacherPerformance.setId(id);
        teacherPerformance.setStatus("0");
        try {
            int result = teacherPerformanceDao.updateByPrimaryKeySelective(teacherPerformance);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int teacherPerformanceFail(Long id) {
        TeacherPerformance teacherPerformance = new TeacherPerformance();
        teacherPerformance.setId(id);
        teacherPerformance.setStatus("3"); //未通过
        try {
            int result = teacherPerformanceDao.updateByPrimaryKeySelective(teacherPerformance);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int addScientificResearchPerformance(ScientificResearch scientificResearch) {
        try {
            int result = scientificResearchDao.insertSelective(scientificResearch);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int addTeachingResearchPerformance(TeachingResearch teachingResearch) {
        try {
            int result = teachingResearchDao.insertSelective(teachingResearch);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int deleteScientificResearchPerformance(List<Long> idList) {
        try {
            int result = scientificResearchDao.deleteByIdList(idList);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int deteleTeachingResearchPerformance(List<Long> idList) {
        try {
            int result = teachingResearchDao.deleteByIdList(idList);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int updateScientificResearchPerformance(ScientificResearch scientificResearch) {
        try {
            int result = scientificResearchDao.updateByPrimaryKeySelective(scientificResearch);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int updateTeachingResearchPerformance(TeachingResearch teachingResearch) {
        try {
            int result = teachingResearchDao.updateByPrimaryKeySelective(teachingResearch);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Map<String, Object> getScientificResearchPerformance(int pageSize, int pageNum) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", "0");
        map.put("firstdata", (pageSize - 1) * pageNum);
        map.put("nums", pageNum);
        try {
            List<ScientificResearch> scientificResearchList = scientificResearchDao
                .selectListByParams(map);
            int count = scientificResearchDao.selectCount(map);

            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("scientificResearchList", scientificResearchList);
            resultMap.put("count", count);
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public Map<String, Object> getTeachingResearchPerformance(int pageSize, int pageNum) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", "0");
        map.put("firstdata", (pageSize - 1) * pageNum);
        map.put("nums", pageNum);
        try {
            List<TeachingResearch> teachingResearchList = teachingResearchDao
                .selectListByParams(map);
            int count = teachingResearchDao.selectCount(map);

            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("teachingResearchList", teachingResearchList);
            map.put("count", count);
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
