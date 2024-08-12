package code.monkeys.zarqa.views.worker.warehouse.product.detail

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import code.monkeys.zarqa.R
import code.monkeys.zarqa.databinding.ActivityDetailProductBinding
import code.monkeys.zarqa.repository.Repository
import code.monkeys.zarqa.utils.DataStoreManager
import code.monkeys.zarqa.utils.ViewModelFactory
import code.monkeys.zarqa.views.worker.warehouse.tab.adapter.ListProductTypeAdapter
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch

class DetailProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailProductBinding
    private lateinit var detailViewModel: DetailProductViewModel
    private lateinit var dataStoreModel: DataStoreManager
    private lateinit var adapter: ListProductTypeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dataStoreModel = DataStoreManager.getInstance(this@DetailProductActivity)
        detailViewModel = ViewModelProvider(
            this,
            ViewModelFactory(Repository(Application()))
        )[DetailProductViewModel::class.java]

        adapter = ListProductTypeAdapter(emptyList())
        binding.rvProductType.layoutManager = LinearLayoutManager(this)

        val productId = intent.getStringExtra("PRODUCT_ID") ?: return


        detailViewModel.status.observe(this) {
            Log.e("DetailProductActivity", "Status: $it")
        }

        detailViewModel.code.observe(this) {
            Log.e("DetailProductActivity", "Code: $it")
        }

        detailViewModel.errorMessage.observe(this) {
            Log.e("DetailProductActivity", "Error Message: $it")
        }

        detailViewModel.productDetail.observe(this) { productDetail ->
            productDetail?.let {
                binding.apply {
                    tvProductName.text = it.name
                    tvProductColor.text = it.color
                    tvProductTotalStock.text = it.totalStock.toString()
                    Glide.with(this@DetailProductActivity)
                        .load(it.images.getOrNull(0))
                        .into(ivProductImage)

                    val adapter = ListProductTypeAdapter(it.productType)
                    binding.rvProductType.adapter = adapter
                }
            }

        }

        lifecycleScope.launch {
            dataStoreModel.tokenFlow.collect { token ->
                if (token != null) {
                    detailViewModel.fetchProductDetail(token, productId)
                } else {
                    Log.e("DetailProductActivity", "Token is null")
                }
            }
        }

        binding.apply {
            btnBack.setOnClickListener {
                finish()
            }
        }
    }

}