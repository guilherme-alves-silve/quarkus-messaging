package br.com.guilhermealvessilve.amqp.converter;

import io.smallrye.reactive.messaging.annotations.Broadcast;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

import static br.com.guilhermealvessilve.amqp.utils.AMQPUtils.AMQP_PRICE_CONSUMER;
import static br.com.guilhermealvessilve.amqp.utils.AMQPUtils.AMQP_PRICE_PRODUCER;

@ApplicationScoped
public class AMQPPriceConverter {

    private static final double RATE_CONVERSION = 0.5d;
    private static final Logger LOGGER = Logger.getLogger(AMQPPriceConverter.class);

    @Broadcast
    @Incoming(AMQP_PRICE_PRODUCER)
    @Outgoing(AMQP_PRICE_CONSUMER)
    public double convert(double price) {
        double convertedPrice = price * RATE_CONVERSION;
        LOGGER.info("[AMQP] Converted price: " + convertedPrice);
        return convertedPrice;
    }
}
