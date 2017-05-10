package com.performance.service.impl;

import java.text.DateFormat;
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
    public Map<String, Object> teacherRegisterCheck(int pageSize, int pageNum) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("grade", 2); //教师
        map.put("status", "2"); //审核中
        map.put("firstdata", (pageNum - 1) * pageSize);
        map.put("nums", pageSize);
        List<Person> teacherList = null;
        int total = 0;
        try {
            teacherList = personDao.selectListByParams(map);
            total = personDao.selectCount(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (teacherList != null && teacherList.size() > 0) {
            DateFormat df = DateFormat.getDateInstance();
            for (int i = 0; i < teacherList.size(); i++) {
                if (null != teacherList.get(i).getAdmissionTime()) {
                    //     teacherList.get(i)
                    //         .setAdmissionTime(df.format(teacherList.get(i).getAdmissionTime()));
                    System.out.println(teacherList.get(i).getAdmissionTime());
                }
            }
            resultMap.put("teacherList", teacherList);
        } else {
            resultMap.put("teacherList", null);
        }
        resultMap.put("total", total);
        return resultMap;
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
        person.setScientificResearchScore(0);
        person.setTeachingResearchScore(0);
        try {
            Person temp = personDao.selectByPrimaryKey(person.getId());
            if (null != temp) {
                return -1;
            }
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
            int result = personDao.deleteByIdList(idList); //删除教师

            // 绩效表中删除该教师的绩效
            teacherPerformanceDao.deleteByIdList(idList);

            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Map<String, Object> getTeacher(Map<String, Object> map) {
        List<Person> personList = null;
        int total = 0;
        try {
            personList = personDao.selectListByIdAndName(map);
            total = personDao.selectCount(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (null != personList && personList.size() > 0) {
            resultMap.put("personList", personList);
        } else {
            resultMap.put("personList", null);
        }
        resultMap.put("total", total);
        return resultMap;
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
    public Integer teacherPerformanceCheckSum() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", "2");
        Integer sum = null;
        try {
            sum = teacherPerformanceDao.selectCount(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sum;
    }

    @Override
    public Map<String, Object> teacherPerformanceCheck(int pageSize, int pageNum) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", "2"); //待审核
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
        Map<String, Object> resultmap = new HashMap<String, Object>();
        if (null != teacherPerformanceList && teacherPerformanceList.size() > 0) {
            resultmap.put("teacherPerformanceList", teacherPerformanceList);
        } else {
            resultmap.put("teacherPerformanceList", null);
        }
        resultmap.put("total", total);
        return resultmap;
    }

    @Override
    public int teacherPerformanceAgree(Long virtualId, Long id) {
        TeacherPerformance teacherPerformance = new TeacherPerformance();
        teacherPerformance.setVirtualId(virtualId);
        teacherPerformance.setStatus("0");
        try {
            //查询是否有该教师存在
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", id);
            map.put("grade", 2); //教师
            map.put("status", "0"); //正常
            Person personTemp = personDao.selectByParams(map);
            if (null == personTemp) {
                return -2;
            }

            //查询是否存在该条绩效
            TeacherPerformance temp = teacherPerformanceDao.selectByPrimaryKey(virtualId);
            if (null == temp) {
                return -1;
            }

            //查询该绩效对应的分值
            Integer score = null;
            Map<String, Object> getScoreMap = new HashMap<String, Object>();
            if (temp.getCategory().equals("科研")) {
                getScoreMap.put("sciContent", temp.getContent());
                getScoreMap.put("sciProject", temp.getProject());
                getScoreMap.put("sciGrade", temp.getProGrade());
                getScoreMap.put("status", "0"); //正常
                score = scientificResearchDao.getScore(getScoreMap);
            } else if (temp.getCategory().equals("教研")) {
                getScoreMap.put("teaContent", temp.getContent());
                getScoreMap.put("teaProject", temp.getProject());
                getScoreMap.put("teaGrade", temp.getProGrade());
                getScoreMap.put("status", "0"); //正常
                score = teachingResearchDao.getScore(getScoreMap);
            }
            if (-1 == score) {
                return -3;
            }

            System.out.println(score);

            //增加该教师相应的科研或教研分数
            int updateScore = 0;
            if (temp.getCategory().equals("科研")) {
                personTemp
                    .setScientificResearchScore(personTemp.getScientificResearchScore() + score);
                updateScore = personDao.updateByPrimaryKeySelective(personTemp);
            } else if (temp.getCategory().equals("教研")) {
                personTemp.setTeachingResearchScore(personTemp.getTeachingResearchScore() + score);
                updateScore = personDao.updateByPrimaryKeySelective(personTemp);
            }
            if (1 != updateScore) {
                return -4;
            }

            System.out.println(updateScore);

            //修改绩效状态为0：正常
            int result = teacherPerformanceDao.updateByPrimaryKeySelective(teacherPerformance);

            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int teacherPerformanceFail(Long virtualId) {
        TeacherPerformance teacherPerformance = new TeacherPerformance();
        teacherPerformance.setVirtualId(virtualId);
        teacherPerformance.setStatus("3"); //未通过
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("virtualId", virtualId);
            map.put("status", "2"); //审核中
            TeacherPerformance temp = teacherPerformanceDao.selectByParams(map);
            if (null == temp) {
                return -1;
            }
            int result = teacherPerformanceDao.updateByPrimaryKeySelective(teacherPerformance);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int addScientificResearchPerformance(ScientificResearch scientificResearch) {
        scientificResearch.setStatus("0");
        try {
            //查询该绩效是否已存在
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("sciContent", scientificResearch.getSciContent());
            map.put("sciProject", scientificResearch.getSciProject());
            map.put("sciGrade", scientificResearch.getSciGrade());
            ScientificResearch temp = scientificResearchDao.selectByParams(map);
            if (null != temp) {
                return -1;
            }

            int result = scientificResearchDao.insertSelective(scientificResearch);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int addTeachingResearchPerformance(TeachingResearch teachingResearch) {
        teachingResearch.setStatus("0");
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("teaContent", teachingResearch.getTeaContent());
            map.put("teaProject", teachingResearch.getTeaProject());
            map.put("teaGrade", teachingResearch.getTeaGrade());
            TeachingResearch temp = teachingResearchDao.selectByParams(map);
            if (null != temp) {
                return -1;
            }

            int result = teachingResearchDao.insertSelective(teachingResearch);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int deleteScientificResearchPerformance(List<Long> virtualIdList) {
        try {
            int result = scientificResearchDao.deleteByIdList(virtualIdList);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int deteleTeachingResearchPerformance(List<Long> virtualIdList) {
        try {
            int result = teachingResearchDao.deleteByIdList(virtualIdList);
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
        map.put("firstdata", (pageNum - 1) * pageSize);
        map.put("nums", pageSize);
        List<ScientificResearch> scientificResearchList = null;
        int total = 0;
        try {
            scientificResearchList = scientificResearchDao.selectListByParams(map);
            total = scientificResearchDao.selectCount(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (null != scientificResearchList && scientificResearchList.size() > 0) {
            resultMap.put("scientificResearchList", scientificResearchList);
        } else {
            resultMap.put("scientificResearchList", null);
        }
        resultMap.put("total", total);
        return resultMap;
    }

    @Override
    public Map<String, Object> getTeachingResearchPerformance(int pageSize, int pageNum) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", "0");
        map.put("firstdata", (pageNum - 1) * pageSize);
        map.put("nums", pageSize);
        List<TeachingResearch> teachingResearchList = null;
        int total = 0;
        try {
            teachingResearchList = teachingResearchDao.selectListByParams(map);
            total = teachingResearchDao.selectCount(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (null != teachingResearchList && teachingResearchList.size() > 0) {
            resultMap.put("teachingResearchList", teachingResearchList);
        } else {
            resultMap.put("teachingResearchList", null);
        }
        resultMap.put("total", total);
        return resultMap;
    }

    @Override
    public String getAdministratorName(Map<String, Object> map) {
        try {
            Person person = personDao.selectByParams(map);
            if (null != person) {
                return person.getName();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, Object> totalRank(Long id, int pageSize, int pageNum) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("firstdata", (pageNum - 1) * pageSize);
        map.put("nums", pageSize);
        map.put("grade", 2); //教师
        map.put("status", "0"); //正常
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
        map.put("grade", 2); //教师
        map.put("status", "0"); //正常
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
        map.put("grade", 2); //教师
        map.put("status", "0"); //正常
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
    public Map<String, Object> getTeacherRank(Long id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("grade", 2); //教师
        map.put("status", "0"); //正常
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            Person temp = personDao.selectByParams(map);
            if (null == temp) {
                resultMap.put("flag", -1); //无该用户存在
            } else {
                resultMap.put("flag", 0);
                resultMap.put("person", temp);
                int totalRank = personDao.selectRankByTotal(id);
                int scientificResearchRank = personDao.selectRankByScientificResearch(id);
                int teachingResearchRank = personDao.selectRankByTeachingResearch(id);
                resultMap.put("totalRank", totalRank + 1);
                resultMap.put("scientificResearchRank", scientificResearchRank + 1);
                resultMap.put("teachingResearchRank", teachingResearchRank + 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultMap;
    }
}
