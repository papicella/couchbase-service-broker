package pas.au.pivotal.pcf.servicebroker.couchbase.service;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.servicebroker.exception.ServiceBrokerException;
import org.springframework.cloud.servicebroker.exception.ServiceInstanceDoesNotExistException;
import org.springframework.cloud.servicebroker.exception.ServiceInstanceExistsException;
import org.springframework.cloud.servicebroker.model.*;
import org.springframework.cloud.servicebroker.service.ServiceInstanceService;
import org.springframework.stereotype.Service;
import pas.au.pivotal.pcf.servicebroker.couchbase.model.ServiceInstance;
import pas.au.pivotal.pcf.servicebroker.couchbase.repository.CouchbaseServiceInstanceRepository;

@Service
public class CouchbaseServiceInstanceService implements ServiceInstanceService {

    private Logger logger = LoggerFactory.getLogger(CouchbaseServiceInstanceService.class);

    private CouchbaseAdminService couchbaseAdminService;
    private CouchbaseServiceInstanceRepository couchbaseServiceInstanceRepository;

    @Autowired
    public CouchbaseServiceInstanceService(CouchbaseAdminService couchbaseAdminService, CouchbaseServiceInstanceRepository couchbaseServiceInstanceRepository) {
        this.couchbaseAdminService = couchbaseAdminService;
        this.couchbaseServiceInstanceRepository = couchbaseServiceInstanceRepository;
    }

    @Override
    public CreateServiceInstanceResponse createServiceInstance(CreateServiceInstanceRequest createServiceInstanceRequest) {
        logger.info("Create service instance requested ...");

        ServiceInstance instance = couchbaseServiceInstanceRepository.findOne(createServiceInstanceRequest.getServiceInstanceId());

        if (instance != null) {
            throw new ServiceInstanceExistsException
                    (createServiceInstanceRequest.getServiceInstanceId(), createServiceInstanceRequest.getServiceDefinitionId());
        }

        instance = new ServiceInstance(createServiceInstanceRequest);

        String password = RandomStringUtils.randomAlphanumeric(25);

        try
        {
            couchbaseAdminService.createDatabase(createServiceInstanceRequest.getServiceInstanceId(), password);
        }
        catch (Exception ex)
        {
            throw new ServiceBrokerException("Failed to create new DB instance: " + createServiceInstanceRequest.getServiceInstanceId());
        }

        couchbaseServiceInstanceRepository.save(instance);

        return new CreateServiceInstanceResponse();
    }

    @Override
    public GetLastServiceOperationResponse getLastOperation(GetLastServiceOperationRequest getLastServiceOperationRequest) {
        return new GetLastServiceOperationResponse().withOperationState(OperationState.SUCCEEDED);
    }

    @Override
    public DeleteServiceInstanceResponse deleteServiceInstance(DeleteServiceInstanceRequest deleteServiceInstanceRequest) {
        logger.info("Delete  service instance requested ...");
        String instanceId = deleteServiceInstanceRequest.getServiceInstanceId();
        ServiceInstance instance = couchbaseServiceInstanceRepository.findOne(instanceId);
        if (instance == null) {
            throw new ServiceInstanceDoesNotExistException(instanceId);
        }

        couchbaseAdminService.deleteDatabase(instanceId);
        couchbaseServiceInstanceRepository.delete(instanceId);

        return new DeleteServiceInstanceResponse();

    }

    @Override
    public UpdateServiceInstanceResponse updateServiceInstance(UpdateServiceInstanceRequest updateServiceInstanceRequest) {
        logger.info("Update service instance requested ...");
        String instanceId = updateServiceInstanceRequest.getServiceInstanceId();
        ServiceInstance instance = couchbaseServiceInstanceRepository.findOne(instanceId);
        if (instance == null) {
            throw new ServiceInstanceDoesNotExistException(instanceId);
        }

        couchbaseServiceInstanceRepository.delete(instanceId);
        ServiceInstance updatedInstance = new ServiceInstance(updateServiceInstanceRequest);
        couchbaseServiceInstanceRepository.save(updatedInstance);

        return new UpdateServiceInstanceResponse();
    }
}
