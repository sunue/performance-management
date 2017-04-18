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
     *
     * @mbggenerated
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

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column person.sex
     *
     * @return the value of person.sex
     *
     * @mbggenerated
     */
    public String getSex() {
        return sex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column person.sex
     *
     * @param sex the value for person.sex
     *
     * @mbggenerated
     */
    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column person.age
     *
     * @return the value of person.age
     *
     * @mbggenerated
     */
    public Integer getAge() {
        return age;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column person.age
     *
     * @param age the value for person.age
     *
     * @mbggenerated
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column person.title
     *
     * @return the value of person.title
     *
     * @mbggenerated
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column person.title
     *
     * @param title the value for person.title
     *
     * @mbggenerated
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column person.admission_time
     *
     * @return the value of person.admission_time
     *
     * @mbggenerated
     */
    public Date getAdmissionTime() {
        return admissionTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column person.admission_time
     *
     * @param admissionTime the value for person.admission_time
     *
     * @mbggenerated
     */
    public void setAdmissionTime(Date admissionTime) {
        this.admissionTime = admissionTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column person.grade
     *
     * @return the value of person.grade
     *
     * @mbggenerated
     */
    public Integer getGrade() {
        return grade;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column person.grade
     *
     * @param grade the value for person.grade
     *
     * @mbggenerated
     */
    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column person.scientific_research_score
     *
     * @return the value of person.scientific_research_score
     *
     * @mbggenerated
     */
    public Integer getScientificResearchScore() {
        return scientificResearchScore;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column person.scientific_research_score
     *
     * @param scientificResearchScore the value for person.scientific_research_score
     *
     * @mbggenerated
     */
    public void setScientificResearchScore(Integer scientificResearchScore) {
        this.scientificResearchScore = scientificResearchScore;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column person.teaching_research_score
     *
     * @return the value of person.teaching_research_score
     *
     * @mbggenerated
     */
    public Integer getTeachingResearchScore() {
        return teachingResearchScore;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column person.teaching_research_score
     *
     * @param teachingResearchScore the value for person.teaching_research_score
     *
     * @mbggenerated
     */
    public void setTeachingResearchScore(Integer teachingResearchScore) {
        this.teachingResearchScore = teachingResearchScore;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column person.status
     *
     * @return the value of person.status
     *
     * @mbggenerated
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column person.status
     *
     * @param status the value for person.status
     *
     * @mbggenerated
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}