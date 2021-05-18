package com.github.insanusmokrassar.PsychomatrixBase.utils

import kotlinx.serialization.json.Json

val nonstrictJson = Json {
    ignoreUnknownKeys = true
    allowSpecialFloatingPointValues = true
}
