package br.com.guilhermealvessilve.jms.resource;

import br.com.guilhermealvessilve.jms.consumer.JMSPriceConsumer;
import org.jboss.resteasy.annotations.SseElementType;
import org.reactivestreams.Publisher;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/jms/price")
public class JMSPriceResource {

    private final JMSPriceConsumer priceConsumer;

    @Inject
    public JMSPriceResource(final JMSPriceConsumer priceConsumer) {
        this.priceConsumer = priceConsumer;
    }

    @GET
    @Path("/stream")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @SseElementType(MediaType.TEXT_PLAIN)
    public Publisher<Double> getPrices() {
        return priceConsumer.consumePrice();
    }
}