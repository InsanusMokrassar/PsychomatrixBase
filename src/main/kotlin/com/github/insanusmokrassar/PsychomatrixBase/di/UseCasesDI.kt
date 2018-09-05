package com.github.insanusmokrassar.PsychomatrixBase.di

import com.github.insanusmokrassar.PsychomatrixBase.domain.UseCases.CalculatePsychomatrixByDateUseCase
import com.github.insanusmokrassar.PsychomatrixBase.domain.UseCases.ModifyPsychomatrixUseCase

interface UseCasesDI : EntitiesDI {
    val calculatePsychomatrixByDateUseCase: CalculatePsychomatrixByDateUseCase
    val modifyPsychomatrixUseCase: ModifyPsychomatrixUseCase
}