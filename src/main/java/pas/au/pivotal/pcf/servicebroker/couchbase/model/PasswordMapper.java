package pas.au.pivotal.pcf.servicebroker.couchbase.model;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;

@Document
public class PasswordMapper
{
    @Id
    private String id;

    @Field
    private String serviceInstanceId;

    @Field
    private String password;

    public PasswordMapper() {
    }

    public PasswordMapper(String id, String serviceInstanceId, String password) {
        this.id = id;
        this.serviceInstanceId = serviceInstanceId;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceInstanceId() {
        return serviceInstanceId;
    }

    public void setServiceInstanceId(String serviceInstanceId) {
        this.serviceInstanceId = serviceInstanceId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "PasswordMapper{" +
                "id='" + id + '\'' +
                ", serviceInstanceId='" + serviceInstanceId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
