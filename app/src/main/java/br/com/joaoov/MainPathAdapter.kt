package br.com.joaoov

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_path.view.*
import java.util.*

class MainPathAdapter(private val stack: Stack<Path> = Stack()) :
    RecyclerView.Adapter<MainPathAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(path: Path) {
            with(itemView) {
                textViewPath.text = path.title
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_path, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = stack.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val path = stack.elementAt(position)
        holder.bind(path)
    }

    fun add(path: Path) {
        if (stack.any { it.type == path.type }) {
            return
        }
        stack.add(path)
        notifyDataSetChanged()
    }

    fun remove() {
        stack.pop()
        notifyDataSetChanged()
    }

    fun refresh() {
        stack.peek()
    }
}

data class Path(
    val type: String,
    val title: String,
) {
    companion object {
        const val COMPANY_PATH = "COMPANY_PATH"
        const val DEPARTAMENT_PATH = "DEPARTAMENT_PATH"
        const val AMBIENT_PATH = "AMBIENT_PATH"
        const val FUNCTION_PATH = "FUNCTION_PATH"
    }
}