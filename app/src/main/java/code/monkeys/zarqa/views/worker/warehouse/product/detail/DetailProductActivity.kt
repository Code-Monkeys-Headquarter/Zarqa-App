package code.monkeys.zarqa.views.worker.warehouse.product.detail

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
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
import code.monkeys.zarqa.utils.CommonUtils
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

    //    Animation Floating Action Button
    private val rotateOpen: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.rotate_open_extended_fab
        )
    }
    private val rotateClose: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.rotate_close_extended_fab
        )
    }
    private val fromBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.from_bottom_extended_fab
        )
    }
    private val toBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.to_bottom_extended_fab
        )
    }

    private var clicked = false

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
        detailViewModel.deleteProduct.observe(this) { result ->
            result.onSuccess {
                CommonUtils.showToast(this, "Product deleted successfully")
                finish()
            }.onFailure {
                Log.e("DetailProductActivity", "Failed to delete product", it)
            }
        }

        lifecycleScope.launch {
            dataStoreModel.tokenFlow.collect { token ->
                if (token != null) {
                    detailViewModel.fetchProductDetail(token, productId)
                    binding.fabDelete.setOnClickListener {
                        CommonUtils.showToast(this@DetailProductActivity, "Delete")
                        detailViewModel.deleteProduct(token, productId)
                    }
                } else {
                    Log.e("DetailProductActivity", "Token is null")
                }
            }

        }

        binding.apply {
            btnBack.setOnClickListener {
                finish()
            }

            fabEdit.setOnClickListener {

            }

            fabDelete.setOnClickListener {

            }

            fabExtended.setOnClickListener {
                onAddedButtonClicked()
            }
        }
    }

    private fun onAddedButtonClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        clicked = !clicked
    }

    private fun setVisibility(clicked: Boolean) {
        if (!clicked) {
            binding.fabEdit.visibility = View.VISIBLE
            binding.fabDelete.visibility = View.VISIBLE
        } else {
            binding.fabEdit.visibility = View.INVISIBLE
            binding.fabEdit.visibility = View.INVISIBLE
        }
    }

    private fun setAnimation(clicked: Boolean) {
        if (!clicked) {
            binding.fabEdit.startAnimation(fromBottom)
            binding.fabDelete.startAnimation(fromBottom)
            binding.fabExtended.startAnimation(rotateOpen)
        } else {
            binding.fabEdit.startAnimation(toBottom)
            binding.fabDelete.startAnimation(toBottom)
            binding.fabExtended.startAnimation(rotateClose)
        }
    }
}