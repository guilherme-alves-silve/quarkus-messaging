package br.com.guilhermealvessilve.kafka.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class KafkaUtils {

    public static final String KAFKA_PRICE_PRODUCER = "price-out";
    public static final String KAFKA_PRICE_CONSUMER = "price-in";

    public static final String KAFKA_JSON_PRICE_PRODUCER = "json-price-out";
    public static final String KAFKA_JSON_PRICE_CONSUMER = "json-price-in";
}
