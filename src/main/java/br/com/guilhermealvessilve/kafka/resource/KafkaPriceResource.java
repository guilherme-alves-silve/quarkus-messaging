package br.com.guilhermealvessilve.kafka.resource;

import br.com.guilhermealvessilve.kafka.json.consumer.JsonPriceConsumer;
import br.com.guilhermealvessilve.kafka.json.data.Price;
import br.com.guilhermealvessilve.kafka.primitive.consumer.PriceConsumer;
import org.jboss.resteasy.annotations.SseElementType;
import org.reactivestreams.Publisher;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/kafka/price")
public class KafkaPriceResource {

    private final PriceConsumer priceConsumer;

    private final JsonPriceConsumer jsonPriceConsumer;

    @Inject
    public KafkaPriceResource(
            final PriceConsumer priceConsumer,
            final JsonPriceConsumer jsonPriceConsumer
    ) {
        this.priceConsumer = priceConsumer;
        this.jsonPriceConsumer = jsonPriceConsumer;
    }

    @GET
    @Path("/stream")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @SseElementType(MediaType.TEXT_PLAIN)
    public Publisher<Double> getPrimitivePrices() {
        return priceConsumer.consumePrice();
    }

    @GET
    @Path("/json/stream")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @SseElementType(MediaType.TEXT_PLAIN)
    public Publisher<Price> getJsonPrices() {
        return jsonPriceConsumer.consumePrice();
    }
}