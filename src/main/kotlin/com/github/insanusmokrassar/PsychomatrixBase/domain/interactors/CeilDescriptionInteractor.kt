package com.github.insanusmokrassar.PsychomatrixBase.domain.interactors

import com.github.insanusmokrassar.PsychomatrixBase.domain.UseCases.CeilDescriptionReady
import com.github.insanusmokrassar.PsychomatrixBase.domain.UseCases.CeilDescriptionUseCase
import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.CeilInfo
import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.CeilState
import com.github.insanusmokrassar.PsychomatrixBase.utils.extensions.SUBSCRIPTIONS_EXTRA_SMALL
import com.github.insanusmokrassar.PsychomatrixBase.utils.extensions.SUBSCRIPTIONS_SMALL
import kotlinx.coroutines.experimental.channels.BroadcastChannel
import kotlinx.coroutines.experimental.channels.ReceiveChannel
import kotlinx.coroutines.experimental.launch

class CeilDescriptionInteractor : CeilDescriptionUseCase {
    private val ceilDescriptionReadyBroadcastChannel = BroadcastChannel<CeilDescriptionReady>(
        SUBSCRIPTIONS_SMALL
    )
    private val ceilDescriptionRequestedBroadcastChannel = BroadcastChannel<CeilState>(
        SUBSCRIPTIONS_EXTRA_SMALL
    )

    override fun openCeilDescriptionReadySubscription(): ReceiveChannel<CeilDescriptionReady> {
        return ceilDescriptionReadyBroadcastChannel.openSubscription()
    }

    override fun openCeilDescriptionRequestedSubscription(): ReceiveChannel<CeilState> {
        return ceilDescriptionRequestedBroadcastChannel.openSubscription()
    }

    override fun descriptionReady(ceilState: CeilState, ceilInfo: CeilInfo) {
        launch {
            ceilDescriptionReadyBroadcastChannel.send(ceilState to ceilInfo)
        }
    }

    override fun requestDescription(ceilState: CeilState) {
        launch {
            ceilDescriptionRequestedBroadcastChannel.send(
                ceilState
            )
        }
    }
}