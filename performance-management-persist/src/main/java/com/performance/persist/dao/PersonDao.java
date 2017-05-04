package com.performance.persist.dao;

import java.util.List;
import java.util.Map;

import com.performance.persist.domain.Person;

public interface PersonDao {

    int deleteByPrimaryKey(Long id);

    int deleteByIdList(List<Long> idList);

    int insert(Person record);

    int insertSelective(Person record);

    Person selectByPrimaryKey(Long id);

    Person selectByParams(Map<String, Object> map);

    List<Person> selectListByParams(Map<String, Object> map);

    List<Person> selectListByIdAndName(Map<String, Object> map);

    List<Person> selectListByTotalRank(Map<String, Object> map);

    List<Person> selectListByScientificResearch(Map<String, Object> map);

    List<Person> selectListByTeachingResearch(Map<String, Object> map);

    int selectRankByTotal(Long id);

    int selectRankByScientificResearch(Long id);

    int selectRankByTeachingResearch(Long id);

    int selectCount(Map<String, Object> map);

    int updateByPrimaryKeySelective(Person record);

    int updateByPrimaryKey(Person record);
}