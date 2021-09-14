package br.com.joaoov.ui.departament

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import br.com.joaoov.R
import br.com.joaoov.data.local.departament.Departament
import br.com.joaoov.ext.formatFirstChar
import kotlinx.android.synthetic.main.item_departament.view.*

class DepartamentListAdapter(
    private val list: MutableList<Departament> = mutableListOf(),
    private val onClick: (Departament) -> Unit,
    private val onEditClick: (Departament) -> Unit,
    private val onDuplicateClick: (Departament) -> Unit,
    private val onMoveClick: (Departament) -> Unit,
    private val onDeleteClick: (Departament) -> Unit,
) : RecyclerView.Adapter<DepartamentListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Departament) {
            with(itemView) {
                textViewDepartament.text = item.name
                textViewDate.text = item.date
                setOnClickListener {
                    onClick(item)
                }
                imageViewMore.setOnClickListener {
                    showMenu(context, imageViewMore, item)
                }
            }
        }
    }

    private fun showMenu(context: Context, anchor: View, item: Departament) {
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
