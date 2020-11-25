package moe.gkd.quickrecycleradapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import moe.gkd.quickrecycleradapter.databinding.EmptyViewBinding

abstract class DefaultEmptyAdapter<N : ViewBinding, T>(data: MutableList<T>) :
    BaseQuickRecyclerAdapter<N, EmptyViewBinding, T>(
        data
    ) {
    override fun onBindEmptyViewHolder(holder: BaseViewHolder<EmptyViewBinding>) {

    }

    override fun getEBinding(inflater: LayoutInflater, parent: ViewGroup): EmptyViewBinding {
      return  EmptyViewBinding.inflate(inflater, parent, false)
    }
}