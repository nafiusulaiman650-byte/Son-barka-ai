package com.example.service

import com.example.model.Language
import com.example.model.VoiceProfile

object CulturalContentCatalog {

  val VOICE_PROFILES = listOf(
    VoiceProfile(
      id = "hausa_amina_kano",
      name = "Amina (Kano Standard)",
      language = Language.HAUSA,
      gender = "Female",
      dialect = "Kano City & Northern Metropolitan",
      toneDescription = "Warm, maternal, articulate storyteller. Ideal for historical epics, educational docs, and emotional dramas.",
      sampleText = "Barkanmu da wannan lokaci mai albarka. A yau za mu duba tarihin birnin Kano da al'adun da suka dade suna haskaka kasar Hausa.",
      sampleTranslation = "Welcome at this blessed hour. Today we examine the history of Kano city and the traditions that have long illuminated Hausaland.",
      isElevenLabsTrained = true,
      isFeatured = true
    ),
    VoiceProfile(
      id = "hausa_ibrahim_sokoto",
      name = "Ibrahim (Sokoto Royal)",
      language = Language.HAUSA,
      gender = "Male",
      dialect = "Sokoto Caliphate Classical",
      toneDescription = "Deep, commanding, regal cadence. Perfect for heroic narrations, royal declarations, and cinematic trailers.",
      sampleText = "Assalamu alaikum ya jama'a. Wannan labari ne na jarumtaka da juriya a fagen fama, wanda kakanni suka bari a matsayin darasi.",
      sampleTranslation = "Peace be upon you all. This is a tale of valor and resilience on the battlefield, which our ancestors left as a timeless lesson.",
      isElevenLabsTrained = true,
      isFeatured = true
    ),
    VoiceProfile(
      id = "hausa_fatima_zaria",
      name = "Fatima (Zaria Expressive)",
      language = Language.HAUSA,
      gender = "Female",
      dialect = "Zazzau & Modern Northern Youth",
      toneDescription = "Bright, youthful, crisp conversational tone. Ideal for modern Kannywood series, podcasts, and social media reels.",
      sampleText = "Kuna jin dadin sabon shirinmu? Kar ku manta cewa kirkirar bidiyo da fasahar AI na iya canza yadda muke bada labaranmu a duniya.",
      sampleTranslation = "Are you enjoying our new show? Never forget that AI video creation can revolutionize how we share our African stories with the world.",
      isElevenLabsTrained = true,
      isFeatured = false
    ),
    VoiceProfile(
      id = "hausa_balarabe_bauchi",
      name = "Balarabe (Bauchi Commercial)",
      language = Language.HAUSA,
      gender = "Male",
      dialect = "Bauchi & North-East Hausa",
      toneDescription = "Energetic, engaging, persuasive presenter voice. Superb for commercials, announcements, and fast-paced recaps.",
      sampleText = "Sanarwa ta musamman ga manoma da 'yan kasuwa! Dandalin Son Barka AI ya kawo muku hanyar talla mafi sauki da inganci.",
      sampleTranslation = "Special announcement for farmers and merchants! Son Barka AI brings you the simplest and most effective advertising platform.",
      isElevenLabsTrained = true,
      isFeatured = false
    ),
    VoiceProfile(
      id = "fulfulde_danbaba_adamawa",
      name = "Danbaba (Adamawa Pastoral)",
      language = Language.FULFULDE,
      gender = "Male",
      dialect = "Adamawa / Yola / Cameroon Border",
      toneDescription = "Melodic, lyrical pastoral griot. Rhythmic cadence suited for ancient Fulani folklore, poetry, and pastoral epics.",
      sampleText = "Jam balɗe mon bandiraaɓe. Ɗum no haani en ndento e aadaaji men ɗi pullo fuu yiɗi, ngam ngurnaaki jam e ɓural leydi.",
      sampleTranslation = "Peace to your days, kinfolk. We must cherish our beloved traditions which every Fulani holds dear, for peace and prosperity in our lands.",
      isElevenLabsTrained = true,
      isFeatured = true
    ),
    VoiceProfile(
      id = "fulfulde_aissatou_gombe",
      name = "Aissatou (Gombe Folktale)",
      language = Language.FULFULDE,
      gender = "Female",
      dialect = "Gombe & Eastern Sahelian Pulaar",
      toneDescription = "Clear, soothing, authentic oral storyteller. Gentle pitch for family tales, lullabies, and cultural preservation.",
      sampleText = "Kowgal ngal ko ngal mawnungol! En ngardi ngam ɓeydugo kawtal e nder leydi men, en kollita duuniyaaru dannaaki leñol Fulɓe.",
      sampleTranslation = "This endeavor is momentous! We have gathered to foster unity across our land, and to show the world the beauty and grace of the Fulani heritage.",
      isElevenLabsTrained = true,
      isFeatured = true
    ),
    VoiceProfile(
      id = "fulfulde_oumarou_sahel",
      name = "Oumarou (Sahelian Elder)",
      language = Language.FULFULDE,
      gender = "Male",
      dialect = "Sahel / Niger & Mali Pulaar",
      toneDescription = "Resonant, weathered, wise elder cadence with traditional pauses and dramatic emphasis.",
      sampleText = "Subaka moyya, ndaw heeriinde wuro men nder ladde. Haa jooni teeyteeyel nagge e ɗowdi lekki ɗon siftora taariika laamu.",
      sampleTranslation = "A blessed morning upon our desert encampment. Even now the bell of the lead cow and the shade of the baobab remember the ancient kingdom.",
      isElevenLabsTrained = true,
      isFeatured = false
    ),
    VoiceProfile(
      id = "english_chidi_nigerian",
      name = "Chidi (Nigerian English)",
      language = Language.ENGLISH,
      gender = "Male",
      dialect = "Pan-Nigerian / African Broadcast",
      toneDescription = "Crisp, confident African narrator with polished articulation for global cinematic distribution.",
      sampleText = "Welcome to Son Barka AI, where the ancestral power of African storytelling merges with state-of-the-art cinematic intelligence.",
      sampleTranslation = "Welcome to Son Barka AI, where the ancestral power of African storytelling merges with state-of-the-art cinematic intelligence.",
      isElevenLabsTrained = true,
      isFeatured = false
    ),
    VoiceProfile(
      id = "arabic_tariq_sahel",
      name = "Tariq (Sudano-Sahelian Arabic)",
      language = Language.ARABIC,
      gender = "Male",
      dialect = "Sahelian & Classical Arabic",
      toneDescription = "Majestic classical Arabic cadence with Sahelian warmth, perfect for Islamic historical docs and cross-border epics.",
      sampleText = "أهلاً بكم في استوديو سون بركة للذكاء الاصطناعي، حيث نروي أمجاد أفريقيا وحضاراتها الخالدة بأحدث تقنيات السينما العالمية.",
      sampleTranslation = "Welcome to Son Barka AI Studio, where we narrate the glories and timeless civilizations of Africa using modern cinema tech.",
      isElevenLabsTrained = true,
      isFeatured = false
    )
  )

