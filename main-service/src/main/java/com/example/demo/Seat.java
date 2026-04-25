package com.example.demo;

import com.example.demo.repository.SeatRepository;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;

@Entity
@Table(name = "seat")
@Data
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long show_id;
    private Status status;
    private Instant locked_at;
    private Long user_id;
    private Integer version;

}
