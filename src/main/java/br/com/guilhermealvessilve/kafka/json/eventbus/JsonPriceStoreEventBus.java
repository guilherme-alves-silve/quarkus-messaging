package br.com.guilhermealvessilve.kafka.json.eventbus;

import br.com.guilhermealvessilve.kafka.json.data.Price;
import br.com.guilhermealvessilve.kafka.json.store.JsonPriceStore;
import io.vertx.mutiny.core.eventbus.EventBus;
import io.vertx.mutiny.core.eventbus.Message;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.function.BiConsumer;

@ApplicationScoped
public class JsonPriceStoreEventBus {

    private final EventBus priceJsonSaverEventBus;

    @Inject
    public JsonPriceStoreEventBus(final EventBus priceJsonSaverEventBus) {
        this.priceJsonSaverEventBus = priceJsonSaverEventBus;
    }

    public void sendAsync(final Price price, BiConsumer<Price, Throwable> whenCompleteAction) {
        priceJsonSaverEventBus.<Price>request(JsonPriceStore.EVENT_BUS_ADDRESS, price)
                .onItem()
                .apply(Message::body)
                .subscribeAsCompletionStage()
                .whenComplete(whenCompleteAction);
    }
}
