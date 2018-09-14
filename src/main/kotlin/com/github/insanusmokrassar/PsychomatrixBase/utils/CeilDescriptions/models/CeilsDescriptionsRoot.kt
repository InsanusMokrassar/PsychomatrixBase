package com.github.insanusmokrassar.PsychomatrixBase.utils.CeilDescriptions.models

import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.CeilDescription
import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.PsychomatrixCeilInfo

data class CeilsDescriptionsRoot(
    val language: String? = "en_US",
    private val descriptionsList: List<List<CeilDescriptionConfig>> = emptyList()
) {
    fun resolveCeilDescription(
        psychomatrixCeilInfo: PsychomatrixCeilInfo
    ): CeilDescription {
        return descriptionsList[psychomatrixCeilInfo.x][psychomatrixCeilInfo.y].toCeilDescription(
            psychomatrixCeilInfo.count ?: 0
        )
    }
}
