package br.com.guilhermealvessilve.kafka.json.store;

import br.com.guilhermealvessilve.kafka.json.data.Price;
import io.quarkus.vertx.ConsumeEvent;
import io.vertx.mutiny.core.eventbus.Message;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class JsonPriceStore {

    public static final int EVENT_BUS_FAILED_CODE = 500;
    public static final String EVENT_BUS_ADDRESS = "event-bus-json-price-out-saver";

    private static final Logger LOGGER = Logger.getLogger(JsonPriceStore.class);

    @Transactional
    @ConsumeEvent(value = EVENT_BUS_ADDRESS, blocking = true)
    public void store(final Message<Price> message) {
        final var price = message.body();
        LOGGER.info("[Kafka] Persisting json price: " + price);
        try {
            price.persist();
            message.replyAndForget(price);
            LOGGER.info("[Kafka] Persisted json price: " + price);
        } catch (final Exception ex) {
            message.fail(EVENT_BUS_FAILED_CODE, "[Kafka] Could not save the received price: " + price + ", error: " + ex.getMessage());
        }
    }
}
