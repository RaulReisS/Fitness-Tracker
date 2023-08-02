package br.com.raulreis.fitnesstracker

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class MainItem(
    val id: Int,
    @DrawableRes val drawableID: Int,
    @StringRes val textStringId: Int,
    val color: Int
)
