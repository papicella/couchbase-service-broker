package pas.au.pivotal.pcf.servicebroker.couchbase.service;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.bucket.BucketType;
import com.couchbase.client.java.cluster.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Service
public class CouchbaseAdminService
{
    private Logger logger = LoggerFactory.getLogger(CouchbaseAdminService.class);

    private static CouchbaseCluster cluster;
    private static ClusterManager clusterManager;

    private Bucket bucket;

    @Value("${couchbase.host:localhost}")
    private String host;

    @Value("${couchbase.bucketName:default}")
    private String bucketName;

    @Value("${couchbase.bucketPassword:}")
    private String bucketPassword;

    @Value("${couchbase.clusterAdminUser:Administrator}")
    private String clusterAdminUser;

    @Value("${couchbase.clusterAdminPassword:welcome1}")
    private String clusterAdminPassword;

    @PostConstruct
    public void init()
    {
        cluster = CouchbaseCluster.create(host);

        //cluster.authenticate(clusterAdminUser, clusterAdminPassword);
        //clusterManager = cluster.clusterManager();

        clusterManager = cluster.clusterManager(clusterAdminUser, clusterAdminPassword);

        logger.info("Connected to Couchbase cluster for management with user : " + clusterAdminUser);

        bucket = cluster.openBucket(bucketName, bucketPassword);

        logger.info("Connected to Couchbase using default bucket....");

    }

    public ClusterManager getClusterManager() {
        return clusterManager;
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
                .password(password)
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

    public void addUserToDatabase(String bucketName, String username, String password)
    {

        UserSettings userSettings = UserSettings.build()
                .name(username)
                .password(password)
                .roles(Arrays.asList(
                        // Roles required for the reading of data from
                        // the bucket.
                        new UserRole("bucket_admin", bucketName),
                        new UserRole("data_reader", bucketName),
                        new UserRole("query_select", bucketName),
                        // Roles required for the writing of data into
                        // the bucket.
                        new UserRole("data_writer", bucketName),
                        new UserRole("query_insert", bucketName),
                        new UserRole("query_delete", bucketName),
                        new UserRole("query_update", bucketName),
                        // Role required for the creation of indexes
                        // on the bucket.
                        new UserRole("query_manage_index", bucketName)));

        clusterManager.upsertUser(username, userSettings);

    }

    public void removeUser (String username)
    {
        clusterManager.removeUser(username);
    }

    public boolean createPrimaryIndex (String bucketName, String bucketPassword)
    {
        Bucket myBucket = cluster.openBucket(bucketName, bucketPassword);
        myBucket.bucketManager().createN1qlPrimaryIndex(true, false);

        myBucket.close();

        return true;
    }

}
