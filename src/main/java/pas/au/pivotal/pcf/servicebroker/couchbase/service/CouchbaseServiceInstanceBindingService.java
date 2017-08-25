package pas.au.pivotal.pcf.servicebroker.couchbase.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.servicebroker.model.CreateServiceInstanceBindingRequest;
import org.springframework.cloud.servicebroker.model.CreateServiceInstanceBindingResponse;
import org.springframework.cloud.servicebroker.model.DeleteServiceInstanceBindingRequest;
import org.springframework.cloud.servicebroker.service.ServiceInstanceBindingService;
import org.springframework.stereotype.Service;
import pas.au.pivotal.pcf.servicebroker.couchbase.repository.CouchbaseServiceInstanceBindingRepository;

@Service
public class CouchbaseServiceInstanceBindingService implements ServiceInstanceBindingService {
    private Logger logger = LoggerFactory.getLogger(CouchbaseServiceInstanceBindingService.class);

    private CouchbaseAdminService couchbaseAdminService;
    private CouchbaseServiceInstanceBindingRepository couchbaseServiceInstanceBindingRepository;

    @Autowired
    public CouchbaseServiceInstanceBindingService(CouchbaseAdminService couchbaseAdminService, CouchbaseServiceInstanceBindingRepository couchbaseServiceInstanceBindingRepository) {
        this.couchbaseAdminService = couchbaseAdminService;
        this.couchbaseServiceInstanceBindingRepository = couchbaseServiceInstanceBindingRepository;
    }

    @Override
    public CreateServiceInstanceBindingResponse createServiceInstanceBinding(CreateServiceInstanceBindingRequest createServiceInstanceBindingRequest) {
        logger.info("Create service instance binding requested ...");

        // create URI and bucketPassword entries
        // https://developer.couchbase.com/documentation/server/4.1/developer-guide/connecting.html
        // couchbase://10.4.3.41,10.4.3.42,10.4.3.43/default
        // couchbase://10.4.3.41/bucketName

        return null;
    }

    @Override
    public void deleteServiceInstanceBinding(DeleteServiceInstanceBindingRequest deleteServiceInstanceBindingRequest) {
        logger.info("Delete  service instance binding requested ...");
    }
}
