package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.model.NavigationDestination
import com.example.ui.components.SonBarkaBottomBar
import com.example.ui.components.SonBarkaTopBar
import com.example.ui.screens.CreationsScreen
import com.example.ui.screens.GeminiVisualStudioScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.PhotoEditorScreen
import com.example.ui.screens.ProviderSettingsScreen
import com.example.ui.screens.ScriptStudioScreen
import com.example.ui.screens.StoryDialectInputScreen
import com.example.ui.screens.VideoEditorScreen
import com.example.ui.screens.VideoGeneratorScreen
import com.example.ui.screens.VoiceStudioScreen
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.SonBarkaViewModel

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme(darkTheme = true) {
        val viewModel: SonBarkaViewModel = viewModel()
        SonBarkaApp(viewModel)
      }
    }
  }
}

@Composable
fun SonBarkaApp(viewModel: SonBarkaViewModel) {
  val destination by viewModel.currentDestination.collectAsState()
  val creditBalance by viewModel.creditBalance.collectAsState()
  val userTier by viewModel.userTier.collectAsState()
  val activeProject by viewModel.activeProject.collectAsState()
  val savedProjects by viewModel.savedProjects.collectAsState()
  val providers by viewModel.providers.collectAsState()
  val safetyAlert by viewModel.safetyAlert.collectAsState()

  // Video Generator States
  val videoPrompt by viewModel.videoPrompt.collectAsState()
  val selectedStyle by viewModel.selectedStyle.collectAsState()
  val selectedAspectRatio by viewModel.selectedAspectRatio.collectAsState()
  val selectedResolution by viewModel.selectedResolution.collectAsState()
  val selectedVideoLanguage by viewModel.selectedVideoLanguage.collectAsState()
  val videoDurationSec by viewModel.videoDurationSec.collectAsState()
  val isVideoGenerating by viewModel.isVideoGenerating.collectAsState()
  val generationProgress by viewModel.generationProgress.collectAsState()

  // Voice Studio States
  val selectedVoiceLanguage by viewModel.selectedVoiceLanguage.collectAsState()
  val selectedVoice by viewModel.selectedVoice.collectAsState()
  val customTtsText by viewModel.customTtsText.collectAsState()

  // Script Studio States
  val scriptConcept by viewModel.scriptConcept.collectAsState()
  val scriptGenre by viewModel.scriptGenre.collectAsState()
  val scriptLanguage by viewModel.scriptLanguage.collectAsState()
  val isGeneratingScript by viewModel.isGeneratingScript.collectAsState()
  val currentScript by viewModel.currentScript.collectAsState()

  // Storytelling & Dialect States
  val storytellingContent by viewModel.storytellingContent.collectAsState()
  val selectedDialect by viewModel.selectedDialect.collectAsState()
  val selectedNarrationDelivery by viewModel.selectedNarrationDelivery.collectAsState()
  val savedDrafts by viewModel.savedStoryDrafts.collectAsState()
  val activeDraftId by viewModel.activeDraftId.collectAsState()
  val draftNotification by viewModel.draftNotification.collectAsState()

  // Gemini Visual Studio States
  val geminiNarrativeText by viewModel.geminiNarrativeText.collectAsState()
  val geminiCinematicStyle by viewModel.geminiCinematicStyle.collectAsState()
  val geminiAspectRatio by viewModel.geminiAspectRatio.collectAsState()
  val geminiCameraLens by viewModel.geminiCameraLens.collectAsState()
  val isGeneratingVisuals by viewModel.isGeneratingVisuals.collectAsState()
  val visualGenerationStep by viewModel.visualGenerationStep.collectAsState()
  val geminiVisualProject by viewModel.geminiVisualProject.collectAsState()

  // Photo Editor States
  val photoPrompt by viewModel.photoPrompt.collectAsState()
  val photoStylePreset by viewModel.photoStylePreset.collectAsState()
  val preserveFace by viewModel.preserveFace.collectAsState()
  val enhancementStrength by viewModel.enhancementStrength.collectAsState()
  val isProcessingPhoto by viewModel.isProcessingPhoto.collectAsState()
  val photoHistory by viewModel.photoHistory.collectAsState()

  // Back handler to navigate back to Home if not on Home screen
  if (destination != NavigationDestination.HOME) {
    BackHandler {
      viewModel.navigateTo(NavigationDestination.HOME)
    }
  }

  Scaffold(
    topBar = {
      SonBarkaTopBar(
        currentDestination = destination,
        creditBalance = creditBalance,
        userTier = userTier,
        onNavigate = { viewModel.navigateTo(it) }
      )
    },
    bottomBar = {
      SonBarkaBottomBar(
        currentDestination = destination,
        onNavigate = { viewModel.navigateTo(it) }
      )
    },
    modifier = Modifier.fillMaxSize()
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .background(MaterialTheme.colorScheme.background)
    ) {
      when (destination) {
        NavigationDestination.HOME -> HomeScreen(
          savedProjects = savedProjects,
          providers = providers,
          onNavigate = { viewModel.navigateTo(it) },
          onSelectProject = {
            viewModel.navigateTo(NavigationDestination.VIDEO_EDITOR)
          }
        )

        NavigationDestination.VIDEO_GEN -> VideoGeneratorScreen(
          videoPrompt = videoPrompt,
          selectedStyle = selectedStyle,
          selectedAspectRatio = selectedAspectRatio,
          selectedResolution = selectedResolution,
          selectedLanguage = selectedVideoLanguage,
          videoDurationSec = videoDurationSec,
          isVideoGenerating = isVideoGenerating,
          generationProgress = generationProgress,
          activeProject = activeProject,
          onPromptChange = { viewModel.setVideoPrompt(it) },
          onStyleChange = { viewModel.setVideoStyle(it) },
          onAspectRatioChange = { viewModel.setAspectRatio(it) },
          onResolutionChange = { viewModel.setResolution(it) },
          onLanguageChange = { viewModel.setVideoLanguage(it) },
          onDurationChange = { viewModel.setVideoDuration(it) },
          onGenerate = { viewModel.generateVideo() },
          onNavigateToEditor = { viewModel.navigateTo(NavigationDestination.VIDEO_EDITOR) }
        )

        NavigationDestination.VOICE_STUDIO -> VoiceStudioScreen(
          audioEngine = viewModel.audioEngine,
          selectedLanguage = selectedVoiceLanguage,
          selectedVoice = selectedVoice,
          customText = customTtsText,
          onLanguageSelect = { viewModel.setVoiceLanguage(it) },
          onVoiceSelect = { viewModel.selectVoice(it) },
          onCustomTextChange = { viewModel.setCustomTtsText(it) }
        )

        NavigationDestination.SCRIPT_STUDIO -> ScriptStudioScreen(
          scriptConcept = scriptConcept,
          scriptGenre = scriptGenre,
          scriptLanguage = scriptLanguage,
          isGeneratingScript = isGeneratingScript,
          currentScript = currentScript,
          onConceptChange = { viewModel.setScriptConcept(it) },
          onGenreChange = { viewModel.setScriptGenre(it) },
          onLanguageChange = { viewModel.setScriptLanguage(it) },
          onGenerateScript = { viewModel.generateScript() },
          onConvertToVideo = { viewModel.convertScriptToVideo(it) },
          onNavigateToDialectStoryteller = { viewModel.navigateTo(NavigationDestination.STORY_DIALECT_INPUT) }
        )

        NavigationDestination.GEMINI_VISUALS -> GeminiVisualStudioScreen(
          narrativeText = geminiNarrativeText,
          cinematicStyle = geminiCinematicStyle,
          aspectRatio = geminiAspectRatio,
          cameraLens = geminiCameraLens,
          isGenerating = isGeneratingVisuals,
          generationStep = visualGenerationStep,
          visualProject = geminiVisualProject,
          onNarrativeChange = { viewModel.setGeminiNarrativeText(it) },
          onStyleChange = { viewModel.setGeminiCinematicStyle(it) },
          onAspectRatioChange = { viewModel.setGeminiAspectRatio(it) },
          onLensChange = { viewModel.setGeminiCameraLens(it) },
          onGenerate = { viewModel.generateGeminiVisualContent() },
          onSendToVideoGenerator = { viewModel.sendVisualProjectToVideoGen(it) }
        )

        NavigationDestination.STORY_DIALECT_INPUT -> StoryDialectInputScreen(
          storyContent = storytellingContent,
          selectedDialect = selectedDialect,
          selectedDeliveryStyle = selectedNarrationDelivery,
          audioEngine = viewModel.audioEngine,
          savedDrafts = savedDrafts,
          activeDraftId = activeDraftId,
          draftNotification = draftNotification,
          onStoryContentChange = { viewModel.setStorytellingContent(it) },
          onDialectSelect = { viewModel.selectDialect(it) },
          onDeliveryStyleSelect = { viewModel.selectNarrationDelivery(it) },
          onPlayNarration = { viewModel.playDialectNarration() },
          onConvertToVideo = { viewModel.convertStoryToVideo() },
          onGenerateVisualsWithGemini = {
            viewModel.setGeminiNarrativeText(storytellingContent)
            viewModel.navigateTo(NavigationDestination.GEMINI_VISUALS)
          },
          onSaveDraft = { title -> viewModel.saveCurrentStoryDraft(title) },
          onLoadDraft = { draft -> viewModel.loadStoryDraft(draft) },
          onDeleteDraft = { id -> viewModel.deleteStoryDraft(id) },
          onNewDraft = { viewModel.clearActiveDraft() },
          onDismissNotification = { viewModel.dismissDraftNotification() },
          onBack = { viewModel.navigateTo(NavigationDestination.HOME) }
        )

        NavigationDestination.PHOTO_EDITOR -> PhotoEditorScreen(
          photoPrompt = photoPrompt,
          photoStylePreset = photoStylePreset,
          preserveFace = preserveFace,
          enhancementStrength = enhancementStrength,
          isProcessing = isProcessingPhoto,
          photoHistory = photoHistory,
          onPromptChange = { viewModel.setPhotoPrompt(it) },
          onPresetChange = { viewModel.setPhotoStylePreset(it) },
          onPreserveFaceChange = { viewModel.setPreserveFace(it) },
          onEnhancementStrengthChange = { viewModel.setEnhancementStrength(it) },
          onProcessPhoto = { viewModel.processPhotoEdit() }
        )

        NavigationDestination.VIDEO_EDITOR -> VideoEditorScreen(
          activeProject = activeProject,
          onExport = {
            viewModel.navigateTo(NavigationDestination.PROJECTS)
          }
        )

        NavigationDestination.PROJECTS -> CreationsScreen(
          savedProjects = savedProjects,
          savedDrafts = savedDrafts,
          onSelectProject = {
            viewModel.navigateTo(NavigationDestination.VIDEO_EDITOR)
          },
          onDeleteProject = { viewModel.deleteProject(it) },
          onLoadDraft = { draft ->
            viewModel.loadStoryDraft(draft)
            viewModel.navigateTo(NavigationDestination.STORY_DIALECT_INPUT)
          },
          onDeleteDraft = { id -> viewModel.deleteStoryDraft(id) },
          onNavigateToVideo = { viewModel.navigateTo(NavigationDestination.VIDEO_GEN) },
          onNavigateToDialectStoryteller = { viewModel.navigateTo(NavigationDestination.STORY_DIALECT_INPUT) }
        )

        NavigationDestination.SETTINGS -> ProviderSettingsScreen(
          providers = providers,
          userTier = userTier,
          creditBalance = creditBalance
        )
      }
    }

    // Safety Alert Dialog
    if (safetyAlert != null) {
      val alert = safetyAlert!!
      AlertDialog(
        onDismissRequest = { viewModel.dismissSafetyAlert() },
        title = {
          Text(
            text = if (alert.flagCategories.contains("Credit Balance Limit")) "Generation Notice" else "Content Safety Shield Flag",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
          )
        },
        text = {
          Text(
            text = alert.message + if (alert.flagCategories.isNotEmpty()) "\n\nFlagged Categories: ${alert.flagCategories.joinToString(", ")}" else "",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        },
        confirmButton = {
          Button(
            onClick = { viewModel.dismissSafetyAlert() },
            colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary)
          ) {
            Text("Understood", color = Color.Black)
          }
        },
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        shape = RoundedCornerShape(16.dp)
      )
    }
  }
}
