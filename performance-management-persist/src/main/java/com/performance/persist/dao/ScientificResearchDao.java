package com.performance.persist.dao;

import com.performance.persist.domain.ScientificResearch;

public interface ScientificResearchDao {

    int deleteByPrimaryKey(Long id);

    int insert(ScientificResearch record);

    int insertSelective(ScientificResearch record);

    ScientificResearch selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ScientificResearch record);

    int updateByPrimaryKey(ScientificResearch record);
}