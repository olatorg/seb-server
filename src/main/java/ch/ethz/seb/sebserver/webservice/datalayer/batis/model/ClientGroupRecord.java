package ch.ethz.seb.sebserver.webservice.datalayer.batis.model;

import javax.annotation.Generated;

public class ClientGroupRecord {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-11-08T16:12:34.719+01:00", comments="Source field: client_group.id")
    private Long id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-11-08T16:12:34.719+01:00", comments="Source field: client_group.exam_id")
    private Long examId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-11-08T16:12:34.719+01:00", comments="Source field: client_group.name")
    private String name;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-11-08T16:12:34.719+01:00", comments="Source field: client_group.type")
    private String type;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-11-08T16:12:34.719+01:00", comments="Source field: client_group.color")
    private String color;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-11-08T16:12:34.719+01:00", comments="Source field: client_group.icon")
    private String icon;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-11-08T16:12:34.719+01:00", comments="Source field: client_group.data")
    private String data;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-11-08T16:12:34.719+01:00", comments="Source Table: client_group")
    public ClientGroupRecord(Long id, Long examId, String name, String type, String color, String icon, String data) {
        this.id = id;
        this.examId = examId;
        this.name = name;
        this.type = type;
        this.color = color;
        this.icon = icon;
        this.data = data;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-11-08T16:12:34.719+01:00", comments="Source field: client_group.id")
    public Long getId() {
        return id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-11-08T16:12:34.719+01:00", comments="Source field: client_group.exam_id")
    public Long getExamId() {
        return examId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-11-08T16:12:34.719+01:00", comments="Source field: client_group.name")
    public String getName() {
        return name;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-11-08T16:12:34.719+01:00", comments="Source field: client_group.type")
    public String getType() {
        return type;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-11-08T16:12:34.719+01:00", comments="Source field: client_group.color")
    public String getColor() {
        return color;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-11-08T16:12:34.719+01:00", comments="Source field: client_group.icon")
    public String getIcon() {
        return icon;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-11-08T16:12:34.719+01:00", comments="Source field: client_group.data")
    public String getData() {
        return data;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table client_group
     *
     * @mbg.generated Tue Nov 08 16:12:34 CET 2022
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", examId=").append(examId);
        sb.append(", name=").append(name);
        sb.append(", type=").append(type);
        sb.append(", color=").append(color);
        sb.append(", icon=").append(icon);
        sb.append(", data=").append(data);
        sb.append("]");
        return sb.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table client_group
     *
     * @mbg.generated Tue Nov 08 16:12:34 CET 2022
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ClientGroupRecord other = (ClientGroupRecord) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getExamId() == null ? other.getExamId() == null : this.getExamId().equals(other.getExamId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getColor() == null ? other.getColor() == null : this.getColor().equals(other.getColor()))
            && (this.getIcon() == null ? other.getIcon() == null : this.getIcon().equals(other.getIcon()))
            && (this.getData() == null ? other.getData() == null : this.getData().equals(other.getData()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table client_group
     *
     * @mbg.generated Tue Nov 08 16:12:34 CET 2022
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getExamId() == null) ? 0 : getExamId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getColor() == null) ? 0 : getColor().hashCode());
        result = prime * result + ((getIcon() == null) ? 0 : getIcon().hashCode());
        result = prime * result + ((getData() == null) ? 0 : getData().hashCode());
        return result;
    }
}