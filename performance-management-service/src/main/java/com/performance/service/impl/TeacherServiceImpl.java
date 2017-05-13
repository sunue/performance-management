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
import com.performance.persist.domain.TeacherPerformance;
import com.performance.service.TeacherService;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private PersonDao personDao;

    @Autowired
    private TeacherPerformanceDao teacherPerformanceDao;

    @Autowired
    private ScientificResearchDao scientificResearchDao;

    @Autowired
    private TeachingResearchDao teachingResearchDao;

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
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Person idVilidate(Long id) {
        try {
            Person person = personDao.selectByPrimaryKey(id);
            return person;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Person teacherLogin(Map<String, Object> map) {
        try {
            Person person = personDao.selectByParams(map);
            return person;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Person getTeacherInfo(Map<String, Object> map) {
        try {
            Person person = personDao.selectByParams(map);
            return person;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String updatePassword(Map<String, Object> map) {
        Long id = (Long) map.get("id");
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String updateTeacherInfo(Person person) {
        Long id = person.getId();
        try {
            Person temp = personDao.selectByPrimaryKey(id);
            if (null == temp) {
                return "该用户不存在";
            }
            int result = personDao.updateByPrimaryKeySelective(person);
            if (1 == result) {
                return "修改成功";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "修改失败";
    }

    @Override
    public Boolean saveTeacherPerformance(TeacherPerformance teacherPerformance) {
        teacherPerformance.setStatus("2"); //审核中
        try {
            int result = teacherPerformanceDao.insertSelective(teacherPerformance);
            if (1 == result) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Map<String, Object> totalRank(Long id, int pageSize, int pageNum) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("firstdata", (pageNum - 1) * pageSize);
        map.put("nums", pageSize);
        try {
            List<Person> personList = personDao.selectListByTotalRank(map);
            int rank = personDao.selectRankByTotal(id);
            int count = personDao.selectCount(map);

            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("personList", personList);
            resultMap.put("rank", rank);
            resultMap.put("count", count);
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, Object> scientificResearchRank(Long id, int pageSize, int pageNum) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("firstdata", (pageNum - 1) * pageSize);
        map.put("nums", pageSize);
        try {
            List<Person> personList = personDao.selectListByScientificResearch(map);
            int rank = personDao.selectRankByScientificResearch(id);
            int count = personDao.selectCount(map);

            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("personList", personList);
            resultMap.put("rank", rank);
            resultMap.put("count", count);
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, Object> teachingResearchRank(Long id, int pageSize, int pageNum) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("firstdata", (pageNum - 1) * pageSize);
        map.put("nums", pageSize);
        try {
            List<Person> personList = personDao.selectListByTeachingResearch(map);
            int rank = personDao.selectRankByTeachingResearch(id);
            int count = personDao.selectCount(map);

            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("personList", personList);
            resultMap.put("rank", rank);
            resultMap.put("count", count);
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, Object> getCheckPerformance(Long id, int pageSize, int pageNum) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("status", "2");
        map.put("firstdata", (pageNum - 1) * pageSize);
        map.put("nums", pageSize);
        List<TeacherPerformance> teacherPerformanceList = null;
        int total = 0;
        try {
            teacherPerformanceList = teacherPerformanceDao.selectListByParams(map);
            total = teacherPerformanceDao.selectCount(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (null != teacherPerformanceList && teacherPerformanceList.size() > 0) {
            resultMap.put("teacherPerformanceList", teacherPerformanceList);
        } else {
            resultMap.put("teacherPerformanceList", null);
        }
        resultMap.put("total", total);
        return resultMap;
    }

    @Override
    public List<String> getSciContent() {
        List<String> sciContentList = null;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", "0"); //正常
        try {
            sciContentList = scientificResearchDao.getSciContent(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null != sciContentList && sciContentList.size() > 0) {
            return sciContentList;
        }
        return null;
    }

    @Override
    public List<String> getTeaContent() {
        List<String> teaContentList = null;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", "0"); //正常
        try {
            teaContentList = teachingResearchDao.getTeaContent(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null != teaContentList && teaContentList.size() > 0) {
            return teaContentList;
        }
        return null;
    }

    @Override
    public List<String> getSciProjectBySciContent(Map<String, Object> map) {
        List<String> sciProjectList = null;
        try {
            sciProjectList = scientificResearchDao.getSciProjectBySciContent(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null != sciProjectList && sciProjectList.size() > 0) {
            return sciProjectList;
        }
        return null;
    }

    @Override
    public List<String> getTeaProjectByTeaContent(Map<String, Object> map) {
        List<String> teaProjectList = null;
        try {
            teaProjectList = teachingResearchDao.getTeaProjectByTeaContent(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null != teaProjectList && teaProjectList.size() > 0) {
            return teaProjectList;
        }
        return null;
    }

    @Override
    public List<String> getSciGradeBySciContentAndSciProject(Map<String, Object> map) {
        List<String> sciGradeList = null;
        try {
            sciGradeList = scientificResearchDao.getSciGradeBySciContentAndSciProject(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null != sciGradeList && sciGradeList.size() > 0) {
            return sciGradeList;
        }
        return null;
    }

    @Override
    public List<String> getTeaGradeByTeaContentAndTeaProject(Map<String, Object> map) {
        List<String> teaGradeList = null;
        try {
            teaGradeList = teachingResearchDao.getTeaGradeByTeaContentAndTeaProject(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null != teaGradeList && teaGradeList.size() > 0) {
            return teaGradeList;
        }
        return null;
    }

    @Override
    public String getTeacherName(Map<String, Object> map) {
        Person person = null;
        try {
            person = personDao.selectByParams(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null != person.getName()) {
            return person.getName();
        }
        return null;
    }

    @Override
    public int deleteCheckPerformance(List<Long> idList) {
        try {
            int result = teacherPerformanceDao.deleteByVirtualIdList(idList);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
