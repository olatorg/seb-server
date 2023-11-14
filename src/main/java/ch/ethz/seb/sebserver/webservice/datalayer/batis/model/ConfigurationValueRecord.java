package ch.ethz.seb.sebserver.webservice.datalayer.batis.model;

import javax.annotation.Generated;

public class ConfigurationValueRecord {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2023-11-14T09:46:30.139+01:00", comments="Source field: configuration_value.id")
    private Long id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2023-11-14T09:46:30.140+01:00", comments="Source field: configuration_value.institution_id")
    private Long institutionId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2023-11-14T09:46:30.140+01:00", comments="Source field: configuration_value.configuration_id")
    private Long configurationId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2023-11-14T09:46:30.140+01:00", comments="Source field: configuration_value.configuration_attribute_id")
    private Long configurationAttributeId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2023-11-14T09:46:30.140+01:00", comments="Source field: configuration_value.list_index")
    private Integer listIndex;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2023-11-14T09:46:30.140+01:00", comments="Source field: configuration_value.value")
    private String value;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2023-11-14T09:46:30.139+01:00", comments="Source Table: configuration_value")
    public ConfigurationValueRecord(Long id, Long institutionId, Long configurationId, Long configurationAttributeId, Integer listIndex, String value) {
        this.id = id;
        this.institutionId = institutionId;
        this.configurationId = configurationId;
        this.configurationAttributeId = configurationAttributeId;
        this.listIndex = listIndex;
        this.value = value;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2023-11-14T09:46:30.140+01:00", comments="Source field: configuration_value.id")
    public Long getId() {
        return id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2023-11-14T09:46:30.140+01:00", comments="Source field: configuration_value.institution_id")
    public Long getInstitutionId() {
        return institutionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2023-11-14T09:46:30.140+01:00", comments="Source field: configuration_value.configuration_id")
    public Long getConfigurationId() {
        return configurationId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2023-11-14T09:46:30.140+01:00", comments="Source field: configuration_value.configuration_attribute_id")
    public Long getConfigurationAttributeId() {
        return configurationAttributeId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2023-11-14T09:46:30.140+01:00", comments="Source field: configuration_value.list_index")
    public Integer getListIndex() {
        return listIndex;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2023-11-14T09:46:30.140+01:00", comments="Source field: configuration_value.value")
    public String getValue() {
        return value;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table configuration_value
     *
     * @mbg.generated Tue Nov 14 09:46:30 CET 2023
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", institutionId=").append(institutionId);
        sb.append(", configurationId=").append(configurationId);
        sb.append(", configurationAttributeId=").append(configurationAttributeId);
        sb.append(", listIndex=").append(listIndex);
        sb.append(", value=").append(value);
        sb.append("]");
        return sb.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table configuration_value
     *
     * @mbg.generated Tue Nov 14 09:46:30 CET 2023
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
        ConfigurationValueRecord other = (ConfigurationValueRecord) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getInstitutionId() == null ? other.getInstitutionId() == null : this.getInstitutionId().equals(other.getInstitutionId()))
            && (this.getConfigurationId() == null ? other.getConfigurationId() == null : this.getConfigurationId().equals(other.getConfigurationId()))
            && (this.getConfigurationAttributeId() == null ? other.getConfigurationAttributeId() == null : this.getConfigurationAttributeId().equals(other.getConfigurationAttributeId()))
            && (this.getListIndex() == null ? other.getListIndex() == null : this.getListIndex().equals(other.getListIndex()))
            && (this.getValue() == null ? other.getValue() == null : this.getValue().equals(other.getValue()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table configuration_value
     *
     * @mbg.generated Tue Nov 14 09:46:30 CET 2023
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getInstitutionId() == null) ? 0 : getInstitutionId().hashCode());
        result = prime * result + ((getConfigurationId() == null) ? 0 : getConfigurationId().hashCode());
        result = prime * result + ((getConfigurationAttributeId() == null) ? 0 : getConfigurationAttributeId().hashCode());
        result = prime * result + ((getListIndex() == null) ? 0 : getListIndex().hashCode());
        result = prime * result + ((getValue() == null) ? 0 : getValue().hashCode());
        return result;
    }
}