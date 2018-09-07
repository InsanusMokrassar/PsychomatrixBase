package com.github.insanusmokrassar.PsychomatrixBase

import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.MutablePsychomatrix
import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.operations.TwoGrowFour
import kotlinx.coroutines.experimental.runBlocking
import org.joda.time.DateTime

fun main(args: Array<String>) {
    val psychomatrix = MutablePsychomatrix(DateTime.now().withDate(2022, 12, 22))
    runBlocking {
        println(psychomatrix.availableOperations.await())
    }
}
