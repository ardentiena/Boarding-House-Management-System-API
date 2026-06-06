package com.boardinghouse.boardinghouse_api.model;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rooms")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room {
        @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "room_number", unique = true, nullable = false, length = 20)
    private String roomNumber;
    
    @Column(name = "price_per_month", nullable = false)
    private BigDecimal pricePerMonth;
    
    @Column(name = "is_available")
    private Boolean isAvailable = true;
    
    @Column(name = "description", length = 500)
    private String description;
}
