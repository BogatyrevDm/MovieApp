package com.example.movieapp.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.ShowSnackBar(
    text: String,
    actionText: String,
    action: (View) -> Unit,
    length: Int = Snackbar.LENGTH_INDEFINITE
) {
    Snackbar.make(this, text, length).setAction(actionText, action).show()
}