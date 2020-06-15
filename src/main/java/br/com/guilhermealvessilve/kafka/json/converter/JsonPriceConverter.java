package br.com.guilhermealvessilve.kafka.json.converter;

import br.com.guilhermealvessilve.kafka.json.data.Price;
import br.com.guilhermealvessilve.kafka.json.eventbus.JsonPriceStoreEventBus;
import io.smallrye.reactive.messaging.annotations.Broadcast;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import static br.com.guilhermealvessilve.kafka.utils.KafkaUtils.KAFKA_JSON_PRICE_CONSUMER;
import static br.com.guilhermealvessilve.kafka.utils.KafkaUtils.KAFKA_JSON_PRICE_PRODUCER;

@ApplicationScoped
public class JsonPriceConverter {

    private static final double RATE_CONVERSION = 1.5d;
    private static final String USD_MONEY = "usd";
    private static final Logger LOGGER = Logger.getLogger(JsonPriceConverter.class);

    private final JsonPriceStoreEventBus jsonPriceStoreEventBus;

    @Inject
    public JsonPriceConverter(final JsonPriceStoreEventBus jsonPriceStoreEventBus) {
        this.jsonPriceStoreEventBus = jsonPriceStoreEventBus;
    }

    @Broadcast
    @Incoming(KAFKA_JSON_PRICE_PRODUCER)
    @Outgoing(KAFKA_JSON_PRICE_CONSUMER)
    public Price convert(final Price price) {
        double convertedValue = price.getValue() * RATE_CONVERSION;
        final var convertedPrice = new Price(convertedValue, USD_MONEY);
        LOGGER.info("[Kafka] Converted json price: " + convertedPrice);

        jsonPriceStoreEventBus.sendAsync(convertedPrice, (sentPrice, throwable) -> {
            if (throwable != null) {
                LOGGER.error("[Kafka] Could not save the price " + sentPrice + ", error: " + throwable.getMessage(), throwable);
            } else {
                LOGGER.info("[Kafka] Price " + sentPrice + " was sent and saved with success!");
            }
        });

        return convertedPrice;
    }
}
