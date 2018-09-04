package com.github.insanusmokrassar.PsychomatrixBase.di.realisations

import com.github.insanusmokrassar.PsychomatrixBase.di.UseCasesDI
import com.github.insanusmokrassar.PsychomatrixBase.domain.UseCases.CalculatePsychomatrixByDate
import com.github.insanusmokrassar.PsychomatrixBase.domain.interactors.CalculatePsychomatrixByDateInteractor

open class UseCasesDIImpl : UseCasesDI, EntitiesDIImpl() {

    override val calculatePsychomatrixByDate: CalculatePsychomatrixByDate by lazy {
        CalculatePsychomatrixByDateInteractor()
    }

}
