package br.com.guilhermealvessilve.jms.consumer;

import org.jboss.logging.Logger;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import javax.enterprise.context.ApplicationScoped;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedDeque;

@ApplicationScoped
class JMSPricePublisher implements Publisher<Double> {

    private static final Logger LOGGER = Logger.getLogger(JMSPricePublisher.class);

    private final ConcurrentLinkedDeque<Subscriber<? super Double>> subscribers;

    JMSPricePublisher() {
        this.subscribers = new ConcurrentLinkedDeque<>();
    }

    @Override
    public void subscribe(Subscriber<? super Double> subscriber) {
        subscribers.add(Objects.requireNonNull(subscriber, "[JMS] The subscriber cannot be null"));
    }

    void publish(String strPrice) {
        if (subscribers.isEmpty()) {
            LOGGER.info("[JMS] There is no one subscribed yet");
            return;
        }

        final var price = Double.valueOf(strPrice);
        subscribers.forEach(subscriber -> subscriber.onNext(price));
    }
}
