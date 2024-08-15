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

    private var clicked = false

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


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        windowsSet()
        showLoading()

        initViewModel()
        initDataStoreManager()
        initAdapter()

        statusHttpObserver()
        val productId = intent.getStringExtra("PRODUCT_ID") ?: ""
        fetchProductDetail(productId)
        fetchProductDetailObserver()

        backButton()
        extendedFloatingActionButton()
        deleteObserver()
    }

    private fun statusHttpObserver() {
        detailViewModel.status.observe(this) {
            Log.e("DetailProductActivity", "Status: $it")
            if (it == "success") {
                hideLoading()
            }
        }

        detailViewModel.code.observe(this) {
            Log.e("DetailProductActivity", "Code: $it")
        }

        detailViewModel.errorMessage.observe(this) {
            Log.e("DetailProductActivity", "Error Message: $it")
        }
    }

    private fun fetchProductDetail(productId: String) {
        lifecycleScope.launch {
            showLoading()
            dataStoreModel.tokenFlow.collect { token ->
                if (token != null) {
                    detailViewModel.fetchProductDetail(token, productId)
                    hideLoading()
                } else {
                    Log.e("DetailProductActivity", "Token is null")
                    showLoading()
                }
            }

        }
    }

    private fun fetchProductDetailObserver() {
        showLoading()
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
                    hideLoading()
                }
            }

        }
    }

    private fun extendedFloatingActionButton() {
        binding.fabExtended.setOnClickListener {
            onAddedButtonClicked()
        }
    }

    private fun backButton() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun initAdapter() {
        adapter = ListProductTypeAdapter(emptyList())
        binding.rvProductType.adapter = adapter
        binding.rvProductType.layoutManager = LinearLayoutManager(this)
    }

    private fun initViewModel() {
        detailViewModel = ViewModelProvider(
            this,
            ViewModelFactory(Repository(Application()))
        )[DetailProductViewModel::class.java]
    }

    private fun initDataStoreManager() {
        dataStoreModel = DataStoreManager.getInstance(this@DetailProductActivity)
    }

    private fun deleteObserver() {
        detailViewModel.deleteProduct.observe(this) { result ->
            result.onSuccess {
                CommonUtils.showToast(this, "Product deleted successfully")
                finish()
            }.onFailure {
                Log.e("DetailProductActivity", "Failed to delete product", it)
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

    private fun showLoading() {
        binding.apply {
            lottieLoadingCard.visibility = View.VISIBLE
            nestedScrollView.visibility = View.GONE
            lottieLoadingCard.playAnimation()
        }
    }

    private fun hideLoading() {
        binding.apply {
            lottieLoadingCard.visibility = View.GONE
            nestedScrollView.visibility = View.VISIBLE
            lottieLoadingCard.cancelAnimation()
        }
    }

    private fun windowsSet() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}