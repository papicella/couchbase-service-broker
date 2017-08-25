package pas.au.pivotal.pcf.servicebroker.couchbase.repository;

import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.repository.CrudRepository;
import pas.au.pivotal.pcf.servicebroker.couchbase.model.ServiceInstanceBinding;

@ViewIndexed(designDoc = "serviceInstanceBinding")
public interface CouchbaseServiceInstanceBindingRepository extends CrudRepository <ServiceInstanceBinding, String> {

}
