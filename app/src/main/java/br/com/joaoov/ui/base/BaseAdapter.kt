package br.com.joaoov.ui.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<DATA, VH : BaseViewHolder<DATA>> :
    RecyclerView.Adapter<BaseViewHolder<DATA>>() {

    var onClick: ((DATA?) -> Unit)? = null

    open var items = mutableListOf<DATA>()
        set(value)  {
            this.items.clear()
            this.items.addAll(value)
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: BaseViewHolder<DATA>, position: Int) {
        holder.bind(items[position], this)
    }

}

abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: T, baseAdapter: BaseAdapter<T, *>)
}
