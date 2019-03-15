package com.github.insanusmokrassar.PsychomatrixBase.domain.UseCases

import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.Psychomatrix
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.channels.ReceiveChannel
import org.joda.time.DateTime
import java.util.*

interface CalculatePsychomatrixByDateUseCase {
    suspend fun calculate(date: Long): Deferred<Psychomatrix>
    suspend fun calculate(date: DateTime): Deferred<Psychomatrix>
    suspend fun calculate(date: Date): Deferred<Psychomatrix>

    suspend fun openPsychomatrixCreatedSubscription(): ReceiveChannel<Psychomatrix>
}