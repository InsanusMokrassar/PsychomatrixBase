package com.github.insanusmokrassar.PsychomatrixBase.presentation.presenters.DefaultRealisations

import com.github.insanusmokrassar.PsychomatrixBase.domain.UseCases.ModifyPsychomatrixUseCase
import com.github.insanusmokrassar.PsychomatrixBase.domain.UseCases.PsychomatrixOperationIsConvert
import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.MutablePsychomatrix
import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.Psychomatrix
import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.operations.*
import com.github.insanusmokrassar.PsychomatrixBase.presentation.presenters.ModifyPsychomatrixPresenter
import com.github.insanusmokrassar.PsychomatrixBase.utils.extensions.subscribe
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.channels.ReceiveChannel
import kotlinx.coroutines.experimental.launch

class ModifyPsychomatrixPresenterImpl(
    private val modifyPsychomatrixUseCase: ModifyPsychomatrixUseCase
) : ModifyPsychomatrixPresenter {

    private val availableConverts = HashMap<Psychomatrix, List<Operation>>()
    private val availableInverts = HashMap<MutablePsychomatrix, List<Operation>>()

    init {
        openPsychomatrixChangedSubscription().subscribe {
            if (it.second.second) {
                updateConvertsOfPsychomatrix(it.first)
            } else {
                updateInvertsOfPsychomatrix(it.first)
            }
        }
    }

    override fun openPsychomatrixChangedSubscription(): ReceiveChannel<PsychomatrixOperationIsConvert> {
        return modifyPsychomatrixUseCase.openPsychomatrixChangedSubscription()
    }

    override suspend fun tryToDoOperation(psychomatrix: MutablePsychomatrix, operation: Operation): Deferred<Boolean> {
        return modifyPsychomatrixUseCase.makeConvert(psychomatrix, operation)
    }

    override suspend fun twoGrowFourAvailable(psychomatrix: Psychomatrix): Deferred<Boolean> {
        return async {
            (availableConverts[psychomatrix] ?: updateConvertsOfPsychomatrix(psychomatrix)).contains(TwoGrowFour)
        }
    }

    override suspend fun fourGrowTwoAvailable(psychomatrix: Psychomatrix): Deferred<Boolean> {
        return async {
            (availableConverts[psychomatrix] ?: updateConvertsOfPsychomatrix(psychomatrix)).contains(FourGrowTwo)
        }
    }

    override suspend fun oneGrowEightAvailable(psychomatrix: Psychomatrix): Deferred<Boolean> {
        return async {
            (availableConverts[psychomatrix] ?: updateConvertsOfPsychomatrix(psychomatrix)).contains(OneGrowEight)
        }
    }

    override suspend fun eightGrowOneAvailable(psychomatrix: Psychomatrix): Deferred<Boolean> {
        return async {
            (availableConverts[psychomatrix] ?: updateConvertsOfPsychomatrix(psychomatrix)).contains(EightGrowOne)
        }
    }

    override suspend fun sixGrowSevenAvailable(psychomatrix: Psychomatrix): Deferred<Boolean> {
        return async {
            (availableConverts[psychomatrix] ?: updateConvertsOfPsychomatrix(psychomatrix)).contains(SixGrowSeven)
        }
    }

    override suspend fun sevenGrowSixAvailable(psychomatrix: Psychomatrix): Deferred<Boolean> {
        return async {
            (availableConverts[psychomatrix] ?: updateConvertsOfPsychomatrix(psychomatrix)).contains(SevenGrowSix)
        }
    }

    override suspend fun fiveGrowNineAvailable(psychomatrix: Psychomatrix): Deferred<Boolean> {
        return async {
            (availableConverts[psychomatrix] ?: updateConvertsOfPsychomatrix(psychomatrix)).contains(FiveGrowNine)
        }
    }

    override suspend fun nineGrowFiveAvailable(psychomatrix: Psychomatrix): Deferred<Boolean> {
        return async {
            (availableConverts[psychomatrix] ?: updateConvertsOfPsychomatrix(psychomatrix)).contains(NineGrowFive)
        }
    }

    override suspend fun customGrowAvailable(psychomatrix: Psychomatrix, number: Byte): Deferred<Boolean> {
        return async {
            (availableConverts[psychomatrix] ?: updateConvertsOfPsychomatrix(psychomatrix)).firstOrNull {
                it is GrowCustom && it.number == number
            } != null
        }
    }

    override suspend fun rollback(psychomatrix: MutablePsychomatrix, operations: Int): Job {
        return launch {
            history(psychomatrix).await().let {
                it.subList(it.size - operations, it.size).asReversed()
            }.forEach {
                modifyPsychomatrixUseCase.makeInvert(psychomatrix, it)
            }
        }
    }

    override suspend fun history(psychomatrix: MutablePsychomatrix): Deferred<List<Operation>> {
        return async {
            availableInverts[psychomatrix] ?: updateInvertsOfPsychomatrix(psychomatrix)
        }
    }

    private suspend fun updateConvertsOfPsychomatrix(psychomatrix: Psychomatrix): List<Operation> {
        return modifyPsychomatrixUseCase.getConverts(psychomatrix).await().also {
            availableConverts[psychomatrix] = it
        }
    }

    private suspend fun updateInvertsOfPsychomatrix(psychomatrix: MutablePsychomatrix): List<Operation> {
        return modifyPsychomatrixUseCase.getInverts(psychomatrix).await().also {
            availableInverts[psychomatrix] = it
        }
    }
}