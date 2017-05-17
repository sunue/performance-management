package com.performance.persist.domain;

public class TeacherPerformance {
    private Long virtualId;

    /**
     * This field corresponds to the database column teacher_performance.id
     */
    private Long id;

    /**
     * This field corresponds to the database column teacher_performance.category
     */
    private String category;

    /**
     * This field corresponds to the database column teacher_performance.content
     */
    private String content;

    /**
     * This field corresponds to the database column teacher_performance.project
     */
    private String project;

    /**
     * This field corresponds to the database column teacher_performance.pro_grade
     */
    private String proGrade;

    /**
     * This field corresponds to the database column teacher_performance.status
     * 0-正常；1-删除；2-审核中；3-未通过
     */
    private String status;

    /**
     * 上传资料
     * */
    private String upload;

    public String getUpload() {
        return upload;
    }

    public void setUpload(String upload) {
        this.upload = upload;
    }

    public Long getVirtualId() {
        return virtualId;
    }

    public void setVirtualId(Long virtualId) {
        this.virtualId = virtualId;
    }

    /**
     * This method returns the value of the database column teacher_performance.id
     * @return the value of teacher_performance.id
     */
    public Long getId() {
        return id;
    }

    /**
     * This method sets the value of the database column teacher_performance.id
     * @param id the value for teacher_performance.id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method returns the value of the database column teacher_performance.category
     * @return the value of teacher_performance.category
     */
    public String getCategory() {
        return category;
    }

    /**
     * This method sets the value of the database column teacher_performance.category
     * @param category the value for teacher_performance.category
     */
    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    /**
     * This method returns the value of the database column teacher_performance.content
     * @return the value of teacher_performance.content
     */
    public String getContent() {
        return content;
    }

    /**
     * This method sets the value of the database column teacher_performance.content
     * @param content the value for teacher_performance.content
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * This method returns the value of the database column teacher_performance.project
     * @return the value of teacher_performance.project
     */
    public String getProject() {
        return project;
    }

    /**
     * This method sets the value of the database column teacher_performance.project
     * @param project the value for teacher_performance.project
     */
    public void setProject(String project) {
        this.project = project == null ? null : project.trim();
    }

    /**
     * This method returns the value of the database column teacher_performance.pro_grade
     * @return the value of teacher_performance.pro_grade
     */
    public String getProGrade() {
        return proGrade;
    }

    /**
     * This method sets the value of the database column teacher_performance.pro_grade
     * @param proGrade the value for teacher_performance.pro_grade
     */
    public void setProGrade(String proGrade) {
        this.proGrade = proGrade == null ? null : proGrade.trim();
    }

    /**
     * This method returns the value of the database column teacher_performance.status
     * @return the value of teacher_performance.status
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method sets the value of the database column teacher_performance.status
     * @param status the value for teacher_performance.status
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}