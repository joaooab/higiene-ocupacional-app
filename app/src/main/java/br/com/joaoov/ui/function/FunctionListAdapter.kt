package br.com.joaoov.ui.function

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import androidx.recyclerview.widget.RecyclerView
import br.com.joaoov.R
import br.com.joaoov.data.function.Function
import br.com.joaoov.ext.gone
import br.com.joaoov.ext.show
import kotlinx.android.synthetic.main.item_ambient.view.constraintLayoutDetail
import kotlinx.android.synthetic.main.item_ambient.view.imageViewArrowDetail
import kotlinx.android.synthetic.main.item_company.view.textViewDate
import kotlinx.android.synthetic.main.item_function.view.*

class FunctionListAdapter(
    private val list: MutableList<Function> = mutableListOf(),
    private val onClick: (Function) -> Unit
) : RecyclerView.Adapter<FunctionListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Function) {
            with(itemView) {
                textViewFunction.text = item.name
                textViewDate.text = item.date
                textViewDescription.text = item.description
                textViewtextViewWorkday.text = item.workday
                setOnClickListener {
                    onClick(item)
                }
                imageViewArrowDetail.setOnClickListener {
                    item.showDetail = !item.showDetail
                    if (item.showDetail) {
                        rotate(it, 180f, 0f)
                        constraintLayoutDetail.show()
                    } else {
                        rotate(it, 0f, 180f)
                        constraintLayoutDetail.gone()
                    }
                }
            }
        }
    }

    private fun rotate(view: View, fromDegrees: Float, toDegress: Float) {
        val rotateAnimation = RotateAnimation(
            fromDegrees,
            toDegress,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        ).apply {
            interpolator = DecelerateInterpolator()
            repeatCount = 0
            duration = android.R.attr.animationDuration.toLong()
            fillAfter = true
        }
        view.startAnimation(rotateAnimation)
    }

    fun refresh(funcitons: List<Function>) {
        list.clear()
        list.addAll(funcitons)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_function,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

}
