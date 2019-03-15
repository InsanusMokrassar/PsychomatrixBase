package com.github.insanusmokrassar.PsychomatrixBase.domain.interactors

import com.github.insanusmokrassar.PsychomatrixBase.domain.UseCases.CalculatePsychomatrixByDateUseCase
import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.Psychomatrix
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.ReceiveChannel
import org.joda.time.DateTime
import java.util.*

class CalculatePsychomatrixByDateUseCaseInteractor : CalculatePsychomatrixByDateUseCase {
    private val scope = CoroutineScope(Dispatchers.Default)
    private val psychomatrixCreatedBroadcast = BroadcastChannel<Psychomatrix>(16)

    override suspend fun calculate(date: Long): Deferred<Psychomatrix> {
        return calculate(DateTime(date))
    }

    override suspend fun calculate(date: DateTime): Deferred<Psychomatrix> {
        return scope.async {
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