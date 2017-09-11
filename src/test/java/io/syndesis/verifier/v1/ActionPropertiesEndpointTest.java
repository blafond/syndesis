/**
 * Copyright (C) 2016 Red Hat, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.syndesis.verifier.v1;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.module.jsonSchema.types.ObjectSchema;

import io.syndesis.verifier.v1.metadata.MetadataAdapter;
import io.syndesis.verifier.v1.metadata.PropertyPair;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ActionPropertiesEndpointTest {

    private static final Map<String, String> PAYLOAD = Collections.singletonMap("this", "is playload");

    private static final Map<String, List<PropertyPair>> PROPERTIES = Collections.singletonMap("property",
        Arrays.asList(new PropertyPair("value1", "First Value"), new PropertyPair("value2", "Second Value")));

    private static final ObjectSchema UNUSED = null;

    private final ActionPropertiesEndpoint endpoint = new ActionPropertiesEndpoint(
        Collections.singletonMap("petstore-adapter", new PetstoreAdapter(PAYLOAD, PROPERTIES, UNUSED, UNUSED))) {
        @Override
        MetadataEndpoint metadataHelper(final String connectorId, final MetadataAdapter<?> adapter) {
            return new MetadataEndpoint(connectorId, adapter) {
                @Override
                protected CamelContext camelContext() {
                    final DefaultCamelContext camelContext = new DefaultCamelContext();
                    camelContext.addComponent("petstore", new PetstoreComponent(PAYLOAD));

                    return camelContext;
                }
            };
        }
    };

    @Test
    public void shouldProvideActionPropertiesBasedOnMetadata() throws Exception {
        final Map<String, List<PropertyPair>> properties = endpoint.properties("petstore", Collections.emptyMap());

        assertThat(properties).isSameAs(PROPERTIES);
    }
}
