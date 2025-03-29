import React, { useState } from 'react';
import { View, Text, Button, StyleSheet, ScrollView, ActivityIndicator } from 'react-native';
import axios from 'axios';

const BASE_URL = "http://127.0.0.1:5000/api/audio";

export default function App() {
  const [transcription, setTranscription] = useState("");
  const [pastTranscriptions, setPastTranscriptions] = useState([]);
  const [loading, setLoading] = useState(false);

  const startRecording = async () => {
    await axios.get(`${BASE_URL}/start`)
      .then(() => alert("🎙️ Recording started..."))
      .catch(() => alert("❌ Failed to start recording"));
  };

  const stopRecording = async () => {
    await axios.get(`${BASE_URL}/stop`)
      .then(() => alert("⏹️ Recording stopped..."))
      .catch(() => alert("❌ Failed to stop recording"));
  };

  const transcribeAudio = async () => {
    setLoading(true); // Show loading spinner while transcribing
    
    await axios.get(`${BASE_URL}/transcribe`)
      .then((res) => {
        // ✅ Capture the transcription and instantly show it
        setTranscription(res.data);
        alert("✅ Audio transcribed successfully!");
      })
      .catch(() => alert("❌ Failed to transcribe audio"))
      .finally(() => setLoading(false)); // Hide loading spinner
  };

  const fetchTranscriptions = async () => {
    await axios.get(`${BASE_URL}/transcriptions`)
      .then((res) => {
        // ✅ Capture all transcriptions from database
        setPastTranscriptions(res.data);
      })
      .catch(() => alert("❌ Failed to fetch transcriptions"));
  };

  return (
    <View style={styles.container}>
      <Text style={styles.title}>🎤 Speech Transcription</Text>

      <Button title="🎙️ Start Recording" onPress={startRecording} />
      <Button title="⏹️ Stop Recording" onPress={stopRecording} />
      <Button title="📝 Transcribe Audio" onPress={transcribeAudio} />
      <Button title="📜 View Past Transcriptions" onPress={fetchTranscriptions} />

      <Text style={styles.heading}>Live Transcription:</Text>

      {loading ? (
        <ActivityIndicator size="large" color="#0000ff" />
      ) : (
        <ScrollView style={styles.scrollView}>
          <Text style={styles.text}>{transcription}</Text>
        </ScrollView>
      )}

      <Text style={styles.heading}>Past Transcriptions:</Text>
      <ScrollView style={styles.scrollView}>
        {pastTranscriptions.map((item, index) => (
          <Text key={index} style={styles.pastText}>
            📜 {item.text}
          </Text>
        ))}
      </ScrollView>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    padding: 20,
    marginTop: 50,
  },
  title: {
    fontSize: 28,
    fontWeight: 'bold',
    marginBottom: 10,
    textAlign: 'center',
  },
  heading: {
    fontSize: 18,
    fontWeight: 'bold',
    marginTop: 20,
  },
  text: {
    backgroundColor: '#f4f4f4',
    padding: 10,
    borderRadius: 5,
  },
  pastText: {
    backgroundColor: '#e3f2fd',
    padding: 10,
    borderRadius: 5,
    marginBottom: 5,
  },
  scrollView: {
    maxHeight: 200,
  },
});
