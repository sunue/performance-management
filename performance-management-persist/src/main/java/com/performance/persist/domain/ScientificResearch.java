package com.performance.persist.domain;

public class ScientificResearch {
    /**
     * This field corresponds to the database column scientific_research.id
     */
    private Long virtualId;

    /**
     * This field corresponds to the database column scientific_research.sci_content
     */
    private String sciContent;

    /**
     * This field corresponds to the database column scientific_research.sci_project
     */
    private String sciProject;

    /**
     * This field corresponds to the database column scientific_research.sci_grade
     */
    private String sciGrade;

    /**
     * This field corresponds to the database column scientific_research.sci_score
     */
    private Integer sciScore;

    /**
     * This field corresponds to the database column scientific_research.status
     * 0-正常；1-删除
     */
    private String status;

    public Long getVirtualId() {
        return virtualId;
    }

    public void setVirtualId(Long virtualId) {
        this.virtualId = virtualId;
    }

    public String getSciContent() {
        return sciContent;
    }

    public void setSciContent(String sciContent) {
        this.sciContent = sciContent == null ? null : sciContent.trim();
    }

    public String getSciProject() {
        return sciProject;
    }

    public void setSciProject(String sciProject) {
        this.sciProject = sciProject == null ? null : sciProject.trim();
    }

    public String getSciGrade() {
        return sciGrade;
    }

    public void setSciGrade(String sciGrade) {
        this.sciGrade = sciGrade == null ? null : sciGrade.trim();
    }

    public Integer getSciScore() {
        return sciScore;
    }

    public void setSciScore(Integer sciScore) {
        this.sciScore = sciScore;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}