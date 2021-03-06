/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.processor;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.camel.CamelContext;
import org.apache.camel.CamelContextAware;
import org.apache.camel.ContextTestSupport;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.ServiceSupport;
import org.apache.camel.spi.DataFormat;

/**
 * Test that data format receives callback if its a service / CamelContextAware
 *
 * @version $Revision$
 */
public class DataFormatServiceTest extends ContextTestSupport {

    private final MyDataFormat my = new MyDataFormat();

    public void testMarshal() throws Exception {
        assertEquals(true, my.isStarted());
        assertSame(context, my.getCamelContext());

        getMockEndpoint("mock:a").expectedBodiesReceived("Hi Camel");

        template.sendBody("direct:a", "Hello World");

        assertMockEndpointsSatisfied();

        context.stop();

        assertEquals(true, my.isStopped());
        assertSame(context, my.getCamelContext());
    }

    public void testUnmarshal() throws Exception {
        assertEquals(true, my.isStarted());
        assertSame(context, my.getCamelContext());

        getMockEndpoint("mock:b").expectedBodiesReceived("Bye World");

        template.sendBody("direct:b", "Hello World");

        assertMockEndpointsSatisfied();

        context.stop();

        assertEquals(true, my.isStopped());
        assertSame(context, my.getCamelContext());
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:a").marshal(my).to("mock:a");
                from("direct:b").unmarshal(my).to("mock:b");
            }
        };
    }

    private class MyDataFormat extends ServiceSupport implements DataFormat, CamelContextAware {

        private CamelContext camelContext;

        public CamelContext getCamelContext() {
            return camelContext;
        }

        public void setCamelContext(CamelContext camelContext) {
            this.camelContext = camelContext;
        }

        public void marshal(Exchange exchange, Object graph, OutputStream stream) throws Exception {
            stream.write("Hi Camel".getBytes());
        }

        public Object unmarshal(Exchange exchange, InputStream stream) throws Exception {
            return "Bye World";
        }

        @Override
        protected void doStart() throws Exception {
            // noop
        }

        @Override
        protected void doStop() throws Exception {
            // noop
        }
    }
}
