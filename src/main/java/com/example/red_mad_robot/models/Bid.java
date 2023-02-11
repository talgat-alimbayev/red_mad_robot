package com.example.red_mad_robot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "поле пользователь не может быть пустым")
    private Ad ad;

    @Positive(message = "ставка не может быть отрицательной")
    @NotNull(message = "поле ставки не может быть пустым")
    private BigDecimal bid;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    @NotNull(message = "поле пользователь не может быть пустым")
    private User user;

    private boolean highest = false;
}
