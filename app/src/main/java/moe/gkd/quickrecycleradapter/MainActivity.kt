package moe.gkd.quickrecycleradapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import moe.gkd.quickrecycleradapter.databinding.ActivityMainBinding
import moe.gkd.quickrecycleradapter.databinding.EmptyViewBinding
import moe.gkd.quickrecycleradapter.databinding.ItemViewBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val data = ArrayList<String>().apply {
        for (i in 0 until 10) {
            add("$i")
        }
    }
    val adapter =
        object : BaseNoEmptyQuickRecyclerAdapter<ItemViewBinding, String>(data) {
            override fun onBindNormalViewHolder(
                holder: BaseViewHolder<ItemViewBinding>,
                item: String,
                position: Int
            ) {
                holder.binding.text.text = item
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }
}