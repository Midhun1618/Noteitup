package com.voxcom.noteitup

import java.io.Serializable

data class Task(
    val title: String,
    val label: String
) : Serializable
