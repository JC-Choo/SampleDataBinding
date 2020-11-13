package dev.chu.databindingsample

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.toast(message: String, duration: Int = 0) =
    Toast.makeText(this, message, duration).show()

fun Context.toast(@StringRes res: Int, duration: Int = 0) =
    Toast.makeText(this, res, duration).show()