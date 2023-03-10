package com.example.red_mad_robot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
@Table(name = "ads")
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "нужно обязательно добавить описание")
    private String description;

    @Positive(message = "стартовая цена должна быть больше нуля")
    @NotNull(message = "стартовая цена не может быть пустой")
    private BigDecimal startPrice;

    @NotBlank(message = "нужно добавить ссылку на изображение")
    private String imageLink; // не успеваю добавить изображения, сорри

    @NotBlank(message = "статус не может быть пустым")
    @Pattern(
            regexp = "^(no bids|active|archived)$",
            message = "статус может быть только no bids, active или archived"
    )
    private String status = "no bids";

    @Positive(message = "продолжительность объявления должна быть положительной")
    @NotNull(message = "продолжительность не может быть пустой")
    private Long adDurationMinutes;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    public Ad archiveAd(){
        this.status = "archived";
        return this;
    }

    public Long adDurationSeconds(){
        return this.adDurationMinutes*60;
    }
}
