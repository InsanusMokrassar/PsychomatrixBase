package com.github.insanusmokrassar.PsychomatrixBase.data.repository

import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.CeilInfo
import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.CeilState

interface PsychomatrixCeilDescriptionRepository {
    fun getCeilDescription(ceilState: CeilState): CeilInfo
}
