package com.performance.persist.dao;

import com.performance.persist.domain.TeachingResearch;

public interface TeachingResearchDao {

    int deleteByPrimaryKey(Long id);

    int insert(TeachingResearch record);

    int insertSelective(TeachingResearch record);

    TeachingResearch selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TeachingResearch record);

    int updateByPrimaryKey(TeachingResearch record);
}