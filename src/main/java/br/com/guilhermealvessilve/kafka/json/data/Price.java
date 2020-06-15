package br.com.guilhermealvessilve.kafka.json.data;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.StringJoiner;

@Data
@NoArgsConstructor
@Entity(name = "price")
@EqualsAndHashCode(callSuper = true)
public class Price extends PanacheEntityBase {

    @Id
    @JsonbTransient
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double value;

    private String money;

    public Price(final double value, final String money) {
        this.value = value;
        this.money = money;
    }

    @Override
    public String toString() {
        final var joiner = new StringJoiner(", ", "{", "}");
        if (id != null) {
            joiner.add("\"id\": \"" + id + "\"");
        }

        return joiner
                .add("\"value\": \"" + value + "\"")
                .add("\"money\": \"" + money + "\"")
                .toString();
    }
}
