package com.github.insanusmokrassar.PsychomatrixBase.utils.CeilDescriptions

import com.github.insanusmokrassar.PsychomatrixBase.utils.CeilDescriptions.models.CeilsInfosRoot
import com.github.insanusmokrassar.PsychomatrixBase.utils.FilesLoader.load
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.InputStreamReader

const val characteristicsFolder = "characteristics"

private val gson: Gson = GsonBuilder().create()

fun resolveCeilsDescriptionsByLanguage(language: String = "en_US"): CeilsInfosRoot {
    return load("$characteristicsFolder/$language.json").let {
        gson.fromJson(InputStreamReader(it), CeilsInfosRoot::class.java)
    }
}
