package com.performance.persist.dao;

import java.util.List;
import java.util.Map;

import com.performance.persist.domain.TeachingResearch;

public interface TeachingResearchDao {

    int deleteByPrimaryKey(Long id);

    int deleteByIdList(List<Long> idList);

    int insert(TeachingResearch record);

    int insertSelective(TeachingResearch record);

    TeachingResearch selectByPrimaryKey(Long id);

    List<TeachingResearch> selectListByParams(Map<String, Object> map);

    int selectCount(Map<String, Object> map);

    int updateByPrimaryKeySelective(TeachingResearch record);

    int updateByPrimaryKey(TeachingResearch record);

    int getScore(Map<String, Object> map);
}