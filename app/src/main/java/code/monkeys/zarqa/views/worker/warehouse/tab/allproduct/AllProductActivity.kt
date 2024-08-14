package code.monkeys.zarqa.views.worker.warehouse.tab.allproduct

import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.PopupMenu
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
import code.monkeys.zarqa.views.worker.warehouse.product.detail.DetailProductActivity
import code.monkeys.zarqa.views.worker.warehouse.tab.adapter.ListProductAdapter
import kotlinx.coroutines.launch

@Suppress("UNUSED_EXPRESSION")
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

            btnSort.setOnClickListener {
                CommonUtils.showToast(this@AllProductActivity, "Sort")
            }
        }

        popupMenuSort()

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

        adapter.setOnItemClickListener(object : ListProductAdapter.OnItemClickListener {
            override fun onItemClick(productId: String) {
                val intent = Intent(this@AllProductActivity, DetailProductActivity::class.java)
                intent.putExtra("PRODUCT_ID", productId)
                startActivity(intent)
            }

        })

        val token = CommonUtils.showToken(this)
        allProductViewModel.fetchProducts(token)
        Log.e("AllProductFragment", CommonUtils.showToken(this))
    }

    @SuppressLint("DiscouragedPrivateApi")
    private fun popupMenuSort() {
        val popUpMenu = PopupMenu(this, binding.btnSort).apply {
            inflate(R.menu.popup_sort_menu)
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.sort_alpha_up -> {
                        CommonUtils.showToast(this@AllProductActivity, "Sorting A-Z")
                        true
                    }

                    R.id.sort_alpha_down -> {
                        CommonUtils.showToast(this@AllProductActivity, "Sorting Z-A")
                        true
                    }

                    else -> false
                }
            }
        }

        // Mengaktifkan penampilan ikon dalam PopupMenu
        try {
            val popup =
                PopupMenu::class.java.getDeclaredField("mPopup").apply { isAccessible = true }
            val menu = popup.get(popUpMenu)
            menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                .invoke(menu, true)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        binding.btnSort.setOnClickListener {
            popUpMenu.show()
        }

    }
}