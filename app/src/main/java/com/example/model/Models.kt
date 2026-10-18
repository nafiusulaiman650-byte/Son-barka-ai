package com.example.model

enum class NavigationDestination {
  HOME,
  VIDEO_GEN,
  GEMINI_VISUALS,
  VOICE_STUDIO,
  SCRIPT_STUDIO,
  STORY_DIALECT_INPUT,
  PHOTO_EDITOR,
  VIDEO_EDITOR,
  PROJECTS,
  SETTINGS
}

data class CinematicVisualProject(
  val id: String,
  val narrativeInput: String,
  val cinematicStyle: String,
  val aspectRatio: String,
  val cameraLens: String,
  val colorPalette: List<String>,
  val scenes: List<CinematicSceneVisual>,
  val characterContinuityNotes: String,
  val isGeneratedWithGemini: Boolean = true,
  val createdAt: Long = System.currentTimeMillis()
)

data class CinematicSceneVisual(
  val sceneNumber: Int,
  val title: String,
  val timeOffset: String,
  val visualDescription: String,
  val cameraDirection: String,
  val lightingAndAtmosphere: String,
  val costumeAndCharacters: String,
  val dialogueOrNarration: String,
  val veoPrompt: String,
  val imageBase64: String? = null,
  val accentColorHex: String = "#F59E0B"
)

data class CulturalDialect(
  val id: String,
  val name: String,
  val nativeName: String,
  val language: Language,
  val region: String,
  val cadenceDescription: String,
  val proverbExample: String,
  val phoneticNotes: String,
  val defaultGender: String = "Female",
  val pitchMultiplier: Float = 1.0f,
  val speedMultiplier: Float = 0.95f
)

enum class NarrationDeliveryStyle(
  val title: String,
  val subtitle: String,
  val speed: Float,
  val pitch: Float
) {
  ELDER_GRIOT("Elder Griot (Fireside)", "Slow, atmospheric, traditional pauses", 0.85f, 0.90f),
  ROYAL_COURT("Royal Court Chronicler", "Commanding, dignified, ceremonial", 0.95f, 0.95f),
  MOTHERLY_FOLKTALE("Motherly Folktale", "Warm, soothing, melodic cadence", 1.0f, 1.15f),
  MODERN_KANNYWOOD("Modern Kannywood Drama", "Expressive, dynamic, emotive", 1.05f, 1.05f),
  PASTORAL_POETIC("Pastoral Sahelian Poetry", "Lyrical, rhythmic, flowing", 0.92f, 1.0f),
  TOWN_CRIER("Town Crier & Commercial", "Energetic, bright, high-clarity", 1.15f, 1.10f)
}


enum class Language(
  val displayName: String,
  val nativeName: String,
  val defaultDialect: String,
  val iconEmoji: String
) {
  HAUSA("Hausa", "Harshen Hausa", "Kano Standard / Northern Nigeria", "🇳🇬"),
  FULFULDE("Fulfulde", "Pulaar / Fulani", "Adamawa / Gombe / Sahel", "🌍"),
  ENGLISH("English", "Nigerian & Global English", "Lagos / West African Standard", "🌐"),
  ARABIC("Arabic", "العربية (Sahelian & Classical)", "Sudano-Sahelian / MSA", "🇸🇩"),
  FRENCH("French", "Français Ouest-Africain", "Niamey / Dakar / Abidjan", "🇫🇷"),
  SWAHILI("Swahili", "Kiswahili", "East & Central African", "🇰🇪"),
  YORUBA("Yoruba", "Èdè Yorùbá", "South-West Nigerian", "🇳🇬"),
  IGBO("Igbo", "Asụsụ Igbo", "South-East Nigerian", "🇳🇬")
}

data class VoiceProfile(
  val id: String,
  val name: String,
  val language: Language,
  val gender: String, // "Male" or "Female"
  val dialect: String,
  val toneDescription: String,
  val sampleText: String,
  val sampleTranslation: String,
  val isElevenLabsTrained: Boolean = true,
  val isFeatured: Boolean = false
)

enum class AspectRatio(val ratioText: String, val description: String) {
  CINEMATIC_16_9("16:9", "Cinematic Film & TV"),
  PORTRAIT_9_16("9:16", "TikTok, Reels & Shorts"),
  SQUARE_1_1("1:1", "Instagram & Feed Posts")
}

enum class VideoResolution(val label: String, val creditMultiplier: Int) {
  HD_720P("720p HD", 1),
  FHD_1080P("1080p Full HD", 2),
  UHD_4K("4K Ultra Cinema", 4)
}

