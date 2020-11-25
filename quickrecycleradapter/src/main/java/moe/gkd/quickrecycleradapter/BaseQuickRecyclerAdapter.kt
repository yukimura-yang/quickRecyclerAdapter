package moe.gkd.quickrecycleradapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

abstract class BaseQuickRecyclerAdapter<N : ViewBinding, E : ViewBinding, T>(protected val data: MutableList<T>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    protected val EMPTY = 0
    protected val NORMAL = 1

    /**
     * 多级继承修改了泛型数量或顺序需要重写
     */
    protected open fun getNBindding(inflater: LayoutInflater, parent: ViewGroup): N {
        val type = this.javaClass.genericSuperclass as ParameterizedType
        val clazz = type.actualTypeArguments[0] as Class<N>
        val method = clazz.getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
        return method.invoke(null, inflater, parent, false) as N
    }

    /**
     * 多级继承修改了泛型数量或顺序需要重写
     */
    protected open fun getEBinding(inflater: LayoutInflater, parent: ViewGroup): E {
        val type = this.javaClass.genericSuperclass as ParameterizedType
        val clazz = type.actualTypeArguments[1] as Class<E>
        val method = clazz.getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
        return method.invoke(null, inflater, parent, false) as E
    }

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

    private fun onCreateEmptyViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): BaseViewHolder<E> {
        val binding: E = getEBinding(inflater, parent)
        return BaseViewHolder(binding)
    }

    private final fun onCreateNormalViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): BaseViewHolder<N> {
        val binding: N = getNBindding(inflater, parent)
        return BaseViewHolder(binding)
    }

    protected abstract fun onBindEmptyViewHolder(holder: BaseViewHolder<E>)

    protected abstract fun onBindNormalViewHolder(
        holder: BaseViewHolder<N>,
        item: T,
        position: Int
    )


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == NORMAL) {
            return onBindNormalViewHolder(holder as BaseViewHolder<N>, data[position], position)
        } else {
            return onBindEmptyViewHolder(holder as BaseViewHolder<E>)
        }
    }
}