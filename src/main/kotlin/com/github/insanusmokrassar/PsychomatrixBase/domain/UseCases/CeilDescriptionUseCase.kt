package com.github.insanusmokrassar.PsychomatrixBase.domain.UseCases

import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.CeilDescription
import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.PsychomatrixCeilInfo
import kotlinx.coroutines.experimental.channels.ReceiveChannel

typealias CeilDescriptionReady = Pair<PsychomatrixCeilInfo, CeilDescription>

interface CeilDescriptionUseCase {
    fun openCeilDescriptionReadySubscription(): ReceiveChannel<CeilDescriptionReady>

    fun openCeilDescriptionRequestedSubscription(): ReceiveChannel<PsychomatrixCeilInfo>

    fun descriptionReady(psychomatrixCeilInfo: PsychomatrixCeilInfo, ceilDescription: CeilDescription)

    fun requestDescription(psychomatrixCeilInfo: PsychomatrixCeilInfo)
}