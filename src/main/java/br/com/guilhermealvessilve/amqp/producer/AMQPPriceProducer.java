package br.com.guilhermealvessilve.amqp.producer;

import io.reactivex.Flowable;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static br.com.guilhermealvessilve.amqp.utils.AMQPUtils.AMQP_PRICE_PRODUCER;

@ApplicationScoped
public class AMQPPriceProducer {

    private static final long PERIOD = 1L;
    private static final Logger LOGGER = Logger.getLogger(AMQPPriceProducer.class);

    @Outgoing(AMQP_PRICE_PRODUCER)
    public Flowable<Double> producePrice() {
        return Flowable.interval(PERIOD, TimeUnit.SECONDS)
                .map(tick -> {
                    double price = new Random().nextInt(4_000);
                    LOGGER.info("[AMQP] Generating price: " + price);
                    return price;
                });
    }
}
