package br.com.joaoov.ui.function

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import br.com.joaoov.R
import br.com.joaoov.data.local.function.Function
import br.com.joaoov.ext.formatFirstChar
import br.com.joaoov.ext.gone
import br.com.joaoov.ext.show
import kotlinx.android.synthetic.main.item_function.view.*

class FunctionListAdapter(
    private val list: MutableList<Function> = mutableListOf(),
    private val onClick: (Function) -> Unit,
    private val onEditClick: (Function) -> Unit,
    private val onDuplicateClick: (Function) -> Unit,
    private val onMoveClick: (Function) -> Unit,
    private val onDeleteClick: (Function) -> Unit,
) : RecyclerView.Adapter<FunctionListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Function) {
            with(itemView) {
                textViewFirstLetter.text = item.name.formatFirstChar()
                textViewFunction.text = item.name
                textViewDate.text = item.date
                textViewDescription.text = item.description
                textViewtextViewWorkday.text = item.workday
                textViewtextViewQuantity.text = item.quantity.toString()
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
                imageViewMore.setOnClickListener {
                    showMenu(context, imageViewMore, item)
                }
            }
        }
    }

    private fun showMenu(context: Context, anchor: View, item: Function) {
        PopupMenu(context, anchor).apply {
            menuInflater.inflate(R.menu.menu_generic_item, menu)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.actionEdit -> onEditClick(item)
                    R.id.actionDuplicate -> onDuplicateClick(item)
                    R.id.actionMove -> onMoveClick(item)
                    R.id.actionDelete -> onDeleteClick(item)
                }
                true
            }
            menu.findItem(R.id.actionDuplicate).isVisible = true
            menu.findItem(R.id.actionMove).isVisible = true
            show()
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
