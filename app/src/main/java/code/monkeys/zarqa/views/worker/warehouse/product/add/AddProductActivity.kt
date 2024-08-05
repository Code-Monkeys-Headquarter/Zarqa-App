package code.monkeys.zarqa.views.worker.warehouse.product.add

import android.Manifest
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
import androidx.lifecycle.lifecycleScope
import code.monkeys.zarqa.R
import code.monkeys.zarqa.data.source.local.entity.Product
import code.monkeys.zarqa.databinding.ActivityAddProductBinding
import code.monkeys.zarqa.utils.CommonUtils
import code.monkeys.zarqa.views.worker.warehouse.product.add.OpenCameraActivity.Companion.CAMERAX_RESULT
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class AddProductActivity : AppCompatActivity() {

    companion object {
        private const val REQUIRED_PERMISSION_CAMERA = Manifest.permission.CAMERA
        private const val DEFAULT_IMAGE_URI = "android.resource://code.monkeys.zarqa/drawable/product_image_default"
    }

    private lateinit var binding: ActivityAddProductBinding
    private var currentImageUri: Uri? = null
    private lateinit var productViewModel: AddProductViewModel

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

//            Save Product Button and Validation for Input User
            btnSave.setOnClickListener {
                val productName = edtProductName.text.toString().trim()
                val price = edtProductPrice.text.toString().toIntOrNull() ?: 0
                val color = edtProductColor.text.toString().trim()
                val totalStock = edtProductQuantity.text.toString().toIntOrNull() ?: 0
                val lowStockAlert = edtProductRangeLowStock.text.toString().toIntOrNull() ?: 0
                val size = getSelectedSize()
                val productImageUri = currentImageUri?.toString() ?: DEFAULT_IMAGE_URI
                val dateAdded = CommonUtils.getCurrentDate()

//                if (validateInput(productName, price, color, totalStock, lowStockAlert, size)) {
//                    val product = Product(
//                        productName = productName,
//                        productPrice = price,
//                        productColor = color,
//                        productTotalStock = totalStock,
//                        productLowStockAlert = lowStockAlert,
//                        size = size,
//                        productImage = productImageUri,
//                        dateAdded = dateAdded
//                    )
//                    lifecycleScope.launch {
//                        try {
//                            productViewModel.insertProduct(product)
//                            Snackbar.make(it, "Barang sudah disimpan ke Gudang😁🙏", Snackbar.LENGTH_SHORT)
//                                .show()
//                        } catch (e: Exception) {
//                            Log.e("LOG_ERROR", e.toString())
//                        }
//
//                    }
//                }
            }
        }

//        productViewModel.lowStockProducts.observe(this@AddProductActivity) {
//            it?.forEach { lowStockProduct ->
//                Log.d(
//                    "LOW_STOCK",
//                    "Low Stock Product: ${lowStockProduct.productName}, Stock: ${lowStockProduct.productTotalStock}"
//                )
//            }
//        }


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
                "Silahkan isi semua data atau Periksa data dengan benar"
            )
            return false
        }
        return true
    }




}