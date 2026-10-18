package com.example

import com.example.model.Language
import com.example.model.VideoResolution
import com.example.service.ContentSafetyService
import com.example.service.CulturalContentCatalog
import com.example.service.ModularAiCoordinator
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {

  @Test
  fun testContentSafetyService_BlocksExplicitContent() {
    val unsafePrompt = "Generate explicit nude scene"
    val result = ContentSafetyService.validateContent(unsafePrompt)
    assertFalse(result.isApproved)
    assertTrue(result.flagCategories.isNotEmpty())
  }

  @Test
  fun testContentSafetyService_ApprovesCulturalPrompt() {
    val culturalPrompt = "Sarauniya Amina ta Zazzau da dabarun yakin da suka kare martabar kasarta"
    val result = ContentSafetyService.validateContent(culturalPrompt)
    assertTrue(result.isApproved)
    assertTrue(result.flagCategories.isEmpty())
  }

  @Test
  fun testCreditCostCalculation() {
    // 60s video at 1080p (multiplier 2): (60/10) * 5 * 2 = 6 * 5 * 2 = 60 credits
    val cost60sFhd = ModularAiCoordinator.calculateVideoCreditCost(60, VideoResolution.FHD_1080P)
    assertEquals(60, cost60sFhd)

    // 30s video at 720p (multiplier 1): (30/10) * 5 * 1 = 3 * 5 = 15 credits
    val cost30sHd = ModularAiCoordinator.calculateVideoCreditCost(30, VideoResolution.HD_720P)
    assertEquals(15, cost30sHd)
  }

  @Test
  fun testCulturalVoiceProfiles_HausaAndFulfulde() {
    val hausaVoices = CulturalContentCatalog.getVoicesForLanguage(Language.HAUSA)
    val fulfuldeVoices = CulturalContentCatalog.getVoicesForLanguage(Language.FULFULDE)

    assertTrue("Hausa voices should be available", hausaVoices.isNotEmpty())
    assertTrue("Fulfulde voices should be available", fulfuldeVoices.isNotEmpty())

    val amina = hausaVoices.find { it.id == "hausa_amina_kano" }
    assertNotNull("Amina Kano voice must exist", amina)
    assertTrue(amina!!.sampleText.contains("Kano"))

    val danbaba = fulfuldeVoices.find { it.id == "fulfulde_danbaba_adamawa" }
    assertNotNull("Danbaba Adamawa voice must exist", danbaba)
    assertTrue(danbaba!!.dialect.contains("Adamawa"))
  }

  @Test
  fun testCulturalDialectsCatalog() {
    val dialects = CulturalContentCatalog.CULTURAL_DIALECTS
    assertTrue("Dialects list should not be empty", dialects.isNotEmpty())

    val kananci = dialects.find { it.id == "hausa_kananci" }
    assertNotNull("Kananci dialect must exist", kananci)
    assertTrue(kananci!!.region.contains("Kano"))

    val sakkwatanci = dialects.find { it.id == "hausa_sakkwatanci" }
    assertNotNull("Sakkwatanci dialect must exist", sakkwatanci)
    assertTrue(sakkwatanci!!.cadenceDescription.contains("Caliphate"))

    val adamawa = dialects.find { it.id == "fulfulde_adamawa" }
    assertNotNull("Adamawa Fulfulde dialect must exist", adamawa)
    assertTrue(adamawa!!.proverbExample.contains("Pulaku"))
  }

  @Test
  fun testDialectStoryStarters() {
    val starters = CulturalContentCatalog.DIALECT_STORY_STARTERS
    assertTrue("Story starters should be provided", starters.isNotEmpty())
    val gizoTale = starters.find { it.dialectId == "hausa_kananci" }
    assertNotNull("Gizo folktale starter must exist", gizoTale)
    assertTrue(gizoTale!!.content.contains("Ga ta ga ta nan"))
  }
}
