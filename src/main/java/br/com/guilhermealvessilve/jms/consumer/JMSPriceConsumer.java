package br.com.guilhermealvessilve.jms.consumer;

import br.com.guilhermealvessilve.jms.thread.JMSPriceThreadFactory;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import org.jboss.logging.Logger;
import org.reactivestreams.Publisher;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Session;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static br.com.guilhermealvessilve.jms.utils.JMSUtils.JMS_QUEUE;

@ApplicationScoped
public class JMSPriceConsumer implements Runnable {

    private static final long MAX_WAITING = 1000L;
    private static final Logger LOGGER = Logger.getLogger(JMSPriceConsumer.class);

    private volatile boolean executing = true;

    private final ConnectionFactory factory;
    private final ExecutorService executorService;
    private final JMSPricePublisher pricePublisher;

    @Inject
    public JMSPriceConsumer(
            final ConnectionFactory factory,
            final JMSPricePublisher pricePublisher,
            final JMSPriceThreadFactory threadFactory
    ) {
        this.factory = factory;
        this.pricePublisher = pricePublisher;
        this.executorService = Executors.newSingleThreadExecutor(threadFactory);
    }

    public void onStart(@Observes final StartupEvent event) {
        LOGGER.info("[JMS] Starting consumer...");
        executorService.execute(this);
    }

    public void onStop(@Observes final ShutdownEvent event) {
        LOGGER.info("[JMS] Stopping consumer...");
        executing = false;
        executorService.shutdown();
    }

    public Publisher<Double> consumePrice() {
        return pricePublisher;
    }

    @Override
    public void run() {
        try (final JMSContext context = factory.createContext(Session.AUTO_ACKNOWLEDGE)) {
            final var queue = context.createQueue(JMS_QUEUE);
            final var consumer = context.createConsumer(queue);
            while (executing) {
                final var message = consumer.receive(MAX_WAITING);
                if (message != null) {
                    final var strPrice = message.getBody(String.class);
                    LOGGER.info("[JMS] Publishing price: " + strPrice);
                    pricePublisher.publish(strPrice);
                } else {
                    LOGGER.debug("[JMS] Timeout occurred retrieving the message");
                }
            }
        } catch (final Exception ex) {
            LOGGER.error("[JMS] Occurred an error in consumer: " + ex.getMessage(), ex);
        }
    }

}
