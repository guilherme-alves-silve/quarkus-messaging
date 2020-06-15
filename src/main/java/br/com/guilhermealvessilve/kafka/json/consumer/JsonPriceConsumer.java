package br.com.guilhermealvessilve.kafka.json.consumer;

import br.com.guilhermealvessilve.kafka.json.data.Price;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.reactivestreams.Publisher;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import static br.com.guilhermealvessilve.kafka.utils.KafkaUtils.KAFKA_JSON_PRICE_CONSUMER;

@ApplicationScoped
public class JsonPriceConsumer {

    private final Publisher<Price> publisher;

    @Inject
    public JsonPriceConsumer(@Channel(KAFKA_JSON_PRICE_CONSUMER) final Publisher<Price> publisher) {
        this.publisher = publisher;
    }

    public Publisher<Price> consumePrice() {
        return publisher;
    }
}
