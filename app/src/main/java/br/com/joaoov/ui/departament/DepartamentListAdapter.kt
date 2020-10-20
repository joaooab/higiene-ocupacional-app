package br.com.joaoov.ui.departament

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.joaoov.R
import br.com.joaoov.data.departament.Departament
import kotlinx.android.synthetic.main.item_company.view.*

class DepartamentListAdapter(
    private val list: MutableList<Departament> = mutableListOf(),
    private val onClick: (Departament) -> Unit
) : RecyclerView.Adapter<DepartamentListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Departament) {
            with(itemView) {
                textViewCompany.text = item.name
                textViewDate.text = item.date
                setOnClickListener {
                    onClick(item)
                }
            }
        }
    }

    fun refresh(departaments: List<Departament>) {
        list.clear()
        list.addAll(departaments)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_departament,
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
