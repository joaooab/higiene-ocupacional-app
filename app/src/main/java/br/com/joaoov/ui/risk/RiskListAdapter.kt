package br.com.joaoov.ui.risk

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
import br.com.joaoov.data.local.risk.Risk
import br.com.joaoov.ext.formatFirstChar
import br.com.joaoov.ext.gone
import br.com.joaoov.ext.show
import kotlinx.android.synthetic.main.item_risk.view.*

class RiskListAdapter(
    private val list: MutableList<Risk> = mutableListOf(),
    private val onClick: (Risk) -> Unit,
    private val onEditClick: (Risk) -> Unit,
    private val onDuplicateClick: (Risk) -> Unit,
    private val onMoveClick: (Risk) -> Unit,
    private val onDeleteClick: (Risk) -> Unit,
) : RecyclerView.Adapter<RiskListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_risk, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Risk) {
            with(itemView) {
                textViewFirstLetter.text = item.agent.formatFirstChar()
                textViewAgent.text = item.agent
                textViewDate.text = item.date
                textViewRiskFactor.text = getString(
                    context,
                    R.string.label_risk_factor,
                    item.agent
                )
                textViewGeneratingSource.text = getString(
                    context,
                    R.string.label_generating_source,
                    item.generatedSource
                )
                textViewIntensityConcentration.text = getString(
                    context,
                    R.string.label_intensity_concentration,
                    item.intensity
                )
                textViewActionLevel.text = getString(
                    context,
                    R.string.label_action_level,
                    item.actionLevel
                )
                textViewNR15.text = getString(
                    context,
                    R.string.label_nr15,
                    item.tolerance.NR15
                )
                textViewACGHI.text = getString(
                    context,
                    R.string.label_acgih,
                    item.tolerance.ACGIH
                )
                textViewEliminationNeutralization.text = getString(
                    context,
                    R.string.label_elimination_neutralization,
                    item.eliminationNeutralization
                )
                textViewExposure.text = getString(
                    context,
                    R.string.label_expousure,
                    item.exposure
                )
                textViewMetodology.text = getString(
                    context,
                    R.string.label_source_methodology,
                    item.methodology
                )
                textViewDegreeOfRisk.text = getString(
                    context,
                    R.string.label_degree_of_risk,
                    item.degreeOfRisk
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

        private fun showMenu(context: Context, anchor: View, item: Risk) {
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

        private fun getString(context: Context, resource: Int, field: String): String {
            val value = if (field.isEmpty()) "-" else field
            return String.format(context.getString(resource), value)
        }
    }

    override fun getItemCount(): Int = list.size

    fun refresh(risks: List<Risk>) {
        list.clear()
        list.addAll(risks)
        notifyDataSetChanged()
    }
}