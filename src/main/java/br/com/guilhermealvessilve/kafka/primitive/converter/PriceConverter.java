package br.com.guilhermealvessilve.kafka.primitive.converter;

import io.smallrye.reactive.messaging.annotations.Broadcast;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

import static br.com.guilhermealvessilve.kafka.utils.KafkaUtils.KAFKA_PRICE_CONSUMER;
import static br.com.guilhermealvessilve.kafka.utils.KafkaUtils.KAFKA_PRICE_PRODUCER;

@ApplicationScoped
public class PriceConverter {

    private static final double RATE_CONVERSION = 1.5d;
    private static final Logger LOGGER = Logger.getLogger(PriceConverter.class);

    @Broadcast
    @Incoming(KAFKA_PRICE_PRODUCER)
    @Outgoing(KAFKA_PRICE_CONSUMER)
    public double convert(double price) {
        double convertedPrice = price * RATE_CONVERSION;
        LOGGER.info("[Kafka] Converted price: " + convertedPrice);
        return convertedPrice;
    }
}
