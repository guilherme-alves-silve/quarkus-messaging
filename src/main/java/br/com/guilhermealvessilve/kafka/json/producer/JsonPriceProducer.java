package br.com.guilhermealvessilve.kafka.json.producer;

import br.com.guilhermealvessilve.kafka.json.data.Price;
import io.reactivex.Flowable;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static br.com.guilhermealvessilve.kafka.utils.KafkaUtils.KAFKA_JSON_PRICE_PRODUCER;

@ApplicationScoped
public class JsonPriceProducer {

    private static final long PERIOD = 1L;
    private static final Logger LOGGER = Logger.getLogger(JsonPriceProducer.class);
    private static final String[] MONEY_TYPE = { "blr", "test" };

    @Outgoing(KAFKA_JSON_PRICE_PRODUCER)
    public Flowable<Price> producePrice() {
        return Flowable.interval(PERIOD, TimeUnit.SECONDS)
                .map(tick -> {
                    final var random = new Random();
                    final var price = new Price(
                            random.nextInt(4_000),
                            MONEY_TYPE[random.nextInt(MONEY_TYPE.length)]
                    );
                    LOGGER.info("[Kafka] Generating json price: " + price);
                    return price;
                });
    }
}
