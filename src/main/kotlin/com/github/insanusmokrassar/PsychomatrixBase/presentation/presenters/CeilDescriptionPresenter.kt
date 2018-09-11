package com.github.insanusmokrassar.PsychomatrixBase.presentation.presenters

import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.CeilDescription
import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.PsychomatrixCeilInfo
import kotlinx.coroutines.experimental.Deferred

interface CeilDescriptionPresenter {
    fun onUserChooseCeil(ceilInfo: PsychomatrixCeilInfo): Deferred<CeilDescription>
}