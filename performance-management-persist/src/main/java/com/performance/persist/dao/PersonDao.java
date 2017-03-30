package com.performance.persist.dao;

import java.util.Map;

import com.performance.persist.domain.Person;

public interface PersonDao {

    int deleteByPrimaryKey(Long id);

    int insert(Person record);

    int insertSelective(Person record);

    Person selectByPrimaryKey(Long id);

    Person selectByParams(Map<String, Object> map);

    int updateByPrimaryKeySelective(Person record);

    int updateByPrimaryKey(Person record);
}