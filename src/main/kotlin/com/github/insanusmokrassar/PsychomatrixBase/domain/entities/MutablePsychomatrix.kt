package com.github.insanusmokrassar.PsychomatrixBase.domain.entities

import org.joda.time.DateTime

class MutablePsychomatrix(date: DateTime) : Psychomatrix(date) {
    private var mutableNumbers = super.numbers

    override val numbers: ByteArray
        get() = mutableNumbers


}
