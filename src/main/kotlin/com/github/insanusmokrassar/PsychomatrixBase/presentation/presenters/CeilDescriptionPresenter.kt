package com.github.insanusmokrassar.PsychomatrixBase.presentation.presenters

import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.CeilInfo
import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.CeilState
import kotlinx.coroutines.experimental.Deferred

interface CeilDescriptionPresenter {
    fun onUserChooseCeil(ceilState: CeilState): Deferred<CeilInfo>
}