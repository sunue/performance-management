package com.performance.persist.dao;

import java.util.List;
import java.util.Map;

import com.performance.persist.domain.ScientificResearch;

public interface ScientificResearchDao {

    int deleteByPrimaryKey(Long id);

    int deleteByIdList(List<Long> idList);

    int insert(ScientificResearch record);

    int insertSelective(ScientificResearch record);

    ScientificResearch selectByPrimaryKey(Long id);

    int selectCount(Map<String, Object> map);

    List<ScientificResearch> selectListByParams(Map<String, Object> map);

    int updateByPrimaryKeySelective(ScientificResearch record);

    int updateByPrimaryKey(ScientificResearch record);
}