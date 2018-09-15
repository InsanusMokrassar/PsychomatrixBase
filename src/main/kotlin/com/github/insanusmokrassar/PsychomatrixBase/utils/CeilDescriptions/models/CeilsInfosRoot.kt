package com.github.insanusmokrassar.PsychomatrixBase.utils.CeilDescriptions.models

import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.CeilInfo
import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.CeilState

data class CeilsInfosRoot(
    val language: String? = "en_US",
    private val descriptionsList: List<List<CeilInfoConfig>> = emptyList()
) {
    fun resolveCeilDescription(
        ceilState: CeilState
    ): CeilInfo {
        return descriptionsList[ceilState.x][ceilState.y].toCeilDescription(
            ceilState.count ?: 0
        )
    }
}
