package com.example.skillTrackr.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false)
    private String content;

    private LocalDateTime commentedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User commentedBy;

    @ManyToOne
    @JoinColumn(name = "skill_id")
    private Skill skill;

}

