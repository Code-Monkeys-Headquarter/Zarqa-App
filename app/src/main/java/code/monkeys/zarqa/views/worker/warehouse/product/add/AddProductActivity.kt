package code.monkeys.zarqa.views.worker.warehouse.product.add

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import code.monkeys.zarqa.R
import code.monkeys.zarqa.data.source.remote.request.product.Product
import code.monkeys.zarqa.data.source.remote.request.product.ProductType
import code.monkeys.zarqa.databinding.ActivityAddProductBinding
import code.monkeys.zarqa.repository.Repository
import code.monkeys.zarqa.utils.CommonUtils
import code.monkeys.zarqa.utils.DataStoreManager
import code.monkeys.zarqa.utils.ViewModelFactory
import code.monkeys.zarqa.views.worker.warehouse.product.add.OpenCameraActivity.Companion.CAMERAX_RESULT

class AddProductActivity : AppCompatActivity() {

    companion object {
        private const val REQUIRED_PERMISSION_CAMERA = Manifest.permission.CAMERA
        private const val DEFAULT_IMAGE_URI =
            "android.resource://code.monkeys.zarqa/drawable/product_image_default"
    }

    private lateinit var binding: ActivityAddProductBinding
    private var currentImageUri: Uri? = null
    private lateinit var productViewModel: AddProductViewModel
    private lateinit var dataStoreManager: DataStoreManager

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dataStoreManager = DataStoreManager.getInstance(this@AddProductActivity)

        productViewModel = ViewModelProvider(
            this,
            ViewModelFactory(Repository(Application()))
        )[AddProductViewModel::class.java]


//        Permission All Granted Handle
        if (!allPermissionGranted()) {
            requestPermissionLauncer.launch(REQUIRED_PERMISSION_CAMERA)
        }

        binding.apply {
            btnBack.setOnClickListener {
                finish()
            }

            rbSizeL.setOnClickListener {
                CommonUtils.showToast(this@AddProductActivity, "L")
            }

            btnGallery.setOnClickListener {
                startGallery()
            }

            btnCamera.setOnClickListener {
                startCameraX()
            }

            tvTitle.setOnClickListener {
                CommonUtils.showToast(
                    this@AddProductActivity,
                    CommonUtils.showToken(this@AddProductActivity)
                )
            }

            tvTitle.setOnClickListener {
                edtProductName.setText("Anjay")
                edtProductPrice.setText("100000")
                edtProductColor.setText("Red")
                edtProductQuantity.setText("10")
                edtProductRangeLowStock.setText("5")
                rbSizeL.text = "L"
                listOf(currentImageUri.toString())

            }
            btnSave.setOnClickListener {
                val name = edtProductName.text.toString().trim()
                val price = edtProductPrice.text.toString().toInt()
                val color = edtProductColor.text.toString().trim()
                val totalStock = edtProductQuantity.text.toString().toInt()
                val lowStockAlert = edtProductRangeLowStock.text.toString().toInt()
                val size = getSelectedSize()
                val productImageUri = currentImageUri?.toString() ?: DEFAULT_IMAGE_URI

                if (validateInput(name, price, color, totalStock, lowStockAlert, size)) {
                    val productType = ProductType(size, price, totalStock)
                    val product = Product(
                        name,
                        listOf(productImageUri),
                        color = color,
                        productType = listOf(productType)
                    )
                    showLoading()
                    val token = CommonUtils.showToken(this@AddProductActivity)
                    productViewModel.addProduct(token, product)
                }
            }
        }

        productViewModel.addProductResult.observe(this) { result ->
            result.onSuccess {
                hideLoading()
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
            }.onFailure {
                hideLoading()
                Toast.makeText(this, "Failed to add product", Toast.LENGTH_SHORT).show()
                Log.e("AddProductActivity", "Failed to add product", it)
            }
        }


    }

    //    Permission Camera START
    private fun allPermissionGranted() = ContextCompat.checkSelfPermission(
        this, REQUIRED_PERMISSION_CAMERA
    ) == PackageManager.PERMISSION_GRANTED

    private val requestPermissionLauncer =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this@AddProductActivity, "Perizinan diijinkan", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this@AddProductActivity, "Perizinan ditolak", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERAX_RESULT) {
            currentImageUri =
                it.data?.getStringExtra(OpenCameraActivity.EXTRA_CAMERAX_IMAGE)?.toUri()
            convertImageToBitmap()
        }
    }

    private fun startCameraX() {
        val intent = Intent(this@AddProductActivity, OpenCameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    //    Permission Open Gallery
    private val launcherGallery =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
            if (uri != null) {
                currentImageUri = uri
                //  show image
                convertImageToBitmap()
            } else {
                Log.d("IMAGE URI", "Photo Picker : No Media Selected")
            }
        }

    private fun startGallery() {
        launcherGallery.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        )
    }

    private fun convertImageToBitmap() {
        currentImageUri?.let {
            val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(it))
            binding.ivProductImage.setImageBitmap(bitmap)
        }
    }

    //    Validation Input User
    private fun getSelectedSize(): String {
        val selectedId = binding.rbGroupSize.checkedRadioButtonId
        return if (selectedId != -1) {
            val radioButton: RadioButton = findViewById(selectedId)
            radioButton.text.toString()
        } else {
            ""
        }
    }

    private fun validateInput(
        productName: String,
        price: Int,
        color: String,
        totalStock: Int,
        lowStockAlert: Int,
        size: String
    ): Boolean {
        if (productName.isEmpty() || price <= 0 || color.isEmpty() || totalStock <= 0 || lowStockAlert <= 0 || size.isEmpty()) {
            CommonUtils.showToast(
                this@AddProductActivity,
                "Please fill in all the fields correctly"
            )
            return false
        }
        return true
    }

    @SuppressLint("SetTextI18n")
    private fun showLoading() {
        binding.apply {
            btnSave.isEnabled = false
            btnSave.text = "Loading....."
            btnSave.setBackgroundColor(ContextCompat.getColor(this@AddProductActivity, android.R.color.darker_gray))
            btnSave.setTextColor(
                ContextCompat.getColor(
                    this@AddProductActivity,
                    android.R.color.white
                )
            )
        }
    }

    @SuppressLint("SetTextI18n")
    private fun hideLoading() {
        binding.apply {
            btnSave.isEnabled = true
            btnSave.text = "Simpan"
            btnSave.setBackgroundColor(ContextCompat.getColor(this@AddProductActivity, android.R.color.black))
            btnSave.setTextColor(
                ContextCompat.getColor(
                    this@AddProductActivity,
                    android.R.color.white
                )
            )
        }
    }


}