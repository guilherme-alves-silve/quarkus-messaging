package br.com.guilhermealvessilve.amqp.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AMQPUtils {

    public static final String AMQP_PRICE_PRODUCER = "amqp-price-out";
    //The consumer can't have the same name as the incoming amqp-price-in
    public static final String AMQP_PRICE_CONSUMER = "amqp-price-consumer";
}
