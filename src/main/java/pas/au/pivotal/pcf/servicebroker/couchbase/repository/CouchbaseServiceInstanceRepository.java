package pas.au.pivotal.pcf.servicebroker.couchbase.repository;

import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.repository.CrudRepository;
import pas.au.pivotal.pcf.servicebroker.couchbase.model.ServiceInstance;

//@ViewIndexed(designDoc = "serviceInstance")
public interface CouchbaseServiceInstanceRepository extends CrudRepository<ServiceInstance, String> {
}
