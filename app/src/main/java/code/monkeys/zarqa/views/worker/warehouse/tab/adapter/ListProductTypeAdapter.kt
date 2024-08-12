package code.monkeys.zarqa.views.worker.warehouse.tab.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import code.monkeys.zarqa.data.source.remote.response.ProductTypeItem
import code.monkeys.zarqa.databinding.WarehouseDetailProductTypeBinding

class ListProductTypeAdapter(private val productTypeList: List<ProductTypeItem>) :
    RecyclerView.Adapter<ListProductTypeAdapter.ProductTypeViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductTypeViewHolder {
        val binding =
            WarehouseDetailProductTypeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ProductTypeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductTypeViewHolder, position: Int) {
        holder.bind(productTypeList[position])
    }

    override fun getItemCount(): Int {
        return productTypeList.size
    }

    inner class ProductTypeViewHolder(private val binding: WarehouseDetailProductTypeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(productType: ProductTypeItem) {
            binding.apply {
                tvProductSize.text = productType.size ?: "N/A"
                tvProductPrice.text = productType.price?.toString() ?: "N/A"
                tvProductStock.text = productType.stock?.toString() ?: "N/A"
            }
        }

    }
}