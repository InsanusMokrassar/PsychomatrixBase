package com.github.insanusmokrassar.PsychomatrixBase.di

import com.github.insanusmokrassar.PsychomatrixBase.domain.UseCases.CalculatePsychomatrixByDateUseCase

interface UseCasesDI : EntitiesDI {
    val calculatePsychomatrixByDateUseCase: CalculatePsychomatrixByDateUseCase
}