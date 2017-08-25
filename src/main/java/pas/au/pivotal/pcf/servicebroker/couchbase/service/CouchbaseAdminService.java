package pas.au.pivotal.pcf.servicebroker.couchbase.service;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.bucket.BucketType;
import com.couchbase.client.java.cluster.BucketSettings;
import com.couchbase.client.java.cluster.ClusterManager;
import com.couchbase.client.java.cluster.DefaultBucketSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class CouchbaseAdminService
{
    private Logger logger = LoggerFactory.getLogger(CouchbaseAdminService.class);

    private CouchbaseCluster cluster;
    private ClusterManager clusterManager;

    private Bucket bucket;

    @Value("${couchbase.host:localhost}")
    private String host;

    @Value("${couchbase.bucketName:default}")
    private String bucketName;

    @Value("${couchbase.bucketPassword:}")
    private String bucketPassword;

    @Value("${couchbase.clusterAdminUser:admin}")
    private String clusterAdminUser;

    @Value("${couchbase.clusterAdminPassword:admin}")
    private String clusterAdminPassword;

    @PostConstruct
    public void init()
    {
        cluster = CouchbaseCluster.create(host);
        clusterManager = cluster.clusterManager(clusterAdminUser, clusterAdminPassword);
        logger.info("Connected to Couchbase cluster for management with user : " + clusterAdminUser);

        //bucket = cluster.openBucket(bucketName);
        //logger.info("Connected to Couchbase using default bucket....");

    }

    public boolean hasBucket (String bucketName)
    {
        return clusterManager.hasBucket(bucketName);
    }

    public void createDatabase (String bucketName, String password)
    {
        BucketSettings bucketSettings = new DefaultBucketSettings.Builder()
                .type(BucketType.COUCHBASE)
                .name(bucketName) // name of bucket to create
                .password(password) // bucket password
                .quota(120) // 120 megabytes
                .replicas(1)
                .indexReplicas(true)
                .enableFlush(true)
                .build();

        clusterManager.insertBucket(bucketSettings);

    }

    public void deleteDatabase (String bucketName)
    {
        clusterManager.removeBucket(bucketName);
    }

    public void emptyDatabase(String bucketName)
    {
        Bucket bucket = cluster.openBucket(bucketName);
        bucket.bucketManager().flush();
    }

}
