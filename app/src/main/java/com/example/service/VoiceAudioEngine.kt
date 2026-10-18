package com.example.service

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import com.example.model.Language
import com.example.model.VoiceProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

class VoiceAudioEngine(private val context: Context) : TextToSpeech.OnInitListener {

  private var tts: TextToSpeech? = null
  private var isInitialized = false

  private val _isPlaying = MutableStateFlow(false)
  val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

  private val _activeVoiceId = MutableStateFlow<String?>(null)
  val activeVoiceId: StateFlow<String?> = _activeVoiceId.asStateFlow()

  private val _speechRate = MutableStateFlow(1.0f)
  val speechRate: StateFlow<Float> = _speechRate.asStateFlow()

  private val _speechPitch = MutableStateFlow(1.0f)
  val speechPitch: StateFlow<Float> = _speechPitch.asStateFlow()

  init {
    tts = TextToSpeech(context.applicationContext, this)
  }

  override fun onInit(status: Int) {
    if (status == TextToSpeech.SUCCESS) {
      isInitialized = true
      tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
        override fun onStart(utteranceId: String?) {
          _isPlaying.value = true
        }

        override fun onDone(utteranceId: String?) {
          _isPlaying.value = false
          _activeVoiceId.value = null
        }

        @Deprecated("Deprecated in Java")
        override fun onError(utteranceId: String?) {
          _isPlaying.value = false
          _activeVoiceId.value = null
        }
      })
    } else {
      Log.e("VoiceAudioEngine", "Native TTS initialization failed with status: $status")
    }
  }

  fun setRate(rate: Float) {
    _speechRate.value = rate.coerceIn(0.5f, 2.0f)
    tts?.setSpeechRate(_speechRate.value)
  }

  fun setPitch(pitch: Float) {
    _speechPitch.value = pitch.coerceIn(0.5f, 2.0f)
    tts?.setPitch(_speechPitch.value)
  }

  fun previewVoice(profile: VoiceProfile, customText: String? = null) {
    val textToSpeak = customText?.ifBlank { null } ?: profile.sampleText
    speakText(textToSpeak, profile.language, profile.gender, profile.id)
  }

  fun speakText(text: String, language: Language, gender: String = "Female", voiceId: String? = null) {
    if (!isInitialized || tts == null) {
      Log.w("VoiceAudioEngine", "TTS not initialized yet")
      return
    }

    // Stop ongoing speech
    tts?.stop()

    // Determine best matching locale
    val locale = when (language) {
      Language.HAUSA -> Locale("ha", "NG") // Hausa (Nigeria)
      Language.FULFULDE -> Locale("ff", "NG") // Fulfulde (Nigeria)
      Language.ENGLISH -> Locale("en", "NG") // English (Nigeria)
      Language.ARABIC -> Locale("ar", "SA")
      Language.FRENCH -> Locale.FRENCH
      Language.SWAHILI -> Locale("sw", "KE")
      Language.YORUBA -> Locale("yo", "NG")
      Language.IGBO -> Locale("ig", "NG")
    }

    val result = tts?.setLanguage(locale)
    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
      // Fallback gracefully to general English or default device locale while preserving custom pitch & cadence
      tts?.setLanguage(Locale.ENGLISH)
    }

    // Adjust pitch dynamically for gender and personality
    val genderPitch = if (gender == "Male") 0.85f else 1.15f
    tts?.setPitch(_speechPitch.value * genderPitch)
    tts?.setSpeechRate(_speechRate.value)

    _activeVoiceId.value = voiceId
    _isPlaying.value = true

    val utteranceId = "utterance_${System.currentTimeMillis()}"
    tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId)
  }

  fun stop() {
    tts?.stop()
    _isPlaying.value = false
    _activeVoiceId.value = null
  }

  fun release() {
    tts?.stop()
    tts?.shutdown()
    tts = null
  }
}
