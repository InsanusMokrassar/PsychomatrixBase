package com.github.insanusmokrassar.PsychomatrixBase.di.realisations

import com.github.insanusmokrassar.PsychomatrixBase.di.EntitiesDI
import com.github.insanusmokrassar.PsychomatrixBase.di.UseCasesDI
import com.github.insanusmokrassar.PsychomatrixBase.domain.UseCases.CalculatePsychomatrixByDateUseCase
import com.github.insanusmokrassar.PsychomatrixBase.domain.UseCases.ModifyPsychomatrixUseCase
import com.github.insanusmokrassar.PsychomatrixBase.domain.interactors.CalculatePsychomatrixByDateUseCaseInteractor
import com.github.insanusmokrassar.PsychomatrixBase.domain.interactors.ModifyPsychomatrixUseCaseInteractor

open class UseCasesDIImpl(
    entitiesDI: EntitiesDI
) : UseCasesDI, EntitiesDI by entitiesDI {
    override val calculatePsychomatrixByDateUseCase: CalculatePsychomatrixByDateUseCase by lazy {
        CalculatePsychomatrixByDateUseCaseInteractor()
    }

    override val modifyPsychomatrixUseCase: ModifyPsychomatrixUseCase by lazy {
        ModifyPsychomatrixUseCaseInteractor()
    }
}
