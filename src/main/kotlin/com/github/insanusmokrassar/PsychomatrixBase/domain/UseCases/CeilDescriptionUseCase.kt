package com.github.insanusmokrassar.PsychomatrixBase.domain.UseCases

import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.CeilInfo
import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.CeilState
import kotlinx.coroutines.experimental.channels.ReceiveChannel

typealias CeilDescriptionReady = Pair<CeilState, CeilInfo>

interface CeilDescriptionUseCase {
    fun openCeilDescriptionReadySubscription(): ReceiveChannel<CeilDescriptionReady>

    fun openCeilDescriptionRequestedSubscription(): ReceiveChannel<CeilState>

    fun descriptionReady(ceilState: CeilState, ceilInfo: CeilInfo)

    fun requestDescription(ceilState: CeilState)
}