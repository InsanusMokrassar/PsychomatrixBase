package com.github.insanusmokrassar.PsychomatrixBase.utils.CeilDescriptions

import com.github.insanusmokrassar.PsychomatrixBase.utils.CeilDescriptions.models.CeilsInfosRoot
import com.github.insanusmokrassar.PsychomatrixBase.utils.FilesLoader.load
import kotlinx.serialization.json.Json
import java.io.InputStreamReader

const val characteristicsFolder = "characteristics"

val availableTranslations = listOf(
    "en_US",
    "ru_RU"
)

private val defaultLanguage = availableTranslations.first()

private fun findSubTranslation(language: String): String {
    return if (availableTranslations.contains(language)) {
        return language
    } else {
        val localeUpper = language.split("_").first()
        availableTranslations.firstOrNull {
            it.startsWith(localeUpper)
        } ?: defaultLanguage
    }
}

fun resolveCeilsDescriptionsByLanguage(language: String = "en_US"): CeilsInfosRoot {
    return load("$characteristicsFolder/${findSubTranslation(language)}.json").let {
        Json.nonstrict.parse(CeilsInfosRoot.serializer(), InputStreamReader(it).readText())
    }
}
