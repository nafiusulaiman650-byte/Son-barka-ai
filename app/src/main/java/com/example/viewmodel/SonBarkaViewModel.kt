package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.R
import com.example.model.AspectRatio
import com.example.model.ContentSafetyCheckResult
import com.example.model.Language
import com.example.model.NavigationDestination
import com.example.model.PhotoEditJob
import com.example.model.ProviderStatus
import com.example.model.ScriptStory
import com.example.model.UserTier
import com.example.model.VideoProject
import com.example.model.VideoResolution
import com.example.model.VideoScene
import com.example.model.VideoStyle
import com.example.model.VoiceProfile
import com.example.service.ContentSafetyService
import com.example.service.CulturalContentCatalog
import com.example.service.ModularAiCoordinator
import com.example.service.VoiceAudioEngine
import com.example.data.local.SonBarkaDatabase
import com.example.data.local.entity.DialectPreferenceEntity
import com.example.data.local.entity.StoryDraftEntity
import com.example.data.repository.StoryDraftRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID

class SonBarkaViewModel(application: Application) : AndroidViewModel(application) {

  val audioEngine = VoiceAudioEngine(application)

  private val database = SonBarkaDatabase.getInstance(application)
  private val storyDraftRepository = StoryDraftRepository(database.storyDraftDao(), database.dialectPreferenceDao())

