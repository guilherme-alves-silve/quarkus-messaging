package br.com.guilhermealvessilve.kafka.primitive.consumer;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.reactivestreams.Publisher;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import static br.com.guilhermealvessilve.kafka.utils.KafkaUtils.KAFKA_PRICE_CONSUMER;

@ApplicationScoped
public class PriceConsumer {

    private final Publisher<Double> publisher;

    @Inject
    public PriceConsumer(@Channel(KAFKA_PRICE_CONSUMER) final Publisher<Double> publisher) {
        this.publisher = publisher;
    }

    public Publisher<Double> consumePrice() {
        return publisher;
    }
}