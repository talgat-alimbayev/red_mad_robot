package com.example.red_mad_robot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
@Table(name = "bids")
    public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "ad")
    private Ad ad;

    @Positive(message = "ставка не может быть отрицательной")
    private BigDecimal bid;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    private String highest = "";
}
