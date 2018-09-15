package com.github.insanusmokrassar.PsychomatrixBase.utils.CeilDescriptions

import com.github.insanusmokrassar.PsychomatrixBase.utils.CeilDescriptions.models.CeilsInfosRoot
import com.github.insanusmokrassar.PsychomatrixBase.utils.FilesLoader.load
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.InputStreamReader

const val characteristicsFolder = "characteristics"

private val gson: Gson = GsonBuilder().create()

val availableTranslations = listOf(
    "en_US"
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
        gson.fromJson(InputStreamReader(it), CeilsInfosRoot::class.java)
    }
}
