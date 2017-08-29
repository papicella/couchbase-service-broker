package pas.au.pivotal.pcf.servicebroker.couchbase.repository;

import org.springframework.data.repository.CrudRepository;
import pas.au.pivotal.pcf.servicebroker.couchbase.model.PasswordMapper;

public interface CouchbasePasswordMapperRepository extends CrudRepository<PasswordMapper, String>{
}
