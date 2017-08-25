package pas.au.pivotal.pcf.servicebroker.couchbase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CouchbaseServiceBrokerApplication {

	private Logger logger = LoggerFactory.getLogger(CouchbaseServiceBrokerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CouchbaseServiceBrokerApplication.class, args);
	}

}
