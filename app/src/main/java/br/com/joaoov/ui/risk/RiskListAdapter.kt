package br.com.joaoov.ui.risk

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.joaoov.R
import br.com.joaoov.data.local.risk.Risk
import kotlinx.android.synthetic.main.item_risk.view.*

class RiskListAdapter(
    private val list: MutableList<Risk> = mutableListOf(),
    private val onClick: (Risk) -> Unit,
    private val onEditClick: (Risk) -> Unit,
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
                textViewRisk.text = item.riskFactor
            }
        }
    }

    override fun getItemCount(): Int = list.size

    fun refresh(risks: List<Risk>) {
        list.clear()
        list.addAll(risks)
        notifyDataSetChanged()
    }
}