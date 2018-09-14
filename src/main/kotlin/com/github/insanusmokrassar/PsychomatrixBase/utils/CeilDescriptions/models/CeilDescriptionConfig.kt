package com.github.insanusmokrassar.PsychomatrixBase.utils.CeilDescriptions.models

import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.CeilDescription

data class CeilDescriptionConfig(
    val title: String = "",
    val description: String = "",
    val note: String? = null,
    val characteristics: List<CeilCharacteristic> = emptyList()
) {
    fun toCeilDescription(countOfCharacteristic: Int): CeilDescription {
        return CeilDescription(
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
