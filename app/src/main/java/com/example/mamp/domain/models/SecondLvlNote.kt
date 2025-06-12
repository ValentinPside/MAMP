package com.example.mamp.domain.models

data class SecondLvlNote(
    val id: Int,
    val parentId: Int,
    val name: String,
    val fileAddress: String
)
