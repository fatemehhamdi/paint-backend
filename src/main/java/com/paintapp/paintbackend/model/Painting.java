package com.paintapp.paintbackend.model;

import jakarta.persistence.*;
import lombok.Data;
import com.paintapp.paintbackend.model.User;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "paintings")

public class Painting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String canvas_data;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
