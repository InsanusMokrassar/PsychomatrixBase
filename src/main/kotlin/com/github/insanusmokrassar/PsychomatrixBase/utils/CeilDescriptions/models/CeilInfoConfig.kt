package com.github.insanusmokrassar.PsychomatrixBase.utils.CeilDescriptions.models

import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.CeilInfo

data class CeilInfoConfig(
    val title: String = "",
    val description: String = "",
    val note: String? = null,
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
