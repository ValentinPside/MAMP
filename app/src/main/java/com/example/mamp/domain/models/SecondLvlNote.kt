package com.example.mamp.domain.models

import java.time.LocalDate

data class SecondLvlNote(
    val id: Int,
    val parenId: Int,
    val name: String,
    val targetDate: LocalDate,
    val fileAddress: String
)
