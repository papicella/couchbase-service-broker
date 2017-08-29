package pas.au.pivotal.pcf.servicebroker.couchbase.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.servicebroker.exception.ServiceInstanceBindingDoesNotExistException;
import org.springframework.cloud.servicebroker.exception.ServiceInstanceBindingExistsException;
import org.springframework.cloud.servicebroker.model.CreateServiceInstanceAppBindingResponse;
import org.springframework.cloud.servicebroker.model.CreateServiceInstanceBindingRequest;
import org.springframework.cloud.servicebroker.model.CreateServiceInstanceBindingResponse;
import org.springframework.cloud.servicebroker.model.DeleteServiceInstanceBindingRequest;
import org.springframework.cloud.servicebroker.service.ServiceInstanceBindingService;
import org.springframework.stereotype.Service;
import pas.au.pivotal.pcf.servicebroker.couchbase.model.PasswordMapper;
import pas.au.pivotal.pcf.servicebroker.couchbase.model.ServiceInstanceBinding;
import pas.au.pivotal.pcf.servicebroker.couchbase.repository.CouchbasePasswordMapperRepository;
import pas.au.pivotal.pcf.servicebroker.couchbase.repository.CouchbaseServiceInstanceBindingRepository;

import java.util.HashMap;
import java.util.Map;

@Service
public class CouchbaseServiceInstanceBindingService implements ServiceInstanceBindingService {
    private Logger logger = LoggerFactory.getLogger(CouchbaseServiceInstanceBindingService.class);

    @Value("${couchbase.host:localhost}")
    private String host;

    private CouchbaseAdminService couchbaseAdminService;
    private CouchbaseServiceInstanceBindingRepository couchbaseServiceInstanceBindingRepository;
    private CouchbasePasswordMapperRepository couchbasePasswordMapperRepository;

    @Autowired
    public CouchbaseServiceInstanceBindingService
            (CouchbaseAdminService couchbaseAdminService,
             CouchbaseServiceInstanceBindingRepository couchbaseServiceInstanceBindingRepository,
             CouchbasePasswordMapperRepository couchbasePasswordMapperRepository) {
        this.couchbaseAdminService = couchbaseAdminService;
        this.couchbaseServiceInstanceBindingRepository = couchbaseServiceInstanceBindingRepository;
        this.couchbasePasswordMapperRepository = couchbasePasswordMapperRepository;
    }

    @Override
    public CreateServiceInstanceBindingResponse createServiceInstanceBinding(CreateServiceInstanceBindingRequest createServiceInstanceBindingRequest) {
        logger.info("Create service instance binding requested ...");

        String bindingId = createServiceInstanceBindingRequest.getBindingId();
        String serviceInstanceId = createServiceInstanceBindingRequest.getServiceInstanceId();

        ServiceInstanceBinding binding = couchbaseServiceInstanceBindingRepository.findOne(bindingId);
        if (binding != null) {
            throw new ServiceInstanceBindingExistsException(serviceInstanceId, bindingId);
        }

        String database = serviceInstanceId;

        PasswordMapper passwordMapper = couchbasePasswordMapperRepository.findOne("CF-" + serviceInstanceId);

        Map<String, Object> credentials = new HashMap<>();
        credentials.put("uri", "couchbase://" + host + "/" + database);
        credentials.put("bucketName", database);
        credentials.put("password", passwordMapper.getPassword());

        binding = new ServiceInstanceBinding
                (bindingId,
                 serviceInstanceId,
                 credentials,
                 null,
                 createServiceInstanceBindingRequest.getBoundAppGuid());
        couchbaseServiceInstanceBindingRepository.save(binding);

        return new CreateServiceInstanceAppBindingResponse().withCredentials(credentials);
    }

    @Override
    public void deleteServiceInstanceBinding(DeleteServiceInstanceBindingRequest deleteServiceInstanceBindingRequest) {
        logger.info("Delete  service instance binding requested ...");

        String bindingId = deleteServiceInstanceBindingRequest.getBindingId();
        ServiceInstanceBinding binding = getServiceInstanceBinding(bindingId);

        if (binding == null) {
            throw new ServiceInstanceBindingDoesNotExistException(bindingId);
        }

        couchbaseServiceInstanceBindingRepository.delete(bindingId);

    }

    protected ServiceInstanceBinding getServiceInstanceBinding(String id) {
        return couchbaseServiceInstanceBindingRepository.findOne(id);
    }
}
