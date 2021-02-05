package br.com.joaoov.ui.component

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import br.com.joaoov.R
import br.com.joaoov.ext.formatFirstChar
import kotlinx.android.synthetic.main.item_dayofweek.view.*
import java.util.*

class DayOfWeekAdapter(private val itens: List<DayOfWeek> = DayOfWeek.values()) :
    RecyclerView.Adapter<DayOfWeekAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(dayOfWeek: DayOfWeek) {
            with(itemView) {
                textViewDayOfWeek.apply {
                    text = dayOfWeek.day.formatFirstChar()
                    setupBackground(this, dayOfWeek.selected)
                    setOnClickListener {
                        dayOfWeek.selected = !dayOfWeek.selected
                        setupBackground(this, dayOfWeek.selected)
                    }
                }
            }
        }

        private fun setupBackground(view: TextView, isSelected: Boolean) {
            if (isSelected) {
                view.background = ContextCompat.getDrawable(
                    view.context,
                    R.drawable.background_circle
                )
            } else {
                view.background = ContextCompat.getDrawable(
                    view.context,
                    R.drawable.background_circle_disable
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_dayofweek, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dayOfWeek = itens[position]
        holder.bind(dayOfWeek)
    }

    override fun getItemCount(): Int = itens.size

    fun getFormatedValeus(): String {
        val selectedItens = itens.filter { it.selected }
        return DayOfWeek.formatValues(selectedItens)
    }
}

data class DayOfWeek(
    val day: String,
    var selected: Boolean = false
) {

    companion object {
        private const val SUNDAY = "Domingo"
        private const val MONDAY = "Segunda"
        private const val TUESDAY = "Terça"
        private const val WEDNESDAY = "Quarta"
        private const val THURSDAY = "Quinta"
        private const val FRIDAY = "Sexta"
        private const val SATURDAY = "Sábado"

        private val days = listOf(
            DayOfWeek(SUNDAY),
            DayOfWeek(MONDAY, true),
            DayOfWeek(TUESDAY, true),
            DayOfWeek(WEDNESDAY, true),
            DayOfWeek(THURSDAY, true),
            DayOfWeek(FRIDAY, true),
            DayOfWeek(SATURDAY)
        )

        fun values() = days

        fun formatValues(days: List<DayOfWeek>): String = when {
            days.isEmpty() -> ""
            days.size == 1 -> days.first().day
            isSequencial(days) -> "De ${days.first().day} a ${days.last().day}"
            else -> days.joinToString { it.day.substring(0, 3) }
        }

        fun isSequencial(sublist: List<DayOfWeek>) = Collections.indexOfSubList(days, sublist) != -1
    }

}
