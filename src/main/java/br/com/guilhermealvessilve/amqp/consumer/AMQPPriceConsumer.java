package br.com.guilhermealvessilve.amqp.consumer;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.reactivestreams.Publisher;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import static br.com.guilhermealvessilve.amqp.utils.AMQPUtils.AMQP_PRICE_CONSUMER;

@ApplicationScoped
public class AMQPPriceConsumer {

    private final Publisher<Double> publisher;

    @Inject
    public AMQPPriceConsumer(@Channel(AMQP_PRICE_CONSUMER) final Publisher<Double> publisher) {
        this.publisher = publisher;
    }

    public Publisher<Double> consumePrice() {
        return publisher;
    }
}