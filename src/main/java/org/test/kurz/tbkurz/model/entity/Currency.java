package org.test.kurz.tbkurz.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "currency")
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "currency_code")
    private String currencyCode;
    @Column(name = "exchange_rate")
    private Double exchangeRate;

}
