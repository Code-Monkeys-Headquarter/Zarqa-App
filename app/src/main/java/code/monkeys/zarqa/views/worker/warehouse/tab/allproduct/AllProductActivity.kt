package code.monkeys.zarqa.views.worker.warehouse.tab.allproduct

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
import code.monkeys.zarqa.databinding.ActivityAllProductBinding
import code.monkeys.zarqa.repository.Repository
import code.monkeys.zarqa.utils.CommonUtils
import code.monkeys.zarqa.utils.DataStoreManager
import code.monkeys.zarqa.utils.ViewModelFactory
import code.monkeys.zarqa.views.worker.warehouse.tab.adapter.ListProductAdapter
import kotlinx.coroutines.launch

class AllProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAllProductBinding
    private lateinit var dataStoreManager: DataStoreManager
    private lateinit var allProductViewModel: AllProductViewModel
    private lateinit var adapter: ListProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAllProductBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.apply {
            btnBack.setOnClickListener {
                finish()
            }

            btnMenu.setOnClickListener {
                CommonUtils.showToast(this@AllProductActivity, "Menu")
            }
        }

        dataStoreManager = DataStoreManager.getInstance(this)

        adapter = ListProductAdapter(emptyList())
        binding.rvProductList.adapter = adapter
        binding.rvProductList.layoutManager = LinearLayoutManager(this)

        val repository = Repository(Application())

        allProductViewModel = ViewModelProvider(
            this,
            ViewModelFactory(repository)
        )[AllProductViewModel::class.java]

        allProductViewModel.products.observe(this) { product ->
            adapter.updateData(product)
        }

        allProductViewModel.status.observe(this) { status ->
            Log.e("AllProductFragment", "Status: $status")
        }

        allProductViewModel.code.observe(this) { code ->
            Log.e("AllProductFragment", "Code: $code")
        }

        allProductViewModel.errorMessage.observe(this) { errorMessage ->
            Log.e("AllProductFragment", "Error Message: $errorMessage")
        }

        lifecycleScope.launch {
            dataStoreManager.tokenFlow.collect { token ->
                if (token != null) {
                    allProductViewModel.fetchProducts(token)
                } else {
                    Log.e("AllProductFragment", "Token is null")
                }
            }
        }

        val token = CommonUtils.showToken(this)
        allProductViewModel.fetchProducts(token)
        Log.e("AllProductFragment", CommonUtils.showToken(this))
    }
}