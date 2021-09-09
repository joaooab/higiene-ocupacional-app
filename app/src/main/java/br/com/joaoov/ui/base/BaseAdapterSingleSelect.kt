package br.com.joaoov.ui.base

import android.view.View

abstract class BaseAdapterSingleSelect<DATA, VH : BaseViewHolderSingleSelect<DATA>> :
    BaseAdapter<ItemSingleSelect<DATA>, VH>() {

    override var items: MutableList<ItemSingleSelect<DATA>> = mutableListOf()
        set(value) {
            value.run {
                if (isNotEmpty() && !any { it.isSelected }) {
                    first().apply { isSelected = true }
                }
            }
            field = value
        }
}

abstract class BaseViewHolderSingleSelect<DATA>(itemView: View) :
    BaseViewHolder<ItemSingleSelect<DATA>>(itemView) {

    override fun bind(
        item: ItemSingleSelect<DATA>,
        baseAdapter: BaseAdapter<ItemSingleSelect<DATA>, *>
    ) {
        with(itemView) {
            setOnClickListener {
                baseAdapter.items.forEach { it.isSelected = false }
                item.isSelected = true
                baseAdapter.onClick?.invoke(item)
                baseAdapter.notifyDataSetChanged()
            }

            bind(item)
        }
    }

    abstract fun bind(item: ItemSingleSelect<DATA>)
}

data class ItemSingleSelect<DATA>(
    val data: DATA,
    override var isSelected: Boolean = false
) : SingleSelect

interface SingleSelect {
    var isSelected: Boolean
}