enum class VideoStyle(val displayName: String, val promptEnhancement: String) {
  CINEMATIC_DRAMA("Cinematic African Drama", "cinematic lighting, 35mm film look, rich color grading, shallow depth of field"),
  HAUSA_NOLLYWOOD("Hausa Kannywood Classic", "vibrant traditional northern Nigerian costume, golden sunlight, Kano palace architecture"),
  HISTORICAL_EPIC("Sahelian Historical Epic", "ancient mud architecture, 16th century Hausa cavalry, detailed embroidered robes, atmospheric dust and sun rays"),
  PASTORAL_NOMADIC("Fulani Nomadic Folktale", "lush savannah grasslands, traditional zebu cattle herd, gourd Calabash art, gentle sunrise breeze"),
  DOCUMENTARY("Heritage Documentary", "photorealistic National Geographic style, clear authentic framing, realistic skin tones, high detail"),
  AFRO_FUTURISM("Sahelian Afro-Futurism", "futuristic Kano metropolis with solar minarets, glowing neon indigo patterns, cinematic sci-fi")
}

data class VideoScene(
  val sceneNumber: Int,
  val title: String,
  val durationSec: Int,
  val visualPrompt: String,
  val cameraAngle: String,
  val characterContinuity: String,
  val narrationText: String,
  val englishSubtitle: String,
  val voiceId: String,
  val status: SceneStatus = SceneStatus.READY
)

enum class SceneStatus {
  DRAFT,
  GENERATING,
  READY,
  FAILED
}

data class VideoProject(
  val id: String,
  val title: String,
  val description: String,
  val aspectRatio: AspectRatio,
  val resolution: VideoResolution,
  val style: VideoStyle,
  val language: Language,
  val totalDurationSec: Int,
  val scenes: List<VideoScene>,
  val audioTrackName: String,
  val includeBilingualSubtitles: Boolean,
  val createdAt: Long = System.currentTimeMillis()
)

data class ScriptStory(
  val id: String,
  val title: String,
  val genre: String,
  val language: Language,
  val logline: String,
  val characters: List<StoryCharacter>,
  val scenes: List<ScriptSceneStory>
)

data class StoryCharacter(
  val name: String,
  val role: String,
  val voiceId: String,
  val costumeDescription: String,
  val visualFeatures: String
)

data class ScriptSceneStory(
  val sceneNumber: Int,
  val location: String,
  val time: String,
  val visualAction: String,
  val dialogues: List<DialogueLine>
)

data class DialogueLine(
  val speaker: String,
  val voiceId: String,
  val textNative: String,
  val textEnglish: String,
  val emotion: String
)

data class PhotoEditJob(
  val id: String,
  val title: String,
  val userPrompt: String,
  val presetName: String,
  val preserveFace: Boolean,
  val enhancementLevel: Float, // 0.0 to 1.0
  val status: String,
  val beforeDrawable: Int? = null,
  val afterDrawable: Int? = null
)

enum class UserTier(val title: String, val maxSecPerVideo: Int, val maxRes: VideoResolution, val monthlyCredits: Int) {
  FREE("Free Tier", 30, VideoResolution.HD_720P, 50),
  PRO("Pro Studio", 90, VideoResolution.FHD_1080P, 500),
  ENTERPRISE("Enterprise Cinema", 300, VideoResolution.UHD_4K, 2500)
}

enum class AiServiceType(
  val serviceName: String,
  val purpose: String,
  val requiredKeyName: String,
  val defaultProvider: String
) {
  LLM_SCRIPT("Story & Script Intelligence", "Generates culturally authentic scripts, storyboards & character prompts", "OPENAI_API_KEY", "GPT-4o / Gemini 1.5 Pro"),
  VOICE_SYNTHESIS("Authentic Voice Synthesis", "Native Hausa & Fulfulde neural text-to-speech with emotional inflections", "ELEVENLABS_API_KEY", "ElevenLabs Multilingual v2 / Android TTS"),
  VIDEO_GENERATION("Cinematic Video Engine", "Generates high-fidelity scenes with character and lighting continuity", "RUNWAY_API_KEY", "Runway Gen-3 Alpha / Luma Dream Machine"),
  PHOTO_IMAGE("Visual Enhancement & Photos", "Background replacement, Sahelian aesthetic filters & restoration", "REPLICATE_API_KEY", "Imagen 3 / Stable Diffusion XL")
}

data class ProviderStatus(
  val serviceType: AiServiceType,
  val isConnected: Boolean,
  val activeModel: String,
  val latencyMs: Int,
  val supportsHausaFulfulde: Boolean,
  val notes: String
)

data class ContentSafetyCheckResult(
  val isApproved: Boolean,
  val score: Float, // 0.0 to 1.0 (1.0 = safe)
  val message: String,
  val flagCategories: List<String>
)
