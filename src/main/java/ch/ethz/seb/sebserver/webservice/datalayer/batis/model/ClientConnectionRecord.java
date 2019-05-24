package ch.ethz.seb.sebserver.webservice.datalayer.batis.model;

import javax.annotation.Generated;

public class ClientConnectionRecord {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-05-24T11:57:58.084+02:00", comments="Source field: client_connection.id")
    private Long id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-05-24T11:57:58.084+02:00", comments="Source field: client_connection.exam_id")
    private Long examId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-05-24T11:57:58.084+02:00", comments="Source field: client_connection.status")
    private String status;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-05-24T11:57:58.084+02:00", comments="Source field: client_connection.connection_token")
    private String connectionToken;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-05-24T11:57:58.084+02:00", comments="Source field: client_connection.user_name")
    private String userName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-05-24T11:57:58.084+02:00", comments="Source field: client_connection.vdi")
    private Boolean vdi;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-05-24T11:57:58.085+02:00", comments="Source field: client_connection.client_address")
    private String clientAddress;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-05-24T11:57:58.085+02:00", comments="Source field: client_connection.virtual_client_address")
    private String virtualClientAddress;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-05-24T11:57:58.084+02:00", comments="Source Table: client_connection")
    public ClientConnectionRecord(Long id, Long examId, String status, String connectionToken, String userName, Boolean vdi, String clientAddress, String virtualClientAddress) {
        this.id = id;
        this.examId = examId;
        this.status = status;
        this.connectionToken = connectionToken;
        this.userName = userName;
        this.vdi = vdi;
        this.clientAddress = clientAddress;
        this.virtualClientAddress = virtualClientAddress;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-05-24T11:57:58.084+02:00", comments="Source field: client_connection.id")
    public Long getId() {
        return id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-05-24T11:57:58.084+02:00", comments="Source field: client_connection.exam_id")
    public Long getExamId() {
        return examId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-05-24T11:57:58.084+02:00", comments="Source field: client_connection.status")
    public String getStatus() {
        return status;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-05-24T11:57:58.084+02:00", comments="Source field: client_connection.connection_token")
    public String getConnectionToken() {
        return connectionToken;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-05-24T11:57:58.084+02:00", comments="Source field: client_connection.user_name")
    public String getUserName() {
        return userName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-05-24T11:57:58.085+02:00", comments="Source field: client_connection.vdi")
    public Boolean getVdi() {
        return vdi;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-05-24T11:57:58.085+02:00", comments="Source field: client_connection.client_address")
    public String getClientAddress() {
        return clientAddress;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-05-24T11:57:58.085+02:00", comments="Source field: client_connection.virtual_client_address")
    public String getVirtualClientAddress() {
        return virtualClientAddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table client_connection
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
        sb.append(", examId=").append(examId);
        sb.append(", status=").append(status);
        sb.append(", connectionToken=").append(connectionToken);
        sb.append(", userName=").append(userName);
        sb.append(", vdi=").append(vdi);
        sb.append(", clientAddress=").append(clientAddress);
        sb.append(", virtualClientAddress=").append(virtualClientAddress);
        sb.append("]");
        return sb.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table client_connection
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
        ClientConnectionRecord other = (ClientConnectionRecord) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getExamId() == null ? other.getExamId() == null : this.getExamId().equals(other.getExamId()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getConnectionToken() == null ? other.getConnectionToken() == null : this.getConnectionToken().equals(other.getConnectionToken()))
            && (this.getUserName() == null ? other.getUserName() == null : this.getUserName().equals(other.getUserName()))
            && (this.getVdi() == null ? other.getVdi() == null : this.getVdi().equals(other.getVdi()))
            && (this.getClientAddress() == null ? other.getClientAddress() == null : this.getClientAddress().equals(other.getClientAddress()))
            && (this.getVirtualClientAddress() == null ? other.getVirtualClientAddress() == null : this.getVirtualClientAddress().equals(other.getVirtualClientAddress()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table client_connection
     *
     * @mbg.generated Fri May 24 11:57:58 CEST 2019
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getExamId() == null) ? 0 : getExamId().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getConnectionToken() == null) ? 0 : getConnectionToken().hashCode());
        result = prime * result + ((getUserName() == null) ? 0 : getUserName().hashCode());
        result = prime * result + ((getVdi() == null) ? 0 : getVdi().hashCode());
        result = prime * result + ((getClientAddress() == null) ? 0 : getClientAddress().hashCode());
        result = prime * result + ((getVirtualClientAddress() == null) ? 0 : getVirtualClientAddress().hashCode());
        return result;
    }
}