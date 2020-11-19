package moe.gkd.quickrecycleradapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

abstract class BaseQuickRecyclerAdapter<VH : ViewBinding, EVH : ViewBinding, T>(protected val data: MutableList<T>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    protected val EMPTY = 0
    protected val NORMAL = 1


    /**
     * 如果data为空就显示空页面
     */
    override fun getItemViewType(position: Int): Int {
        return if (data.size == 0) {
            EMPTY
        } else {
            NORMAL
        }
    }

    override fun getItemCount(): Int {
        return if (data.size > 0) {
            data.size
        } else {
            1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == NORMAL) {
            return onCreateNormalViewHolder(LayoutInflater.from(parent.context), parent)
        } else {
            return onCreateEmptyViewHolder(LayoutInflater.from(parent.context), parent)
        }
    }

    protected fun onCreateEmptyViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): BaseViewHolder<EVH> {
        val type = javaClass.genericSuperclass as ParameterizedType
        val clazz = type.actualTypeArguments[1] as Class<T>
        val method = clazz.getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
        val binding: EVH = method.invoke(null, inflater, parent, false) as EVH
        return BaseViewHolder(binding)
    }

    protected fun onCreateNormalViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): BaseViewHolder<VH> {
        val type = javaClass.genericSuperclass as ParameterizedType
        val clazz = type.actualTypeArguments[0] as Class<T>
        val method = clazz.getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
        val binding: VH = method.invoke(null, inflater, parent, false) as VH
        return BaseViewHolder(binding)
    }

    protected abstract fun onBindEmptyViewHolder(holder: BaseViewHolder<EVH>)

    protected abstract fun onBindNormalViewHolder(
        holder: BaseViewHolder<VH>,
        item: T,
        position: Int
    )


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == NORMAL) {
            return onBindNormalViewHolder(holder as BaseViewHolder<VH>, data[position], position)
        } else {
            return onBindEmptyViewHolder(holder as BaseViewHolder<EVH>)
        }
    }
}