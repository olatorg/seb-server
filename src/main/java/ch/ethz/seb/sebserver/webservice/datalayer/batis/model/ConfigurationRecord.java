package ch.ethz.seb.sebserver.webservice.datalayer.batis.model;

import javax.annotation.Generated;
import org.joda.time.DateTime;

public class ConfigurationRecord {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-05-24T11:57:58.057+02:00", comments="Source field: configuration.id")
    private Long id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-05-24T11:57:58.057+02:00", comments="Source field: configuration.institution_id")
    private Long institutionId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-05-24T11:57:58.057+02:00", comments="Source field: configuration.configuration_node_id")
    private Long configurationNodeId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-05-24T11:57:58.058+02:00", comments="Source field: configuration.version")
    private String version;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-05-24T11:57:58.058+02:00", comments="Source field: configuration.version_date")
    private DateTime versionDate;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-05-24T11:57:58.058+02:00", comments="Source field: configuration.followup")
    private Integer followup;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-05-24T11:57:58.057+02:00", comments="Source Table: configuration")
    public ConfigurationRecord(Long id, Long institutionId, Long configurationNodeId, String version, DateTime versionDate, Integer followup) {
        this.id = id;
        this.institutionId = institutionId;
        this.configurationNodeId = configurationNodeId;
        this.version = version;
        this.versionDate = versionDate;
        this.followup = followup;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-05-24T11:57:58.057+02:00", comments="Source field: configuration.id")
    public Long getId() {
        return id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-05-24T11:57:58.057+02:00", comments="Source field: configuration.institution_id")
    public Long getInstitutionId() {
        return institutionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-05-24T11:57:58.057+02:00", comments="Source field: configuration.configuration_node_id")
    public Long getConfigurationNodeId() {
        return configurationNodeId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-05-24T11:57:58.058+02:00", comments="Source field: configuration.version")
    public String getVersion() {
        return version;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-05-24T11:57:58.058+02:00", comments="Source field: configuration.version_date")
    public DateTime getVersionDate() {
        return versionDate;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-05-24T11:57:58.058+02:00", comments="Source field: configuration.followup")
    public Integer getFollowup() {
        return followup;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table configuration
     *
     * @mbg.generated Fri May 24 11:57:58 CEST 2019
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", institutionId=").append(institutionId);
        sb.append(", configurationNodeId=").append(configurationNodeId);
        sb.append(", version=").append(version);
        sb.append(", versionDate=").append(versionDate);
        sb.append(", followup=").append(followup);
        sb.append("]");
        return sb.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table configuration
     *
     * @mbg.generated Fri May 24 11:57:58 CEST 2019
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
        ConfigurationRecord other = (ConfigurationRecord) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getInstitutionId() == null ? other.getInstitutionId() == null : this.getInstitutionId().equals(other.getInstitutionId()))
            && (this.getConfigurationNodeId() == null ? other.getConfigurationNodeId() == null : this.getConfigurationNodeId().equals(other.getConfigurationNodeId()))
            && (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
            && (this.getVersionDate() == null ? other.getVersionDate() == null : this.getVersionDate().equals(other.getVersionDate()))
            && (this.getFollowup() == null ? other.getFollowup() == null : this.getFollowup().equals(other.getFollowup()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table configuration
     *
     * @mbg.generated Fri May 24 11:57:58 CEST 2019
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getInstitutionId() == null) ? 0 : getInstitutionId().hashCode());
        result = prime * result + ((getConfigurationNodeId() == null) ? 0 : getConfigurationNodeId().hashCode());
        result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
        result = prime * result + ((getVersionDate() == null) ? 0 : getVersionDate().hashCode());
        result = prime * result + ((getFollowup() == null) ? 0 : getFollowup().hashCode());
        return result;
    }
}