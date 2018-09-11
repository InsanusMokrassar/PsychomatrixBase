package com.github.insanusmokrassar.PsychomatrixBase.data.repository

import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.CeilDescription
import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.PsychomatrixCeilInfo

interface PsychomatrixCeilDescriptionRepository {
    fun getCeilDescription(psychomatrixCeilInfo: PsychomatrixCeilInfo): CeilDescription
}
