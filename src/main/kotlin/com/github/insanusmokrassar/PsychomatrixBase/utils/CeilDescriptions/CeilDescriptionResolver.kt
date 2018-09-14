package com.github.insanusmokrassar.PsychomatrixBase.utils.CeilDescriptions

import com.github.insanusmokrassar.PsychomatrixBase.utils.CeilDescriptions.models.CeilsDescriptionsRoot
import com.github.insanusmokrassar.PsychomatrixBase.utils.load
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.InputStreamReader

const val characteristicsFolder = "characteristics"

private val gson: Gson = GsonBuilder().create()

fun resolveCeilsDescriptionsByLanguage(language: String = "en_US"): CeilsDescriptionsRoot {
    return load("$characteristicsFolder/$language.json").let {
        gson.fromJson(InputStreamReader(it), CeilsDescriptionsRoot::class.java)
    }
}
