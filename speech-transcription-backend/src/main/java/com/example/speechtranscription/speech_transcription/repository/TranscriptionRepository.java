package com.example.speechtranscription.speech_transcription.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.speechtranscription.speech_transcription.model.Transcription;

@Repository
public interface TranscriptionRepository extends JpaRepository<Transcription, Long> {
    Optional<Transcription> findFirstByOrderByTimestampDesc();  // Fetch latest transcription
}