  val CULTURAL_DIALECTS = listOf(
    com.example.model.CulturalDialect(
      id = "hausa_kananci",
      name = "Kano Standard (Kananci)",
      nativeName = "Kananci Mai Daraja",
      language = Language.HAUSA,
      region = "Kano Metropolitan & Northern Media Hub",
      cadenceDescription = "Crystal-clear metropolitan Hausa, prestigious tone, balanced pitch for cinematic narration and drama.",
      proverbExample = "Karin Magana: 'Kano ta dabo cigari, ko da mai ka zo an fika.'",
      phoneticNotes = "Accurate tonal registers, clear implosives (ɓ, ɗ) and glottalized 'y.",
      defaultGender = "Female",
      pitchMultiplier = 1.0f,
      speedMultiplier = 1.0f
    ),
    com.example.model.CulturalDialect(
      id = "hausa_sakkwatanci",
      name = "Sokoto Caliphate (Sakkwatanci)",
      nativeName = "Sakkwatanci na Asali",
      language = Language.HAUSA,
      region = "Sokoto, Zamfara & Kebbi Caliphate Basin",
      cadenceDescription = "Deep, solemn, authoritative rhythm rooted in Caliphate royal chronicles and Islamic historical narratives.",
      proverbExample = "Karin Magana: 'Gaskiya ta fi zuma dadi ga mai hankali da natsuwa.'",
      phoneticNotes = "Preserved classical vocabulary, dignified pause cadence, guttural warmth.",
      defaultGender = "Male",
      pitchMultiplier = 0.88f,
      speedMultiplier = 0.92f
    ),
    com.example.model.CulturalDialect(
      id = "hausa_zazzaganci",
      name = "Zaria & Kaduna (Zazzaganci)",
      nativeName = "Zazzaganci Mai Dadin Ji",
      language = Language.HAUSA,
      region = "Zazzau Kingdom & Central Savannah",
      cadenceDescription = "Melodic, expressive, gentle cadence prominent in Northern Nigerian literature, poetry, and emotional drama.",
      proverbExample = "Karin Magana: 'Hannu daya ba ya daukar jinka; hadin kai shi ne karfi.'",
      phoneticNotes = "Flowing pitch glide, softened terminal syllables, warm vowel transitions.",
      defaultGender = "Female",
      pitchMultiplier = 1.08f,
      speedMultiplier = 0.98f
    ),
    com.example.model.CulturalDialect(
      id = "hausa_katsinanci",
      name = "Katsina & Daura (Katsinanci)",
      nativeName = "Katsinanci na Al'ada",
      language = Language.HAUSA,
      region = "Katsina Emirate & Cradle of Hausa Origin",
      cadenceDescription = "Eloquent court rhetoric, poetic cadence filled with ancient proverbs, heroic epics, and royal praise poetry.",
      proverbExample = "Karin Magana: 'Da rarrafe yaro kan tashi; hakuri shi ne jigon nasara.'",
      phoneticNotes = "Distinct melodic rise at sentence closures, rich cultural imagery.",
      defaultGender = "Male",
      pitchMultiplier = 0.94f,
      speedMultiplier = 0.95f
    ),
    com.example.model.CulturalDialect(
      id = "hausa_guddiranci",
      name = "Bauchi & Katagum (Guddiranci)",
      nativeName = "Guddiranci Mai Kuzari",
      language = Language.HAUSA,
      region = "Bauchi, Gombe & North-East Savannah",
      cadenceDescription = "Lively, energetic, rhythmic delivery suited for fast-paced folklore, town announcements, and commercials.",
      proverbExample = "Karin Magana: 'Kowa ya ci zomo, ya ci gudu; aiki da cikawa.'",
      phoneticNotes = "Crisp, defined consonant attacks, brisk tempo.",
      defaultGender = "Male",
      pitchMultiplier = 1.0f,
      speedMultiplier = 1.06f
    ),
    com.example.model.CulturalDialect(
      id = "fulfulde_adamawa",
      name = "Adamawa & Yola (Eastern Fulfulde)",
      nativeName = "Fulfulde Adamawa (Pulaaku)",
      language = Language.FULFULDE,
      region = "Adamawa, Taraba & North Cameroon",
      cadenceDescription = "Lyrical pastoral cadence, flowing melody celebrating Savannah cattle transhumance and Pulaku honor codes.",
      proverbExample = "Taalol: 'Pulaku ko ɓural leñol; teddungal ɗon jippoo e semteende.'",
      phoneticNotes = "Pure long vowels (aa, ee, oo), distinct implosives (ɓ, ɗ, ƴ), melodic cadence.",
      defaultGender = "Female",
      pitchMultiplier = 1.10f,
      speedMultiplier = 0.92f
    ),
    com.example.model.CulturalDialect(
      id = "fulfulde_gombe",
      name = "Gombe & Central Savannah",
      nativeName = "Fulfulde Gombe",
      language = Language.FULFULDE,
      region = "Gombe, Bauchi & Gongola Basin",
      cadenceDescription = "Warm fireside folktale cadence, gentle rhythmic pauses, soothing for family and ancestral tales.",
      proverbExample = "Taalol: 'Nagge wootere hisnata wuro nder ladde mawnde.'",
      phoneticNotes = "Flawless noun class concordance, gentle elision.",
      defaultGender = "Female",
      pitchMultiplier = 1.05f,
      speedMultiplier = 0.95f
    ),
    com.example.model.CulturalDialect(
      id = "fulfulde_sokoto",
      name = "Sokoto / Gwandu (Western Torodi)",
      nativeName = "Fulfulde Hirnaange",
      language = Language.FULFULDE,
      region = "Western Rima Basin & Niger Border",
      cadenceDescription = "Resonant, weathered pastoral voice reflecting long desert journeys and ancient nomadic wisdom.",
      proverbExample = "Taalol: 'Ladde ɗon mari taariika; laawol wuro woɗɗaa to en ngoni e jam.'",
      phoneticNotes = "Deep chest resonance, deliberate consonant gemination.",
      defaultGender = "Male",
      pitchMultiplier = 0.86f,
      speedMultiplier = 0.90f
    ),
    com.example.model.CulturalDialect(
      id = "fulfulde_sahel",
      name = "Sahelian Nomadic (Pulaaku / Border)",
      nativeName = "Pulaar Sahel",
      language = Language.FULFULDE,
      region = "Niger, Mali & Chad Sahelian Strip",
      cadenceDescription = "Ancient oral epic cadence with dramatic atmospheric pauses, storytelling beneath the desert starlight.",
      proverbExample = "Taalol: 'Ko pullo wari ladde, aadaaji ɗon tokki mo ko feewi.'",
      phoneticNotes = "Dramatic dynamic shifts from whispering wind to heroic decree.",
      defaultGender = "Male",
      pitchMultiplier = 0.90f,
      speedMultiplier = 0.88f
    ),
    com.example.model.CulturalDialect(
      id = "english_nigerian",
      name = "Nigerian English (Broadcast)",
      nativeName = "Nigerian English",
      language = Language.ENGLISH,
      region = "Pan-Nigerian / African Cinema",
      cadenceDescription = "Articulate, confident, rich African English cadence for global documentary and cinematic narration.",
      proverbExample = "Proverb: 'A person who asks questions never gets lost in the forest.'",
      phoneticNotes = "Clear syllable-timed rhythm with West African warmth.",
      defaultGender = "Male",
      pitchMultiplier = 0.98f,
      speedMultiplier = 1.0f
    ),
    com.example.model.CulturalDialect(
      id = "arabic_sahelian",
      name = "Sudano-Sahelian Arabic",
      nativeName = "العربية الساحلية",
      language = Language.ARABIC,
      region = "Borno, Chad & Sudan Basin",
      cadenceDescription = "Majestic classical recitation cadence with Sahelian warmth, ideal for historical records.",
      proverbExample = "حكمة: 'العلم نور والجهل ظلام، وتاريخ الأجداد فخرٌ للأحفاد.'",
      phoneticNotes = "Rich pharyngeal articulation and poetic resonance.",
      defaultGender = "Male",
      pitchMultiplier = 0.92f,
      speedMultiplier = 0.92f
    ),
    com.example.model.CulturalDialect(
      id = "french_westafrican",
      name = "West African French (Sahel)",
      nativeName = "Français Ouest-Africain",
      language = Language.FRENCH,
      region = "Niamey, Dakar & Abidjan",
      cadenceDescription = "Smooth, lyrical West African Francophone cadence for international African co-productions.",
      proverbExample = "Proverbe: 'L'arbre ne grandit bien qu'avec des racines profondes.'",
      phoneticNotes = "Melodic vowel phrasing and expressive musicality.",
      defaultGender = "Female",
      pitchMultiplier = 1.08f,
      speedMultiplier = 0.98f
    )
  )

