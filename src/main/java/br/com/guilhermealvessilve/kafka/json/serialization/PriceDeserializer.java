package br.com.guilhermealvessilve.kafka.json.serialization;

import br.com.guilhermealvessilve.kafka.json.data.Price;
import io.quarkus.kafka.client.serialization.JsonbDeserializer;

public class PriceDeserializer extends JsonbDeserializer<Price> {

    public PriceDeserializer() {
        super(Price.class);
    }
}
