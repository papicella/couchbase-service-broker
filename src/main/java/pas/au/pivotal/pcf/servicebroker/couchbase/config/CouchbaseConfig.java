package pas.au.pivotal.pcf.servicebroker.couchbase.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;
import org.springframework.data.couchbase.repository.support.IndexManager;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableCouchbaseRepositories (basePackages={"pas.au.pivotal.pcf.servicebroker.couchbase"})
public class CouchbaseConfig extends AbstractCouchbaseConfiguration {

    @Value("${couchbase.host:localhost}")
    private String host;

    @Value("${couchbase.bucketName:default}")
    private String bucketName;

    @Value("${couchbase.bucketPassword:}")
    private String bucketPassword;

    @Override
    protected List<String> getBootstrapHosts() {
        return Arrays.asList(host);
    }

    @Override
    protected String getBucketName() {
        return bucketName;
    }

    @Override
    protected String getBucketPassword() {
        return bucketPassword;
    }

    //this is for dev so it is ok to auto-create indexes
    @Override
    public IndexManager indexManager() {
        return new IndexManager(false, false, false);
    }

}
