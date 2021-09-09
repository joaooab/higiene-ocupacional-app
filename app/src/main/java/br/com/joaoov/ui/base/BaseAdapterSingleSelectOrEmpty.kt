package br.com.joaoov.ui.base

import android.view.View

abstract class BaseAdapterSingleOrEmptySelect<DATA, VH : BaseViewHolderSingleOrEmptySelect<DATA>> :
    BaseAdapter<ItemSingleSelect<DATA>, VH>()

abstract class BaseViewHolderSingleOrEmptySelect<DATA>(itemView: View) :
    BaseViewHolder<ItemSingleSelect<DATA>>(itemView) {

    override fun bind(
        item: ItemSingleSelect<DATA>,
        baseAdapter: BaseAdapter<ItemSingleSelect<DATA>, *>
    ) {
        with(itemView) {
            setOnClickListener {
                baseAdapter.items.forEach { it.isSelected = false }
                item.isSelected = !isSelected

                if (item.isSelected) {
                    baseAdapter.onClick?.invoke(item)
                } else {
                    baseAdapter.onClick?.invoke(null)
                }

                baseAdapter.notifyDataSetChanged()
            }

            isSelected = item.isSelected
            bind(item)
        }
    }

    abstract fun bind(item: ItemSingleSelect<DATA>)
}
