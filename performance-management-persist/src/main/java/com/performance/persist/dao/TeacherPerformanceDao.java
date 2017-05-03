package com.performance.persist.dao;

import java.util.List;
import java.util.Map;

import com.performance.persist.domain.TeacherPerformance;

public interface TeacherPerformanceDao {

    int deleteByPrimaryKey(Long id);

    int deleteByIdList(List<Long> idList);

    int insert(TeacherPerformance record);

    int insertSelective(TeacherPerformance record);

    TeacherPerformance selectByPrimaryKey(Long id);

    List<TeacherPerformance> selectListByParams(Map<String, Object> map);

    int selectCount(Map<String, Object> map);

    int updateByPrimaryKeySelective(TeacherPerformance record);

    int updateByPrimaryKey(TeacherPerformance record);
}