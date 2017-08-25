package pas.au.pivotal.pcf.servicebroker.couchbase.config;

import org.springframework.cloud.servicebroker.model.Catalog;
import org.springframework.cloud.servicebroker.model.Plan;
import org.springframework.cloud.servicebroker.model.ServiceDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
public class CatalogConfig
{
    @Bean
    public Catalog catalog() {
        return new Catalog(Collections.singletonList(
                new ServiceDefinition(
                        "couchbase-service-broker",
                        "couchbase",
                        "A simple Couchbase service broker implementation",
                        true,
                        false,
                        Collections.singletonList(
                                new Plan("couchbase-plan",
                                        "default",
                                        "This is a default Couchbase plan.  All services are created equally.",
                                        getPlanMetadata())),
                        Arrays.asList("couchbase", "document"),
                        getServiceDefinitionMetadata(),
                        null,
                        null)));
    }

    /* Used by Pivotal CF console */

    private Map<String, Object> getServiceDefinitionMetadata() {
        Map<String, Object> sdMetadata = new HashMap<>();
        sdMetadata.put("displayName", "Couchbase");
        sdMetadata.put("imageUrl", "https://upload.wikimedia.org/wikipedia/commons/6/67/Couchbase%2C_Inc._official_logo.png");
        sdMetadata.put("longDescription", "Couchbase Service");
        sdMetadata.put("providerDisplayName", "Pivotal");
        sdMetadata.put("documentationUrl", "https://www.couchbase.com/");
        sdMetadata.put("supportUrl", "https://www.couchbase.com/");
        return sdMetadata;
    }

    private Map<String,Object> getPlanMetadata() {
        Map<String,Object> planMetadata = new HashMap<>();
        planMetadata.put("costs", getCosts());
        planMetadata.put("bullets", getBullets());
        return planMetadata;
    }

    private List<Map<String,Object>> getCosts() {
        Map<String,Object> costsMap = new HashMap<>();

        Map<String,Object> amount = new HashMap<>();
        amount.put("usd", 0.0);

        costsMap.put("amount", amount);
        costsMap.put("unit", "MONTHLY");

        return Collections.singletonList(costsMap);
    }

    private List<String> getBullets() {
        return Arrays.asList("Shared Couchbase server",
                "120 MB Storage (Enforced)",
                "40 concurrent connections (not enforced)");
    }
}
