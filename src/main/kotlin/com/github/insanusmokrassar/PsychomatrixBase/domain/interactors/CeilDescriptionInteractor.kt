package com.github.insanusmokrassar.PsychomatrixBase.domain.interactors

import com.github.insanusmokrassar.PsychomatrixBase.domain.UseCases.CeilDescriptionReady
import com.github.insanusmokrassar.PsychomatrixBase.domain.UseCases.CeilDescriptionUseCase
import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.CeilDescription
import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.PsychomatrixCeilInfo
import com.github.insanusmokrassar.PsychomatrixBase.utils.extensions.SUBSCRIPTIONS_EXTRA_SMALL
import com.github.insanusmokrassar.PsychomatrixBase.utils.extensions.SUBSCRIPTIONS_SMALL
import kotlinx.coroutines.experimental.channels.BroadcastChannel
import kotlinx.coroutines.experimental.channels.ReceiveChannel
import kotlinx.coroutines.experimental.launch

class CeilDescriptionInteractor : CeilDescriptionUseCase {
    private val ceilDescriptionReadyBroadcastChannel = BroadcastChannel<CeilDescriptionReady>(
        SUBSCRIPTIONS_SMALL
    )
    private val ceilDescriptionRequestedBroadcastChannel = BroadcastChannel<PsychomatrixCeilInfo>(
        SUBSCRIPTIONS_EXTRA_SMALL
    )

    override fun openCeilDescriptionReadySubscription(): ReceiveChannel<CeilDescriptionReady> {
        return ceilDescriptionReadyBroadcastChannel.openSubscription()
    }

    override fun openCeilDescriptionRequestedSubscription(): ReceiveChannel<PsychomatrixCeilInfo> {
        return ceilDescriptionRequestedBroadcastChannel.openSubscription()
    }

    override fun descriptionReady(psychomatrixCeilInfo: PsychomatrixCeilInfo, ceilDescription: CeilDescription) {
        launch {
            ceilDescriptionReadyBroadcastChannel.send(psychomatrixCeilInfo to ceilDescription)
        }
    }

    override fun requestDescription(psychomatrixCeilInfo: PsychomatrixCeilInfo) {
        launch {
            ceilDescriptionRequestedBroadcastChannel.send(
                psychomatrixCeilInfo
            )
        }
    }
}