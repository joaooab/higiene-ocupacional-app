package br.com.joaoov.ui.company

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.joaoov.R
import br.com.joaoov.data.Company
import kotlinx.android.synthetic.main.item_company.view.*

class CompanyListAdapter(
    private val list: MutableList<Company> = mutableListOf(),
    private val onClick: (Company) -> Unit
) :
    RecyclerView.Adapter<CompanyListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Company) {
            with(itemView) {
                try {
                    textViewFirstChar.text = item.name.trim().first().toUpperCase().toString()
                } catch (e: Exception) {
                    textViewFirstChar.text = "-"
                }
                textViewEmpresa.text = item.name
                textViewData.text = item.date
                setOnClickListener {
                    onClick(item)
                }
            }
        }
    }

    fun refresh(companies: List<Company>) {
        notifyItemRangeRemoved(0, list.size)
        list.clear()
        list.addAll(companies)
        notifyItemRangeInserted(0, list.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_company,
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
