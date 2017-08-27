package pas.au.pivotal.pcf.servicebroker.couchbase;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pas.au.pivotal.pcf.servicebroker.couchbase.service.CouchbaseAdminService;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CouchbaseServiceBrokerApplicationTests {

    private final String testBucketName = "testbucket";

    @Autowired
    private CouchbaseAdminService couchbaseAdminService;

    @Test
    public void aa_createDatabase()
    {
        couchbaseAdminService.createDatabase(testBucketName);
        Assert.assertTrue(couchbaseAdminService.hasBucket(testBucketName));
    }

    @Test
    public void ab_hasBucket()
    {
        Assert.assertTrue(couchbaseAdminService.hasBucket(testBucketName));
    }

    @Test
    public void ac_deleteDatabase()
    {
        couchbaseAdminService.deleteDatabase(testBucketName);
        Assert.assertFalse(couchbaseAdminService.hasBucket(testBucketName));
    }

}
