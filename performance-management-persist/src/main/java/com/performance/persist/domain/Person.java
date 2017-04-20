package com.performance.persist.domain;

import java.util.Date;

public class Person {
    /**
     * This field corresponds to the database column person.id
     * 教职工工号
     */
    private Long id;

    /**
     * This field corresponds to the database column person.name
     */
    private String name;

    /**
     * This field corresponds to the database column person.password
     */
    private String password;

    /**
     * This field corresponds to the database column person.sex
     */
    private String sex;

    /**
     * This field corresponds to the database column person.age
     */
    private Integer age;

    /**
     * This field corresponds to the database column person.title
     */
    private String title;

    /**
     * This field corresponds to the database column person.admission_time
     */
    private Date admissionTime;

    /**
     * This field corresponds to the database column person.grade
     * 等级（管理员=1，教师=2）
     */
    private Integer grade;

    /**
     * This field corresponds to the database column person.scientific_research_score
     */
    private Integer scientificResearchScore;

    /**
     * This field corresponds to the database column person.teaching_research_score
     */
    private Integer teachingResearchScore;

    /**
     * This field corresponds to the database column person.status
     * 0-正常；1-删除；2-审核中；3-未通过
     */
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Date getAdmissionTime() {
        return admissionTime;
    }

    public void setAdmissionTime(Date admissionTime) {
        this.admissionTime = admissionTime;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getScientificResearchScore() {
        return scientificResearchScore;
    }

    public void setScientificResearchScore(Integer scientificResearchScore) {
        this.scientificResearchScore = scientificResearchScore;
    }

    public Integer getTeachingResearchScore() {
        return teachingResearchScore;
    }

    public void setTeachingResearchScore(Integer teachingResearchScore) {
        this.teachingResearchScore = teachingResearchScore;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}