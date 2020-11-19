package moe.gkd.quickrecycleradapter

import androidx.viewbinding.ViewBinding

abstract class BaseNoEmptyQuickRecyclerAdapter<VH : ViewBinding, T>(data: MutableList<T>) :
    BaseQuickRecyclerAdapter<VH, VH, T>(
        data
    ) {
    override fun onBindEmptyViewHolder(holder: BaseViewHolder<VH>) {

    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return NORMAL
    }
}