package ch.ethz.seb.sebserver.webservice.datalayer.batis.model;

import javax.annotation.Generated;

public class RoleRecord {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2023-11-14T09:46:30.161+01:00", comments="Source field: user_role.id")
    private Long id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2023-11-14T09:46:30.161+01:00", comments="Source field: user_role.user_id")
    private Long userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2023-11-14T09:46:30.161+01:00", comments="Source field: user_role.role_name")
    private String roleName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2023-11-14T09:46:30.161+01:00", comments="Source Table: user_role")
    public RoleRecord(Long id, Long userId, String roleName) {
        this.id = id;
        this.userId = userId;
        this.roleName = roleName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2023-11-14T09:46:30.161+01:00", comments="Source field: user_role.id")
    public Long getId() {
        return id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2023-11-14T09:46:30.161+01:00", comments="Source field: user_role.user_id")
    public Long getUserId() {
        return userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2023-11-14T09:46:30.161+01:00", comments="Source field: user_role.role_name")
    public String getRoleName() {
        return roleName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_role
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
        sb.append(", userId=").append(userId);
        sb.append(", roleName=").append(roleName);
        sb.append("]");
        return sb.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_role
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
        RoleRecord other = (RoleRecord) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getRoleName() == null ? other.getRoleName() == null : this.getRoleName().equals(other.getRoleName()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_role
     *
     * @mbg.generated Tue Nov 14 09:46:30 CET 2023
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getRoleName() == null) ? 0 : getRoleName().hashCode());
        return result;
    }
}