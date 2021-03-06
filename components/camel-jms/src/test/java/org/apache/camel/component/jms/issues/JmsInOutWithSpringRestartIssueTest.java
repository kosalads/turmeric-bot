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
package org.apache.camel.component.jms.issues;

import org.apache.camel.ProducerTemplate;
import org.apache.camel.test.junit4.CamelSpringTestSupport;
import org.junit.Test;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @version $Revision$
 */
public class JmsInOutWithSpringRestartIssueTest extends CamelSpringTestSupport {

    @Override
    protected AbstractXmlApplicationContext createApplicationContext() {
        return new ClassPathXmlApplicationContext("org/apache/camel/component/jms/issues/JmsInOutWithSpringRestartIssueTest.xml");
    }

    @Override
    protected int getExpectedRouteCount() {
        return 0;
    }

    @Test
    public void testRestartSpringIssue() throws Exception {
        context.startRoute("foo");

        ProducerTemplate producer = context.createProducerTemplate();
        producer.start();

        Object out = producer.requestBody("activemq:queue:foo", "Foo");
        assertEquals("Bye Foo", out);

        // on purpose forget to stop the producer and it should still work

        context.stopRoute("foo");
        context.startRoute("foo");

        out = producer.requestBody("activemq:queue:foo", "Bar");
        assertEquals("Bye Bar", out);
    }

}
