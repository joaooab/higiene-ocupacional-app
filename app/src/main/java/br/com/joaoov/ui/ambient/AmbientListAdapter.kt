package br.com.joaoov.ui.ambient

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.joaoov.R
import br.com.joaoov.data.Ambient
import kotlinx.android.synthetic.main.item_ambient.view.*

class AmbientListAdapter(
    private val list: MutableList<Ambient> = mutableListOf(),
    private val onClick: (Ambient) -> Unit
) :
    RecyclerView.Adapter<AmbientListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Ambient) {
            with(itemView) {
                textViewLocal.text = item.local
                textViewArea.text = item.getAreaFormat()
                textViewData.text = item.data
                setOnClickListener {
                    onClick(item)
                }
            }
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