  val savedStoryDrafts: StateFlow<List<StoryDraftEntity>> = storyDraftRepository.allDrafts
    .stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5000),
      initialValue = emptyList()
    )

  val savedDialectPreference: StateFlow<DialectPreferenceEntity?> = storyDraftRepository.dialectPreference
    .stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5000),
      initialValue = null
    )

  private val _activeDraftId = MutableStateFlow<Long?>(null)
  val activeDraftId: StateFlow<Long?> = _activeDraftId.asStateFlow()

  private val _draftNotification = MutableStateFlow<String?>(null)
  val draftNotification: StateFlow<String?> = _draftNotification.asStateFlow()

  private val _currentDestination = MutableStateFlow(NavigationDestination.HOME)
  val currentDestination: StateFlow<NavigationDestination> = _currentDestination.asStateFlow()

  private val _creditBalance = MutableStateFlow(120)
  val creditBalance: StateFlow<Int> = _creditBalance.asStateFlow()

  private val _userTier = MutableStateFlow(UserTier.PRO)
  val userTier: StateFlow<UserTier> = _userTier.asStateFlow()

  // Video Generator State
  private val _videoPrompt = MutableStateFlow("Cinematic Hausa royal procession entering Kano city gates at sunset, with golden banners and majestic cavalry")
  val videoPrompt: StateFlow<String> = _videoPrompt.asStateFlow()

  private val _selectedStyle = MutableStateFlow(VideoStyle.HISTORICAL_EPIC)
  val selectedStyle: StateFlow<VideoStyle> = _selectedStyle.asStateFlow()

  private val _selectedAspectRatio = MutableStateFlow(AspectRatio.CINEMATIC_16_9)
  val selectedAspectRatio: StateFlow<AspectRatio> = _selectedAspectRatio.asStateFlow()

  private val _selectedResolution = MutableStateFlow(VideoResolution.FHD_1080P)
  val selectedResolution: StateFlow<VideoResolution> = _selectedResolution.asStateFlow()

  private val _selectedVideoLanguage = MutableStateFlow(Language.HAUSA)
  val selectedVideoLanguage: StateFlow<Language> = _selectedVideoLanguage.asStateFlow()

  private val _videoDurationSec = MutableStateFlow(60) // 1 minute multi-scene by default!
  val videoDurationSec: StateFlow<Int> = _videoDurationSec.asStateFlow()

  private val _isVideoGenerating = MutableStateFlow(false)
  val isVideoGenerating: StateFlow<Boolean> = _isVideoGenerating.asStateFlow()

  private val _generationProgress = MutableStateFlow(0f)
  val generationProgress: StateFlow<Float> = _generationProgress.asStateFlow()

  private val _activeProject = MutableStateFlow<VideoProject?>(null)
  val activeProject: StateFlow<VideoProject?> = _activeProject.asStateFlow()

  private val _savedProjects = MutableStateFlow<List<VideoProject>>(emptyList())
  val savedProjects: StateFlow<List<VideoProject>> = _savedProjects.asStateFlow()

  // Voice Studio State
  private val _selectedVoiceLanguage = MutableStateFlow(Language.HAUSA)
  val selectedVoiceLanguage: StateFlow<Language> = _selectedVoiceLanguage.asStateFlow()

  private val _selectedVoice = MutableStateFlow<VoiceProfile>(CulturalContentCatalog.VOICE_PROFILES.first())
  val selectedVoice: StateFlow<VoiceProfile> = _selectedVoice.asStateFlow()

  private val _customTtsText = MutableStateFlow("")
  val customTtsText: StateFlow<String> = _customTtsText.asStateFlow()

  // Story / Script Studio State
  private val _scriptConcept = MutableStateFlow("Sarauniya Amina ta Zazzau da dabarun yakin da suka kare martabar kasarta")
  val scriptConcept: StateFlow<String> = _scriptConcept.asStateFlow()

  private val _scriptGenre = MutableStateFlow("Historical Epic Drama")
  val scriptGenre: StateFlow<String> = _scriptGenre.asStateFlow()

  private val _scriptLanguage = MutableStateFlow(Language.HAUSA)
  val scriptLanguage: StateFlow<Language> = _scriptLanguage.asStateFlow()

  private val _isGeneratingScript = MutableStateFlow(false)
  val isGeneratingScript: StateFlow<Boolean> = _isGeneratingScript.asStateFlow()

  private val _currentScript = MutableStateFlow<ScriptStory?>(null)
  val currentScript: StateFlow<ScriptStory?> = _currentScript.asStateFlow()

  // Storytelling Content & Dialect Narration State
  private val _storytellingContent = MutableStateFlow(
    "Ga ta ga ta nan. Ta zo mu ji ta. A wani gari mai albarka a kasar Hausa, Gizo ya shirya dabara domin samun abinci a lokacin rani. Ya je wajen Damo ya ce masa: 'Ya abokina, zo mu yi tafiya zuwa rafi domin neman abin da zai amfane mu.' Amma Damo mai wayo ne, ya lura da tarkon da Gizo ya dana..."
  )
  val storytellingContent: StateFlow<String> = _storytellingContent.asStateFlow()

  private val _selectedDialect = MutableStateFlow(CulturalContentCatalog.CULTURAL_DIALECTS.first())
  val selectedDialect: StateFlow<com.example.model.CulturalDialect> = _selectedDialect.asStateFlow()

  private val _selectedNarrationDelivery = MutableStateFlow(com.example.model.NarrationDeliveryStyle.ELDER_GRIOT)
  val selectedNarrationDelivery: StateFlow<com.example.model.NarrationDeliveryStyle> = _selectedNarrationDelivery.asStateFlow()

  // Gemini Cinematic Visual Studio State
  private val _geminiNarrativeText = MutableStateFlow(
    "A legendary 16th century Hausa cavalry brigade led by Queen Amina of Zazzau arriving at sunset before the towering red mud-brick fortifications of ancient Kano city, golden sand dust swirling under horse hooves, royal banners unfurling."
  )
  val geminiNarrativeText: StateFlow<String> = _geminiNarrativeText.asStateFlow()

  private val _geminiCinematicStyle = MutableStateFlow("Sahelian Epic Cinema (35mm Panavision)")
  val geminiCinematicStyle: StateFlow<String> = _geminiCinematicStyle.asStateFlow()

  private val _geminiAspectRatio = MutableStateFlow("16:9")
  val geminiAspectRatio: StateFlow<String> = _geminiAspectRatio.asStateFlow()

  private val _geminiCameraLens = MutableStateFlow("35mm Anamorphic Prime")
  val geminiCameraLens: StateFlow<String> = _geminiCameraLens.asStateFlow()

  private val _isGeneratingVisuals = MutableStateFlow(false)
  val isGeneratingVisuals: StateFlow<Boolean> = _isGeneratingVisuals.asStateFlow()

  private val _visualGenerationStep = MutableStateFlow("")
  val visualGenerationStep: StateFlow<String> = _visualGenerationStep.asStateFlow()

  private val _geminiVisualProject = MutableStateFlow<com.example.model.CinematicVisualProject?>(null)
  val geminiVisualProject: StateFlow<com.example.model.CinematicVisualProject?> = _geminiVisualProject.asStateFlow()

  // Photo Editor State
  private val _photoPrompt = MutableStateFlow("Enhance skin tones with golden Sahelian hour lighting, convert background to Kano indigo mud-brick palace")
  val photoPrompt: StateFlow<String> = _photoPrompt.asStateFlow()

  private val _photoStylePreset = MutableStateFlow("Nollywood Cinematic 35mm")
  val photoStylePreset: StateFlow<String> = _photoStylePreset.asStateFlow()

  private val _preserveFace = MutableStateFlow(true)
  val preserveFace: StateFlow<Boolean> = _preserveFace.asStateFlow()

  private val _enhancementStrength = MutableStateFlow(0.85f)
  val enhancementStrength: StateFlow<Float> = _enhancementStrength.asStateFlow()

  private val _isProcessingPhoto = MutableStateFlow(false)
  val isProcessingPhoto: StateFlow<Boolean> = _isProcessingPhoto.asStateFlow()

  private val _photoHistory = MutableStateFlow<List<PhotoEditJob>>(emptyList())
  val photoHistory: StateFlow<List<PhotoEditJob>> = _photoHistory.asStateFlow()

  // Moderation / Alert State
  private val _safetyAlert = MutableStateFlow<ContentSafetyCheckResult?>(null)
  val safetyAlert: StateFlow<ContentSafetyCheckResult?> = _safetyAlert.asStateFlow()

  // Provider Statuses
  private val _providers = MutableStateFlow<List<ProviderStatus>>(ModularAiCoordinator.getProviderStatuses())
  val providers: StateFlow<List<ProviderStatus>> = _providers.asStateFlow()

  init {
    loadSeedData()
  }

  private fun loadSeedData() {
    viewModelScope.launch {
      // Create initial high quality seed project
      val seedProject = ModularAiCoordinator.generateMultiSceneVideo(
        prompt = "Queen Amina of Zazzau leading cavalry across golden Sahelian plains",
        style = VideoStyle.HISTORICAL_EPIC,
        aspectRatio = AspectRatio.CINEMATIC_16_9,
        resolution = VideoResolution.FHD_1080P,
        language = Language.HAUSA,
        targetDurationSec = 60
      )
      _activeProject.value = seedProject
      _savedProjects.value = listOf(seedProject)

      // Initial script
      val seedScript = ModularAiCoordinator.generateStoryScript(
        concept = "Sarauniya Amina ta Zazzau",
        language = Language.HAUSA,
        genre = "Historical Drama",
        targetDurationSec = 60
      )
      _currentScript.value = seedScript

      // Initial photo jobs
      _photoHistory.value = listOf(
        PhotoEditJob(
          id = "photo_1",
          title = "Sahelian Royal Portrait",
          userPrompt = "Golden hour cinematic enhancement with Kano Indigo textiles",
          presetName = "Kano Indigo Royal",
          preserveFace = true,
          enhancementLevel = 0.9f,
          status = "Completed",
          beforeDrawable = R.drawable.ic_son_barka_icon,
          afterDrawable = R.drawable.img_hero_banner
        )
      )

      // Initial Gemini Visual Project
      _geminiVisualProject.value = com.example.service.GeminiVisualService.generateLocalCinematicVisuals(
        narrativeText = _geminiNarrativeText.value,
        cinematicStyle = _geminiCinematicStyle.value,
        aspectRatio = _geminiAspectRatio.value,
        cameraLens = _geminiCameraLens.value
      )

      // Seed Room database if empty
      try {
        if (storyDraftRepository.getDraftCount() == 0) {
          val kanoDialect = CulturalContentCatalog.getDialectById("hausa_kananci")
          storyDraftRepository.saveDraft(
            title = "Gizo Da Damo (Tatsuniyar Hausa)",
            content = "Ga ta ga ta nan. Ta zo mu ji ta. A wani gari mai albarka a kasar Hausa, Gizo ya shirya dabara domin samun abinci a lokacin rani. Ya je wajen Damo ya ce masa: 'Ya abokina, zo mu yi tafiya zuwa rafi domin neman abin da zai amfane mu.' Amma Damo mai wayo ne, ya lura da tarkon da Gizo ya dana...",
            dialect = kanoDialect,
            deliveryStyle = com.example.model.NarrationDeliveryStyle.ELDER_GRIOT
          )

          val fulfuldeDialect = CulturalContentCatalog.getDialectById("fulfulde_adamawa")
          storyDraftRepository.saveDraft(
            title = "Ngaynaaka e Durngol (Pastoral Odyssey)",
            content = "Woni koota woni ndaara. E nder ladde mawnde nder Adamawa, suka gorko biyeteeɗo Buba ina foofa sereendu makko les lekki ɓoolii. Na'i ɗii no ndurna no beldi, ndiyam maayo kadi no yaara e jam...",
            dialect = fulfuldeDialect,
            deliveryStyle = com.example.model.NarrationDeliveryStyle.PASTORAL_POETIC
          )
        }

        // Restore user's saved dialect preference from Room
        val savedPref = storyDraftRepository.getDialectPreferenceOnce()
        if (savedPref != null) {
          val dialect = CulturalContentCatalog.CULTURAL_DIALECTS.firstOrNull { it.id == savedPref.dialectId }
          if (dialect != null) {
            _selectedDialect.value = dialect
          }
          val delivery = com.example.model.NarrationDeliveryStyle.values().firstOrNull { it.name == savedPref.deliveryStyleId }
          if (delivery != null) {
            _selectedNarrationDelivery.value = delivery
          }
        }
      } catch (e: Exception) {
        android.util.Log.e("SonBarkaViewModel", "Error initializing Room draft seed: ${e.message}")
      }
    }
  }

  fun navigateTo(destination: NavigationDestination) {
    _currentDestination.value = destination
  }

  fun setVideoPrompt(prompt: String) {
    _videoPrompt.value = prompt
  }

  fun setVideoStyle(style: VideoStyle) {
    _selectedStyle.value = style
  }

  fun setAspectRatio(ratio: AspectRatio) {
    _selectedAspectRatio.value = ratio
  }

  fun setResolution(res: VideoResolution) {
    _selectedResolution.value = res
  }

  fun setVideoLanguage(lang: Language) {
    _selectedVideoLanguage.value = lang
  }

  fun setVideoDuration(sec: Int) {
    _videoDurationSec.value = sec
  }

  fun setVoiceLanguage(lang: Language) {
    _selectedVoiceLanguage.value = lang
    val matching = CulturalContentCatalog.getVoicesForLanguage(lang)
    if (matching.isNotEmpty()) {
      _selectedVoice.value = matching.first()
    }
  }

  fun selectVoice(voice: VoiceProfile) {
    _selectedVoice.value = voice
  }

  fun setCustomTtsText(text: String) {
    _customTtsText.value = text
  }

  fun playVoiceSample(profile: VoiceProfile, customText: String? = null) {
    audioEngine.previewVoice(profile, customText)
  }

  fun stopAudio() {
    audioEngine.stop()
  }

  fun setScriptConcept(concept: String) {
    _scriptConcept.value = concept
  }

  fun setScriptGenre(genre: String) {
    _scriptGenre.value = genre
  }

  fun setScriptLanguage(lang: Language) {
    _scriptLanguage.value = lang
  }

  fun setPhotoPrompt(prompt: String) {
    _photoPrompt.value = prompt
  }

  fun setPhotoStylePreset(preset: String) {
    _photoStylePreset.value = preset
  }

  fun setPreserveFace(preserve: Boolean) {
    _preserveFace.value = preserve
  }

  fun setEnhancementStrength(strength: Float) {
    _enhancementStrength.value = strength
  }

  fun dismissSafetyAlert() {
    _safetyAlert.value = null
  }

  fun generateVideo() {
    val prompt = _videoPrompt.value
    // Safety check first
    val safety = ContentSafetyService.validateContent(prompt)
    if (!safety.isApproved) {
      _safetyAlert.value = safety
      return
    }

    val cost = ModularAiCoordinator.calculateVideoCreditCost(_videoDurationSec.value, _selectedResolution.value)
    if (_creditBalance.value < cost) {
      _safetyAlert.value = ContentSafetyCheckResult(
        isApproved = false,
        score = 0.5f,
        message = "Insufficient credits: This ${_videoDurationSec.value}s video requires $cost credits, but your balance is ${_creditBalance.value}. Upgrade to Pro for more credits.",
        flagCategories = listOf("Credit Balance Limit")
      )
      return
    }

    viewModelScope.launch {
      _isVideoGenerating.value = true
      _generationProgress.value = 0.1f

      for (step in 1..9) {
        kotlinx.coroutines.delay(200)
        _generationProgress.value = step * 0.1f
      }

      val newProject = ModularAiCoordinator.generateMultiSceneVideo(
        prompt = prompt,
        style = _selectedStyle.value,
        aspectRatio = _selectedAspectRatio.value,
        resolution = _selectedResolution.value,
        language = _selectedVideoLanguage.value,
        targetDurationSec = _videoDurationSec.value
      )

      _creditBalance.value -= cost
      _activeProject.value = newProject
      _savedProjects.value = listOf(newProject) + _savedProjects.value
      _isVideoGenerating.value = false
      _generationProgress.value = 1.0f
    }
  }

  fun generateScript() {
    val concept = _scriptConcept.value
    val safety = ContentSafetyService.validateContent(concept)
    if (!safety.isApproved) {
      _safetyAlert.value = safety
      return
    }

    viewModelScope.launch {
      _isGeneratingScript.value = true
      val script = ModularAiCoordinator.generateStoryScript(
        concept = concept,
        language = _scriptLanguage.value,
        genre = _scriptGenre.value,
        targetDurationSec = _videoDurationSec.value
      )
      _currentScript.value = script
      _isGeneratingScript.value = false
    }
  }

  fun convertScriptToVideo(script: ScriptStory) {
    _videoPrompt.value = "${script.title}: ${script.logline}"
    _selectedVideoLanguage.value = script.language
    navigateTo(NavigationDestination.VIDEO_GEN)
  }

  fun processPhotoEdit() {
    val prompt = _photoPrompt.value
    val safety = ContentSafetyService.validateContent(prompt)
    if (!safety.isApproved) {
      _safetyAlert.value = safety
      return
    }

    viewModelScope.launch {
      _isProcessingPhoto.value = true
      kotlinx.coroutines.delay(1200)

      val newJob = PhotoEditJob(
        id = UUID.randomUUID().toString(),
        title = "Sahelian Edit #${_photoHistory.value.size + 1}",
        userPrompt = prompt,
        presetName = _photoStylePreset.value,
        preserveFace = _preserveFace.value,
        enhancementLevel = _enhancementStrength.value,
        status = "Completed",
        beforeDrawable = R.drawable.ic_son_barka_icon,
        afterDrawable = R.drawable.img_hero_banner
      )

      _photoHistory.value = listOf(newJob) + _photoHistory.value
      _isProcessingPhoto.value = false
    }
  }

  fun updateSceneTitle(sceneIndex: Int, newTitle: String) {
    val project = _activeProject.value ?: return
    val updatedScenes = project.scenes.mapIndexed { idx, scene ->
      if (idx == sceneIndex) scene.copy(title = newTitle) else scene
    }
    _activeProject.value = project.copy(scenes = updatedScenes)
  }

  fun deleteProject(projectId: String) {
    _savedProjects.value = _savedProjects.value.filter { it.id != projectId }
    if (_activeProject.value?.id == projectId) {
      _activeProject.value = _savedProjects.value.firstOrNull()
    }
  }

  fun setStorytellingContent(content: String) {
    _storytellingContent.value = content
  }

  fun selectDialect(dialect: com.example.model.CulturalDialect) {
    _selectedDialect.value = dialect
    viewModelScope.launch {
      storyDraftRepository.saveDialectPreference(dialect, _selectedNarrationDelivery.value)
    }
  }

  fun selectNarrationDelivery(delivery: com.example.model.NarrationDeliveryStyle) {
    _selectedNarrationDelivery.value = delivery
    viewModelScope.launch {
      storyDraftRepository.saveDialectPreference(_selectedDialect.value, delivery)
    }
  }

  fun saveCurrentStoryDraft(customTitle: String? = null) {
    val content = _storytellingContent.value.trim()
    if (content.isBlank()) return

    val safety = ContentSafetyService.validateContent(content)
    if (!safety.isApproved) {
      _safetyAlert.value = safety
      return
    }

    viewModelScope.launch {
      val dialect = _selectedDialect.value
      val delivery = _selectedNarrationDelivery.value
      val id = _activeDraftId.value ?: 0L

      val savedId = storyDraftRepository.saveDraft(
        title = customTitle ?: "",
        content = content,
        dialect = dialect,
        deliveryStyle = delivery,
        existingId = id
      )
      _activeDraftId.value = savedId
      _draftNotification.value = "Draft saved to Room DB: ${dialect.name}"
    }
  }

  fun loadStoryDraft(draft: StoryDraftEntity) {
    _storytellingContent.value = draft.content
    _activeDraftId.value = draft.id

    val matchingDialect = CulturalContentCatalog.CULTURAL_DIALECTS.firstOrNull { it.id == draft.dialectId }
      ?: CulturalContentCatalog.CULTURAL_DIALECTS.first()
    _selectedDialect.value = matchingDialect

    val matchingDelivery = com.example.model.NarrationDeliveryStyle.values().firstOrNull { it.name == draft.deliveryStyleId }
      ?: com.example.model.NarrationDeliveryStyle.ELDER_GRIOT
    _selectedNarrationDelivery.value = matchingDelivery

    _draftNotification.value = "Loaded: '${draft.title}'"
  }

  fun deleteStoryDraft(draftId: Long) {
    viewModelScope.launch {
      storyDraftRepository.deleteDraft(draftId)
      if (_activeDraftId.value == draftId) {
        _activeDraftId.value = null
      }
      _draftNotification.value = "Draft deleted from Room DB"
    }
  }

  fun clearActiveDraft() {
    _activeDraftId.value = null
    _storytellingContent.value = ""
  }

  fun dismissDraftNotification() {
    _draftNotification.value = null
  }

  fun playDialectNarration(customText: String? = null) {
    val textToSpeak = customText?.ifBlank { null } ?: _storytellingContent.value
    val dialect = _selectedDialect.value
    val delivery = _selectedNarrationDelivery.value

    audioEngine.setRate(delivery.speed * dialect.speedMultiplier)
    audioEngine.setPitch(delivery.pitch * dialect.pitchMultiplier)
    audioEngine.speakText(
      text = textToSpeak,
      language = dialect.language,
      gender = dialect.defaultGender,
      voiceId = dialect.id
    )
  }

  fun loadStoryStarter(starter: CulturalContentCatalog.DialectStoryStarter) {
    _storytellingContent.value = starter.content
    val dialect = CulturalContentCatalog.getDialectById(starter.dialectId)
    _selectedDialect.value = dialect
  }

  fun convertStoryToVideo() {
    val dialect = _selectedDialect.value
    _videoPrompt.value = "Cinematic storytelling in ${dialect.name}: ${_storytellingContent.value.take(150)}"
    _selectedVideoLanguage.value = dialect.language
    navigateTo(NavigationDestination.VIDEO_GEN)
  }

  fun setGeminiNarrativeText(text: String) {
    _geminiNarrativeText.value = text
  }

  fun setGeminiCinematicStyle(style: String) {
    _geminiCinematicStyle.value = style
  }

  fun setGeminiAspectRatio(ratio: String) {
    _geminiAspectRatio.value = ratio
  }

  fun setGeminiCameraLens(lens: String) {
    _geminiCameraLens.value = lens
  }

  fun generateGeminiVisualContent() {
    val text = _geminiNarrativeText.value.trim()
    val safety = ContentSafetyService.validateContent(text)
    if (!safety.isApproved) {
      _safetyAlert.value = safety
      return
    }

    viewModelScope.launch {
      _isGeneratingVisuals.value = true
      _visualGenerationStep.value = "Analyzing narrative context with Gemini 3.5 Flash..."
      kotlinx.coroutines.delay(500)
      _visualGenerationStep.value = "Directing camera angles & Sahelian lighting palette..."
      kotlinx.coroutines.delay(600)
      _visualGenerationStep.value = "Synthesizing cinematic visual scenes & continuity..."

      val project = com.example.service.GeminiVisualService.generateCinematicVisuals(
        narrativeText = text,
        cinematicStyle = _geminiCinematicStyle.value,
        aspectRatio = _geminiAspectRatio.value,
        cameraLens = _geminiCameraLens.value
      )

      _geminiVisualProject.value = project
      _visualGenerationStep.value = "Completed!"
      _isGeneratingVisuals.value = false
    }
  }

  fun sendVisualProjectToVideoGen(project: com.example.model.CinematicVisualProject) {
    val firstScene = project.scenes.firstOrNull()
    _videoPrompt.value = firstScene?.veoPrompt ?: project.narrativeInput
    navigateTo(NavigationDestination.VIDEO_GEN)
  }

  override fun onCleared() {
    super.onCleared()
    audioEngine.release()
  }
}

