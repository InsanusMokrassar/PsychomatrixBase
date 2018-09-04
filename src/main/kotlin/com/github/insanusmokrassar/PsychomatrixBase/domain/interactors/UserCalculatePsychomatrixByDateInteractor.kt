package com.github.insanusmokrassar.PsychomatrixBase.domain.interactors

import com.github.insanusmokrassar.PsychomatrixBase.domain.UseCases.UserCalculatePsychomatrixByDate
import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.Psychomatrix
import com.github.insanusmokrassar.PsychomatrixBase.utils.extensions.SUBSCRIPTIONS_MEDIUM
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.channels.BroadcastChannel
import kotlinx.coroutines.experimental.channels.ReceiveChannel
import org.joda.time.DateTime
import java.util.*

class UserCalculatePsychomatrixByDateInteractor : UserCalculatePsychomatrixByDate {
    private val psychomatrixCreatedBroadcast = BroadcastChannel<Psychomatrix>(SUBSCRIPTIONS_MEDIUM)

    override suspend fun calculate(date: Long): Deferred<Psychomatrix> {
        return calculate(DateTime(date))
    }

    override suspend fun calculate(date: DateTime): Deferred<Psychomatrix> {
        return async {
            Psychomatrix(date).also {
                psychomatrixCreatedBroadcast.send(it)
            }
        }
    }

    override suspend fun calculate(date: Date): Deferred<Psychomatrix> {
        return calculate(DateTime(date))
    }

    override suspend fun openPsychomatrixCreatedSubscription(): ReceiveChannel<Psychomatrix> {
        return psychomatrixCreatedBroadcast.openSubscription()
    }
}