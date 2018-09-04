package com.github.insanusmokrassar.PsychomatrixBase.di.realisations

import com.github.insanusmokrassar.PsychomatrixBase.di.PresentationLayerDI
import com.github.insanusmokrassar.PsychomatrixBase.di.UIDI

class UIDIImpl(
    presentationLayerDI: PresentationLayerDI
) : UIDI, PresentationLayerDI by presentationLayerDI {

}
