package code.monkeys.zarqa.views.worker.warehouse.product.detail.update

import android.Manifest
import android.app.Application
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import code.monkeys.zarqa.R
import code.monkeys.zarqa.data.source.remote.request.product.Product
import code.monkeys.zarqa.data.source.remote.request.product.ProductType
import code.monkeys.zarqa.data.source.remote.response.ProductTypeItem
import code.monkeys.zarqa.databinding.ActivityUpdateProductBinding
import code.monkeys.zarqa.repository.Repository
import code.monkeys.zarqa.utils.CommonUtils
import code.monkeys.zarqa.utils.DataStoreManager
import code.monkeys.zarqa.utils.ViewModelFactory

@Suppress("DEPRECATION")
class UpdateProductActivity : AppCompatActivity() {

    companion object {
        private const val REQUIRED_PERMISSION_CAMERA = Manifest.permission.CAMERA
    }

    private lateinit var binding: ActivityUpdateProductBinding
    private lateinit var viewModel: UpdateProductViewModel

    private var currentImageUri: Uri? = null
    private var filePathImage: String? = null
    private var penampungStringDariCloudinary: String? = null
    private lateinit var dataStoreManager: DataStoreManager


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityUpdateProductBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        windowInset()

        val productId = intent.getStringExtra("PRODUCT_ID") ?: ""
        val productName = intent.getStringExtra("PRODUCT_NAME")
        val productLowStock = intent.getStringExtra("PRODUCT_LOW_STOCK") ?: "0"
        val productColor = intent.getStringExtra("PRODUCT_COLOR")
        val productType = intent.getParcelableArrayListExtra<ProductTypeItem>("PRODUCT_TYPE")

        binding.edtProductName.setText(productName)
        binding.edtProductColor.setText(productColor)
        binding.edtProductRangeLowStock.setText(productLowStock)
        productType?.forEach { type ->

            when (type.size) {
                "S" -> {
                    binding.edtProductPriceS.setText(type.price.toString())
                    binding.edtProductStockS.setText(type.stock.toString())
                }

                "M" -> {
                    binding.edtProductPriceM.setText(type.price.toString())
                    binding.edtProductStockM.setText(type.stock.toString())
                }

                "L" -> {
                    binding.edtProductPriceL.setText(type.price.toString())
                    binding.edtProductStockL.setText(type.stock.toString())
                }

                "XL" -> {
                    binding.edtProductPriceXl.setText(type.price.toString())
                    binding.edtProductStockXl.setText(type.stock.toString())
                }

                "XXL" -> {
                    binding.edtProductPriceXxl.setText(type.price.toString())
                    binding.edtProductStockXxl.setText(type.stock.toString())
                }

                "ALL" -> {
                    binding.edtProductPriceAll.setText(type.price.toString())
                    binding.edtProductStockAll.setText(type.stock.toString())
                }
            }

        }

        binding.apply {
            btnSave.setOnClickListener {
                updateProduct(productId)
            }
        }

        initViewModel()
        initDataStoreManager()
        backButton()

    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(
            this@UpdateProductActivity,
            ViewModelFactory(Repository(Application()))
        )[UpdateProductViewModel::class.java]
    }

    private fun initDataStoreManager() {
        dataStoreManager = DataStoreManager.getInstance(this@UpdateProductActivity)
    }

    private fun updateProduct(productId: String) {
        binding.apply {

            val updatedProductName = edtProductName.text.toString()
            val updatedProductColor = edtProductColor.text.toString()
            val updatedProductStockLow = edtProductRangeLowStock.text.toString().toIntOrNull() ?: 0

            val sizeS = "S"
            val pricesS = binding.edtProductPriceS.text.toString().toIntOrNull() ?: 0
            val stockS = binding.edtProductStockS.text.toString().toIntOrNull() ?: 0

            val sizeM = "M"
            val pricesM = binding.edtProductPriceM.text.toString().toIntOrNull() ?: 0
            val stockM = binding.edtProductStockM.text.toString().toIntOrNull() ?: 0

            val sizeL = "L"
            val pricesL = binding.edtProductPriceL.text.toString().toIntOrNull() ?: 0
            val stockL = binding.edtProductStockL.text.toString().toIntOrNull() ?: 0

            val sizeXL = "XL"
            val pricesXL = binding.edtProductPriceXl.text.toString().toIntOrNull() ?: 0
            val stockXL = binding.edtProductStockXl.text.toString().toIntOrNull() ?: 0

            val sizeXXL = "XXL"
            val pricesXXL = binding.edtProductPriceXxl.text.toString().toIntOrNull() ?: 0
            val stockXXL = binding.edtProductStockXxl.text.toString().toIntOrNull() ?: 0

            val sizeALL = "ALL"
            val pricesALL = binding.edtProductPriceAll.text.toString().toIntOrNull() ?: 0
            val stockALL = binding.edtProductStockAll.text.toString().toIntOrNull() ?: 0

            val productTypeS = ProductType(sizeS, pricesS, stockS)
            val productTypeM = ProductType(sizeM, pricesM, stockM)
            val productTypeL = ProductType(sizeL, pricesL, stockL)
            val productTypeXL = ProductType(sizeXL, pricesXL, stockXL)
            val productTypeXXL = ProductType(sizeXXL, pricesXXL, stockXXL)
            val productTypeALL = ProductType(sizeALL, pricesALL, stockALL)

            val newProduct = Product(
                name = updatedProductName,
                color = updatedProductColor,
                productType = listOf(
                    productTypeS,
                    productTypeM,
                    productTypeL,
                    productTypeXL,
                    productTypeXXL,
                    productTypeALL
                )
            )

            viewModel.updateProduct(CommonUtils.showToken(this@UpdateProductActivity), productId, newProduct)
            finish()
        }
    }


    private fun backButton() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun windowInset() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}