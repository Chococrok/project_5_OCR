package com.dummy.myerp.consumer.it;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Demonstrates a basic Message Endpoint that simply prepends a greeting ("Hello
 * ") to an inbound String payload from a Message. This is a very low-level
 * example, using Message Channels directly for both input and output. Notice
 * that the output channel has a queue sub-element. It is therefore a
 * PollableChannel and its consumers must invoke receive() as demonstrated
 * below.
 * <p>
 * View the configuration of the channels and the endpoint (a
 * &lt;service-activator/&gt; element) in 'helloWorldDemo.xml' within this same
 * package.
 *
 * @author Mark Fisher
 * @author Oleg Zhurakousky
 * @author Gary Russell
 */
public class ConsumerIntegrationTest {

	private static Log logger = LogFactory.getLog(ConsumerIntegrationTest.class);

	public static void main(String[] args) {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext();
		logger.info("==> ConsumerIntegrationTest: starting integration tests" );
		context.close();
	}

}
