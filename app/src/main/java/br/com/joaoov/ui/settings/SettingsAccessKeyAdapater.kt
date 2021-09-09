package br.com.joaoov.ui.settings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.joaoov.R
import br.com.joaoov.ui.base.BaseAdapter
import br.com.joaoov.ui.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_linked_user.view.*

class SettingsAccessKeyAdapater(private val onDeleteClick: (String) -> Unit) :
    BaseAdapter<String, SettingsAccessKeyAdapater.ViewHolder>() {

    inner class ViewHolder(itemView: View) : BaseViewHolder<String>(itemView) {
        override fun bind(item: String, baseAdapter: BaseAdapter<String, *>) {
            with(itemView) {
                textLinkedUser.text = item
                imageViewDeleteLinkedUser.setOnClickListener {
                    onDeleteClick(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_linked_user, parent, false)
        return ViewHolder(view)
    }

}