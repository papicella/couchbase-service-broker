package pas.au.pivotal.pcf.servicebroker.couchbase.model;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

@Document
public class ServiceInstanceBinding {

    @Id
    private String id;

    @Field
    private String serviceInstanceId;

    @Field
    private Map<String,Object> credentials = new HashMap<>();

    @Field
    private String syslogDrainUrl;

    @Field
    private String appGuid;

    public ServiceInstanceBinding() {
    }

    public ServiceInstanceBinding(String id, String serviceInstanceId, Map<String, Object> credentials, String syslogDrainUrl, String appGuid) {
        this.id = id;
        this.serviceInstanceId = serviceInstanceId;
        this.credentials = credentials;
        this.syslogDrainUrl = syslogDrainUrl;
        this.appGuid = appGuid;
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

    public Map<String, Object> getCredentials() {
        return credentials;
    }

    public void setCredentials(Map<String, Object> credentials) {
        this.credentials = credentials;
    }

    public String getSyslogDrainUrl() {
        return syslogDrainUrl;
    }

    public void setSyslogDrainUrl(String syslogDrainUrl) {
        this.syslogDrainUrl = syslogDrainUrl;
    }

    public String getAppGuid() {
        return appGuid;
    }

    public void setAppGuid(String appGuid) {
        this.appGuid = appGuid;
    }

    @Override
    public String toString() {
        return "ServiceInstanceBinding{" +
                "id='" + id + '\'' +
                ", serviceInstanceId='" + serviceInstanceId + '\'' +
                ", credentials=" + credentials +
                ", syslogDrainUrl='" + syslogDrainUrl + '\'' +
                ", appGuid='" + appGuid + '\'' +
                '}';
    }
}
