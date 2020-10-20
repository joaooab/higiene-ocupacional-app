package br.com.joaoov.ui.company

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.joaoov.R
import br.com.joaoov.data.company.Company
import kotlinx.android.synthetic.main.item_company.view.*

class CompanyListAdapter(
    private val list: MutableList<Company> = mutableListOf(),
    private val onClick: (Company) -> Unit
) : RecyclerView.Adapter<CompanyListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Company) {
            with(itemView) {
                textViewCompany.text = item.name
                textViewDate.text = item.date
                setOnClickListener {
                    onClick(item)
                }
            }
        }
    }

    fun refresh(companies: List<Company>) {
        list.clear()
        list.addAll(companies)
        notifyDataSetChanged()
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