  data class DialectStoryStarter(
    val title: String,
    val dialectId: String,
    val category: String,
    val content: String,
    val translation: String
  )

  val DIALECT_STORY_STARTERS = listOf(
    DialectStoryStarter(
      title = "Tatsuniyar Gizo da Damo (Kananci)",
      dialectId = "hausa_kananci",
      category = "Hausa Classical Folktale",
      content = "Ga ta ga ta nan. Ta zo mu ji ta. A wani gari mai albarka a kasar Hausa, Gizo ya shirya dabara domin samun abinci a lokacin rani. Ya je wajen Damo ya ce masa: 'Ya abokina, zo mu yi tafiya zuwa rafi domin neman abin da zai amfane mu.' Amma Damo mai wayo ne, ya lura da tarkon da Gizo ya dana...",
      translation = "Here is a tale! Let it come so we may hear it. In a blessed town in Hausaland, Gizo the spider devised a cunning plan to acquire food during the dry season. He went to Damo the monitor lizard and whispered: 'My friend, let us journey to the stream...' But wise Damo sensed the trap..."
    ),
    DialectStoryStarter(
      title = "Sarauniya Amina: Jarumar Zazzau (Zazzaganci)",
      dialectId = "hausa_zazzaganci",
      category = "Historical Epic Drama",
      content = "A karni na sha shida, wata jaruma mai kishi da kwarjini ta bayyana a kasar Zazzau. Sunanta Sarauniya Amina. Tun tana yarinya take hawa doki tana jagorantar dakarunta. Ta kewaye garuruwa da manyan ganuwowi da aka sani da Ganuwar Amina, domin kare martaba da mutuncin al'ummarta daga kowace irin barazana.",
      translation = "In the 16th century, a fearless warrior of radiant majesty arose in Zazzau. Her name was Queen Amina. From youth she rode upon warhorses leading her troops. She encircled cities with mighty defensive ramparts, Queen Amina's walls, to safeguard the dignity and honor of her people."
    ),
    DialectStoryStarter(
      title = "Kariyar Birnin Sokoto (Sakkwatanci)",
      dialectId = "hausa_sakkwatanci",
      category = "Royal Caliphate Chronicle",
      content = "A zamanin da a fadin Daular Sokoto, manyan malamai da jarumai sun hadu domin shimfida adalci da ilimi a dukkan fadin kasar. Suna girmama alkawari, suna kare marasa karfi. Wannan shi ne asalin daukakar da har yanzu muke tunawa da ita a matsayin madubin rayuwa ga kowane mai son zaman lafiya da ci gaba.",
      translation = "In ancient days across the Sokoto Caliphate, esteemed scholars and warriors united to establish justice and knowledge across the realm. They honored their covenants and shielded the vulnerable. This is the bedrock of glory we remember as a beacon for all who cherish peace and progress."
    ),
    DialectStoryStarter(
      title = "Taalol Pullo e Nagge Mum (Fulfulde Adamawa)",
      dialectId = "fulfulde_adamawa",
      category = "Fulani Pastoral Legend",
      content = "Taalol taalol, gommbol gommbol. Wuro gooto ɗonno nder ladde mawnde haa Adamawa. Pullo gooto bi'eteeɗo Danbaba ɗon mari nagge wootere nde o yiɗi sanne. Nyande go'o, nder ceeɗu, ndiyam ɗon ɗoostee. Danbaba ƴeewi asamaan, o fuufi serdu maako, nagge ngeen ɗon tokki mo haa ɓe tawi maayo bee ndiyam belɗam.",
      translation = "A tale, a wondrous tale! There once lived an encampment in the vast savannah of Adamawa. A Fulani herdsman named Danbaba possessed a beloved white cow. One day during the severe dry season when water was scarce, Danbaba looked up, played his pastoral wooden flute, and guided the herd to a cool fresh river."
    ),
    DialectStoryStarter(
      title = "Pulaaku e Teddungal Ladde (Fulfulde Sahel)",
      dialectId = "fulfulde_sahel",
      category = "Sahelian Oral Ballad",
      content = "Subaka moyya ya bandiraaɓe. Ko laawol ladde ɗon juuti, ɓernde pullo ɗon tiiɗa. En ndenndi ngam hisnugo aadaaji men ɗi kakanni accani en. Pulaaku ko semteende, ko kawtal, ko teddungal leñol fuu. Haa jooni ɗowdi lekki ɗon siftora taariika laamuuji men.",
      translation = "A blessed morning, kinfolk. Though the desert trail is vast and long, the heart of the herdsman remains steadfast. We gather to safeguard the traditions bequeathed by our ancestors. Pulaaku is modesty, unity, and honor for all. Even now the shade of the ancient baobab remembers our kingdoms."
    )
  )

  fun getVoicesForLanguage(language: Language): List<VoiceProfile> {
    return VOICE_PROFILES.filter { it.language == language }
  }

  fun getVoiceById(id: String): VoiceProfile {
    return VOICE_PROFILES.firstOrNull { it.id == id } ?: VOICE_PROFILES.first()
  }

  fun getDialectById(id: String): com.example.model.CulturalDialect {
    return CULTURAL_DIALECTS.firstOrNull { it.id == id } ?: CULTURAL_DIALECTS.first()
  }

  fun getDialectsForLanguage(language: Language): List<com.example.model.CulturalDialect> {
    return CULTURAL_DIALECTS.filter { it.language == language }
  }
}

