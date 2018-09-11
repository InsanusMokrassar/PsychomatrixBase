package com.github.insanusmokrassar.PsychomatrixBase.di

import com.github.insanusmokrassar.PsychomatrixBase.domain.UseCases.*

interface UseCasesDI : EntitiesDI {
    val calculatePsychomatrixByDateUseCase: CalculatePsychomatrixByDateUseCase
    val modifyPsychomatrixUseCase: ModifyPsychomatrixUseCase
    val ceilDescriptionUseCase: CeilDescriptionUseCase
}