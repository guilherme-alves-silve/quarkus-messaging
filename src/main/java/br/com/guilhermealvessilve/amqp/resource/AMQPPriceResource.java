package br.com.guilhermealvessilve.amqp.resource;

import br.com.guilhermealvessilve.amqp.consumer.AMQPPriceConsumer;
import org.jboss.resteasy.annotations.SseElementType;
import org.reactivestreams.Publisher;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/amqp/price")
public class AMQPPriceResource {

    private final AMQPPriceConsumer priceConsumer;

    @Inject
    public AMQPPriceResource(final AMQPPriceConsumer priceConsumer) {
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