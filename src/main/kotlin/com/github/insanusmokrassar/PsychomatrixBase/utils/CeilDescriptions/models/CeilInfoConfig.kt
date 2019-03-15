package com.github.insanusmokrassar.PsychomatrixBase.utils.CeilDescriptions.models

import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.CeilInfo
import kotlinx.serialization.Optional
import kotlinx.serialization.Serializable

@Serializable
data class CeilInfoConfig(
    @Optional
    val title: String = "",
    @Optional
    val description: String = "",
    @Optional
    val note: String? = null,
    @Optional
    val characteristics: List<CeilCharacteristic> = emptyList()
) {
    fun toCeilDescription(countOfCharacteristic: Int): CeilInfo {
        return CeilInfo(
            title,
            description,
            countOfCharacteristic.let {
                if (characteristics.size <= it) {
                    characteristics.lastOrNull() ?: description
                } else {
                    characteristics[it]
                }
            },
            note
        )
    }
}
