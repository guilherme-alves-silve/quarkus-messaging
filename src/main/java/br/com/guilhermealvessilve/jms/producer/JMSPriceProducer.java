package br.com.guilhermealvessilve.jms.producer;

import br.com.guilhermealvessilve.jms.thread.JMSPriceThreadFactory;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Session;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static br.com.guilhermealvessilve.jms.utils.JMSUtils.JMS_QUEUE;

@ApplicationScoped
public class JMSPriceProducer implements Runnable {

    private static final long INITIAL_DELAY = 0L;
    private static final long DELAY = 1L;
    private static final Logger LOGGER = Logger.getLogger(JMSPriceProducer.class);

    private final ConnectionFactory factory;
    private final ScheduledExecutorService executorService;

    @Inject
    public JMSPriceProducer(
            final ConnectionFactory factory,
            final JMSPriceThreadFactory threadFactory
    ) {
        this.factory = factory;
        this.executorService = Executors.newSingleThreadScheduledExecutor(threadFactory);
    }

    public void onStart(@Observes StartupEvent event) {
        LOGGER.info("[JMS] Starting producer...");
        executorService.scheduleWithFixedDelay(this, INITIAL_DELAY, DELAY, TimeUnit.SECONDS);
    }

    public void onStop(@Observes ShutdownEvent event) {
        LOGGER.info("[JMS] Stopping producer...");
        executorService.shutdown();
    }

    @Override
    public void run() {
        try (final JMSContext context = factory.createContext(Session.AUTO_ACKNOWLEDGE)) {
            final var random = new Random();
            final var price = Double.toString(random.nextInt(5000));
            LOGGER.info("[JMS] Generating price: " + price);
            final var producer = context.createProducer();
            final var queue = context.createQueue(JMS_QUEUE);
            producer.send(queue, price);
        } catch (final Exception ex) {
            LOGGER.error("[JMS] Occurred an error in producer: " + ex.getMessage(), ex);
        }
    }
}
