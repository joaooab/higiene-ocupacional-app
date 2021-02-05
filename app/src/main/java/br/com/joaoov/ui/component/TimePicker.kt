package br.com.joaoov.ui.component

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.widget.TimePicker
import br.com.joaoov.ext.toHour
import br.com.joaoov.ext.toMinute

open class TimePickerCustom @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : TimePicker(context, attrs, defStyle) {

    private fun getHourValue() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        this.hour
    } else {
        currentHour
    }

    fun setHourValue(value: Int) = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        hour = value
    } else {
        currentHour = value
    }

    private fun getMinuteValue() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        minute
    } else {
        currentMinute
    }

    fun setMinuteValue(value: Int) = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        minute = value
    } else {
        currentMinute = value
    }

    fun getFormatedTime() = "${this.getHourValue().toHour()}:${this.getMinuteValue().toMinute()}"

    fun isGreaterThan(anotherTime: TimePickerCustom): Boolean {
        return when {
            this.getHourValue() > anotherTime.getHourValue() -> {
                true
            }
            this.getHourValue() == anotherTime.getHourValue() -> {
                this.getMinuteValue() > anotherTime.getMinuteValue()
            }
            else -> {
                false
            }
        }
    }

}