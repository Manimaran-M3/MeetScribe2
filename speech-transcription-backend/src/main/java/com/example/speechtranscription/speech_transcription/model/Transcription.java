package com.example.speechtranscription.speech_transcription.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "transcriptions")
@Getter
@Setter
@NoArgsConstructor
public class Transcription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    private LocalDateTime timestamp = LocalDateTime.now();

    public Transcription(String text) {
        this.text = text;
    }
}
