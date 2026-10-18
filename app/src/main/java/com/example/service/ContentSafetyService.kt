package com.example.service

import com.example.model.ContentSafetyCheckResult

object ContentSafetyService {

  private val PROHIBITED_KEYWORDS = listOf(
    "nude", "nudity", "porn", "pornography", "sexual", "erotic", "nsfw", "xxx",
    "stripper", "underwear", "intercourse", "genitals", "explicit",
    // Child exploitation protection
    "minor", "child exploitation", "underage", "pedophile", "cp",
    // Violence & Hate
    "terrorist", "suicide", "bombing", "genocide", "hate speech", "lynch",
    "deepfake celebrity impersonation"
  )

  fun validateContent(text: String): ContentSafetyCheckResult {
    val lower = text.lowercase().trim()
    val flagged = mutableListOf<String>()

    if (PROHIBITED_KEYWORDS.any { lower.contains(it) }) {
      if (lower.contains("minor") || lower.contains("child") || lower.contains("underage")) {
        flagged.add("Child Safety Policy (Zero Tolerance)")
      }
      if (lower.contains("nude") || lower.contains("porn") || lower.contains("sexual") || lower.contains("nsfw")) {
        flagged.add("Sexually Explicit / Adult Content")
      }
      if (lower.contains("terror") || lower.contains("bomb") || lower.contains("suicide") || lower.contains("hate")) {
        flagged.add("Violence, Harm & Extremism")
      }
      if (lower.contains("deepfake") || lower.contains("impersonation")) {
        flagged.add("Unauthorized Impersonation")
      }

      if (flagged.isEmpty()) {
        flagged.add("Safety & Community Standard Flag")
      }

      return ContentSafetyCheckResult(
        isApproved = false,
        score = 0.15f,
        message = "Generation blocked: Prompt violates Son Barka AI safety policy regarding prohibited content.",
        flagCategories = flagged
      )
    }

    return ContentSafetyCheckResult(
      isApproved = true,
      score = 0.98f,
      message = "Content passed ethical safety standards. Ready for neural synthesis.",
      flagCategories = emptyList()
    )
  }
}
