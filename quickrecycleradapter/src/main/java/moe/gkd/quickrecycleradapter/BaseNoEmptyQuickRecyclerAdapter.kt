package moe.gkd.quickrecycleradapter

import androidx.viewbinding.ViewBinding

abstract class BaseNoEmptyQuickRecyclerAdapter<N : ViewBinding, T>(data: MutableList<T>) :
    BaseQuickRecyclerAdapter<N, N, T>(
        data
    ) {
    override fun onBindEmptyViewHolder(holder: BaseViewHolder<N>) {

    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return NORMAL
    }
}