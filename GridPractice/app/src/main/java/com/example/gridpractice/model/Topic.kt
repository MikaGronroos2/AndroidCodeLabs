package com.example.gridpractice.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Topic(
    @StringRes val name: Int,
    val numOfCourses: Int,
    @DrawableRes val imageId: Int,
)
