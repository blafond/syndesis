package io.syndesis.connector.odata.springboot;

import javax.annotation.Generated;

/**
 * Get OData Entity
 * 
 * Generated by camel-package-maven-plugin - do not edit this file!
 */
@Generated("org.apache.camel.maven.connector.SpringBootAutoConfigurationMojo")
public class ODataRetrieveEntityConnectorConfigurationCommon {

    /**
     * URL of the OData Service
     */
    private String serviceUri = "http://services.odata.org/TripPinRESTierService(SessionId)";
    /**
     * Entity Resource Path, usually Entity Name
     */
    private String resourcePath = "People";

    public String getServiceUri() {
        return serviceUri;
    }

    public void setServiceUri(String serviceUri) {
        this.serviceUri = serviceUri;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }
}