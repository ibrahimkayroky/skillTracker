package com.example.skillTrackr.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "endorsements", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"endorser_id", "skill_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Endorsement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "endorser_id")
    private User endorser;

    @ManyToOne
    @JoinColumn(name = "skill_id")
    private Skill skill;

    private LocalDateTime endorsedAt;
}
