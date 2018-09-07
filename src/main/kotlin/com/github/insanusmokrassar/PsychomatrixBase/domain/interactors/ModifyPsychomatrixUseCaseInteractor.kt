package com.github.insanusmokrassar.PsychomatrixBase.domain.interactors

import com.github.insanusmokrassar.PsychomatrixBase.domain.UseCases.ModifyPsychomatrixUseCase
import com.github.insanusmokrassar.PsychomatrixBase.domain.UseCases.PsychomatrixOperationIsConvert
import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.MutablePsychomatrix
import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.Psychomatrix
import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.operations.Operation
import com.github.insanusmokrassar.PsychomatrixBase.utils.extensions.SUBSCRIPTIONS_MEDIUM
import kotlinx.coroutines.experimental.channels.BroadcastChannel
import kotlinx.coroutines.experimental.channels.ReceiveChannel
import kotlinx.coroutines.experimental.launch

class ModifyPsychomatrixUseCaseInteractor : ModifyPsychomatrixUseCase {
    private val currentPsychomatrixes: MutableList<MutablePsychomatrix> = ArrayList()

    private val psychomatrixChangedBroadcastChannel = BroadcastChannel<PsychomatrixOperationIsConvert>(SUBSCRIPTIONS_MEDIUM)

    override fun openPsychomatrixChangedSubscription(): ReceiveChannel<PsychomatrixOperationIsConvert> {
        return psychomatrixChangedBroadcastChannel.openSubscription()
    }

    override fun makeConvert(psychomatrix: MutablePsychomatrix, operation: Operation): Boolean {
        return asMutablePsychomatrix(psychomatrix).applyConvert(operation).also {
            if (it) {
                launch {
                    psychomatrixChangedBroadcastChannel.send(psychomatrix to (operation to true))
                }
            }
        }
    }

    override fun makeInvert(psychomatrix: MutablePsychomatrix, operation: Operation): Boolean {
        return asMutablePsychomatrix(psychomatrix).applyInvert(operation).also {
            launch {
                psychomatrixChangedBroadcastChannel.send(psychomatrix to (operation to false))
            }
        }
    }

    override fun getConverts(psychomatrix: Psychomatrix): List<Operation> {
        return asMutablePsychomatrix(psychomatrix).availableConverts
    }

    override fun getInverts(psychomatrix: Psychomatrix): List<Operation> {
        return asMutablePsychomatrix(psychomatrix).availableInverts
    }

    override fun getPsychomatrixHistory(psychomatrix: Psychomatrix): List<Operation> {
        return asMutablePsychomatrix(psychomatrix).operationsHistory
    }

    private fun asMutablePsychomatrix(psychomatrix: Psychomatrix): MutablePsychomatrix {
        return currentPsychomatrixes.firstOrNull {
            it == psychomatrix
        } ?: psychomatrix.asMutablePsychomatrix.also {
            currentPsychomatrixes.add(it)
        }
    }
}