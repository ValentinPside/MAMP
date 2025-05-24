package com.example.mamp.domain.models

import java.time.LocalDate

data class FirstLvlNote(
    val id: Int,
    val name: String,
    val targetDate: LocalDate,
    val fileAddress: String
)
