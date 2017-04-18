package com.performance.persist.domain;

public class TeachingResearch {
    /**
     * This field corresponds to the database column teaching_research.id
     */
    private Long virtualId;

    /**
     * This field corresponds to the database column teaching_research.tea_content
     */
    private String teaContent;

    /**
     * This field corresponds to the database column teaching_research.tea_project
     */
    private String teaProject;

    /**
     * This field corresponds to the database column teaching_research.tea_grade
     */
    private String teaGrade;

    /**
     * This field corresponds to the database column teaching_research.tea_score
     */
    private Integer teaScore;

    /**
     * This field corresponds to the database column teaching_research.status
     */
    private String status;

    public Long getVirtualId() {
        return virtualId;
    }

    public void setVirtualId(Long virtualId) {
        this.virtualId = virtualId;
    }

    /**
     * This method returns the value of the database column teaching_research.tea_content
     * @return the value of teaching_research.tea_content
     */
    public String getTeaContent() {
        return teaContent;
    }

    /**
     * This method sets the value of the database column teaching_research.tea_content
     * @param teaContent the value for teaching_research.tea_content
     */
    public void setTeaContent(String teaContent) {
        this.teaContent = teaContent == null ? null : teaContent.trim();
    }

    /**
     * This method returns the value of the database column teaching_research.tea_project
     * @return the value of teaching_research.tea_project
     */
    public String getTeaProject() {
        return teaProject;
    }

    /**
     * This method sets the value of the database column teaching_research.tea_project
     * @param teaProject the value for teaching_research.tea_project
     */
    public void setTeaProject(String teaProject) {
        this.teaProject = teaProject == null ? null : teaProject.trim();
    }

    /**
     * This method returns the value of the database column teaching_research.tea_grade
     * @return the value of teaching_research.tea_grade
     */
    public String getTeaGrade() {
        return teaGrade;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column teaching_research.tea_grade
     *
     * @param teaGrade the value for teaching_research.tea_grade
     *
     * @mbggenerated
     */
    public void setTeaGrade(String teaGrade) {
        this.teaGrade = teaGrade == null ? null : teaGrade.trim();
    }

    /**
     * This method returns the value of the database column teaching_research.tea_score
     * @return the value of teaching_research.tea_score
     */
    public Integer getTeaScore() {
        return teaScore;
    }

    /**
     * This method sets the value of the database column teaching_research.tea_score
     * @param teaScore the value for teaching_research.tea_score
     */
    public void setTeaScore(Integer teaScore) {
        this.teaScore = teaScore;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column teaching_research.status
     *
     * @return the value of teaching_research.status
     *
     * @mbggenerated
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column teaching_research.status
     *
     * @param status the value for teaching_research.status
     *
     * @mbggenerated
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}