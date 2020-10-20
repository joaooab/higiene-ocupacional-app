package br.com.joaoov

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_path.view.*
import java.util.*

class MainPathAdapter(private val list: Stack<Path> = Stack()) :
    RecyclerView.Adapter<MainPathAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(path: Path) {
            with(itemView) {
                textViewPath.text = path.title
                textViewPath.setCompoundDrawablesWithIntrinsicBounds(path.icon, 0, 0, 0)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_path, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val path = list.elementAt(position)
        holder.bind(path)
    }

    fun add(path: Path) {
        if (list.any { it.type == path.type }) {
            return
        }
        list.add(path)
        notifyDataSetChanged()
    }

    fun remove() {
        list.pop()
        notifyDataSetChanged()
    }
}

data class Path(
    val type: String,
    val icon: Int,
    val title: String
) {
    companion object {
        const val COMPANY_TYPE = "COMPANY"
        const val COMPANY_ICON = R.drawable.ic_company
        const val DEPARTAMENT_TYPE = "DEPARTAMENT"
        const val DEPARTAMENT_ICON = R.drawable.ic_departament
        const val AMBIENT_TYPE = "AMBIENT_TYPE"
        const val AMBIENT_TYPE_ICON = R.drawable.ic_ambient
    }
}