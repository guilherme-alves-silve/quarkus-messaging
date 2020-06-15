package br.com.guilhermealvessilve.kafka.primitive.producer;

import io.reactivex.Flowable;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static br.com.guilhermealvessilve.kafka.utils.KafkaUtils.KAFKA_PRICE_PRODUCER;

@ApplicationScoped
public class PriceProducer {

    private static final long PERIOD = 1L;
    private static final Logger LOGGER = Logger.getLogger(PriceProducer.class);

    @Outgoing(KAFKA_PRICE_PRODUCER)
    public Flowable<Double> producePrice() {
        return Flowable.interval(PERIOD, TimeUnit.SECONDS)
                .map(tick -> {
                    double price = new Random().nextInt(4_000);
                    LOGGER.info("[Kafka] Generating price: " + price);
                    return price;
                });
    }
}
