package code.monkeys.zarqa.views.worker.warehouse.tab.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import code.monkeys.zarqa.R
import code.monkeys.zarqa.data.source.remote.response.DataItem
import code.monkeys.zarqa.databinding.WarehouseListProductBinding
import com.bumptech.glide.Glide

class ListProductAdapter(private var productList: List<DataItem?>) :
    RecyclerView.Adapter<ListProductAdapter.ProductViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListProductAdapter.ProductViewHolder {
        val binding =
            WarehouseListProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListProductAdapter.ProductViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    inner class ProductViewHolder(private val binding: WarehouseListProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: DataItem?) {
            product?.let {
                binding.apply {
                    tvProductName.text = it.name ?: "Unknown Product Name"
                    tvProductColor.text = it.color ?: "Unknown Product Colour"
                    tvProductStock.text = it.totalStock?.toString() ?: "0"
                    Glide.with(ivProductImage.context)
                        .load(it.images?.getOrNull(0) ?: R.drawable.image_placeholder)
                        .into(ivProductImage)
                    val productId: String = it.id ?: ""


                    root.setOnClickListener {
                        onItemClickListener?.onItemClick(productId)
                    }
                }
            }


        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newProductList: List<DataItem>) {
        productList = newProductList
        notifyDataSetChanged()  // Jika ingin update semua item
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(productId: String)
    }


}