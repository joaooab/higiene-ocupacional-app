package br.com.joaoov.ui.billing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import br.com.joaoov.R
import br.com.joaoov.ui.base.BaseAdapterSingleOrEmptySelect
import br.com.joaoov.ui.base.BaseViewHolderSingleOrEmptySelect
import br.com.joaoov.ui.base.ItemSingleSelect
import com.android.billingclient.api.SkuDetails
import kotlinx.android.synthetic.main.item_billing_plan.view.*

class BillingAdapter :
    BaseAdapterSingleOrEmptySelect<SkuDetails, BillingAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) :
        BaseViewHolderSingleOrEmptySelect<SkuDetails>(itemView) {

        override fun bind(item: ItemSingleSelect<SkuDetails>) {
            val data = item.data
            with(itemView) {
                textViewTitle.text = formatTitle(data)
                textViewDescription.text = data.description
                textViewValue.text = context.getString(R.string.label_billing_value, data.price)

                if (item.isSelected) {
                    layoutItemBilling.setBackgroundColor(ContextCompat.getColor(context,R.color.colorAccent))
                    textViewTitle.setTextColor(ContextCompat.getColor(context,R.color.colorWhite))
                    textViewDescription.setTextColor(ContextCompat.getColor(context,R.color.colorWhite))
                    textViewValue.setTextColor(ContextCompat.getColor(context,R.color.colorWhite))
                } else {
                    layoutItemBilling.setBackgroundColor(ContextCompat.getColor(context,R.color.colorWhite))
                    textViewTitle.setTextColor(ContextCompat.getColor(context,R.color.colorAccent))
                    textViewDescription.setTextColor(ContextCompat.getColor(context,R.color.colorAccent))
                    textViewValue.setTextColor(ContextCompat.getColor(context,R.color.colorPrimaryDark))
                }
            }
        }

        private fun formatTitle(data: SkuDetails) = Regex(" \\(.*").replace(data.title, "")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_billing_plan, parent, false)
        return ViewHolder(view)
    }

}