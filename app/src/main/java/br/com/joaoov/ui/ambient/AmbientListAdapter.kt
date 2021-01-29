package br.com.joaoov.ui.ambient

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
import br.com.joaoov.data.local.ambient.Ambient
import br.com.joaoov.ext.*
import kotlinx.android.synthetic.main.item_ambient.view.*

class AmbientListAdapter(
    private val list: MutableList<Ambient> = mutableListOf(),
    private val onClick: (Ambient) -> Unit,
    private val onEditClick: (Ambient) -> Unit,
    private val onDeleteClick: (Ambient) -> Unit,
) : RecyclerView.Adapter<AmbientListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Ambient) {
            with(itemView) {
                textViewFirstLetter.text = item.name.formatFirstChar()
                textViewDate.text = item.date
                textViewLocal.text = item.name
                textViewArea.text = getString(
                    context,
                    R.string.label_area,
                    item.area.formatToArea()
                )
                textViewHeight.text = getString(
                    context,
                    R.string.label_height,
                    item.height.formatToMeters()
                )
                textViewFloor.text = getString(
                    context,
                    R.string.label_floor,
                    item.floor
                )
                textViewRoof.text = getString(
                    context,
                    R.string.label_roof,
                    item.roof
                )
                textViewRoofTiles.text = getString(
                    context,
                    R.string.label_roof_tiles,
                    item.roofTiles
                )
                textViewNaturalLighting.text = getString(
                    context,
                    R.string.label_natural_lighting,
                    item.naturalLighting
                )
                textViewArtificialLighting.text = getString(
                    context,
                    R.string.label_artificial_lighting,
                    item.artificialLighting
                )
                textViewNaturalVentilation.text = getString(
                    context,
                    R.string.label_natural_ventilation,
                    item.naturalVentilation
                )
                textViewArtificialVentilation.text = getString(
                    context,
                    R.string.label_artificial_ventilation,
                    item.artificialVentilation
                )
                textViewWall.text = getString(
                    context,
                    R.string.label_wall,
                    item.wall
                )
                textViewWindow.text = getString(
                    context,
                    R.string.label_window,
                    item.window
                )
                textViewCeiling.text = getString(
                    context,
                    R.string.label_ceiling,
                    item.ceiling
                )

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

        private fun showMenu(context: Context, anchor: View, item: Ambient) {
            PopupMenu(context, anchor).apply {
                menuInflater.inflate(R.menu.menu_generic_item, menu)
                setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.actionEdit -> onEditClick(item)
                        R.id.actionDelete -> onDeleteClick(item)
                    }
                    true
                }
                show()
            }
        }

        private fun getString(context: Context, resource: Int, field: String): String {
            val value = if (field.isEmpty()) "-" else field
            return String.format(context.getString(resource), value)
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
    }

    fun refresh(ambients: List<Ambient>) {
        list.clear()
        list.addAll(ambients)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_ambient,
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
