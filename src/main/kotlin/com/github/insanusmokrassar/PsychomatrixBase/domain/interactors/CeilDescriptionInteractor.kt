package com.github.insanusmokrassar.PsychomatrixBase.domain.interactors

import com.github.insanusmokrassar.PsychomatrixBase.domain.UseCases.CeilDescriptionReady
import com.github.insanusmokrassar.PsychomatrixBase.domain.UseCases.CeilDescriptionUseCase
import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.CeilInfo
import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.CeilState
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.ReceiveChannel

class CeilDescriptionInteractor : CeilDescriptionUseCase {
    private val scope = CoroutineScope(Dispatchers.Default)
    private val ceilDescriptionReadyBroadcastChannel = BroadcastChannel<CeilDescriptionReady>(
        16
    )
    private val ceilDescriptionRequestedBroadcastChannel = BroadcastChannel<CeilState>(
        8
    )

    override fun openCeilDescriptionReadySubscription(): ReceiveChannel<CeilDescriptionReady> {
        return ceilDescriptionReadyBroadcastChannel.openSubscription()
    }

    override fun openCeilDescriptionRequestedSubscription(): ReceiveChannel<CeilState> {
        return ceilDescriptionRequestedBroadcastChannel.openSubscription()
    }

    override fun descriptionReady(ceilState: CeilState, ceilInfo: CeilInfo) {
        scope.launch {
            ceilDescriptionReadyBroadcastChannel.send(ceilState to ceilInfo)
        }
    }

    override fun requestDescription(ceilState: CeilState) {
        scope.launch {
            ceilDescriptionRequestedBroadcastChannel.send(
                ceilState
            )
        }
    }
}