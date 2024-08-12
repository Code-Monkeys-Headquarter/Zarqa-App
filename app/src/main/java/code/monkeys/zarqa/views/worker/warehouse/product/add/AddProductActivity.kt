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
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
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
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback

class AddProductActivity : AppCompatActivity() {

    companion object {
        private const val REQUIRED_PERMISSION_CAMERA = Manifest.permission.CAMERA
    }

    private lateinit var binding: ActivityAddProductBinding
    private var currentImageUri: Uri? = null
    private var filePathImage: String? = null
    private var penampungStringDariCloudinary: String? = null
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
            this, ViewModelFactory(Repository(Application()))
        )[AddProductViewModel::class.java]

//        Permission All Granted Handle
        if (!allPermissionGranted()) {
            requestPermissionLauncer.launch(REQUIRED_PERMISSION_CAMERA)
        }

        binding.apply {
            btnBack.setOnClickListener {
                finish()
            }

            btnGallery.setOnClickListener {
                startGallery()
            }

            btnCamera.setOnClickListener {
                startCameraX()
            }

            tvProductImageTitle.setOnClickListener {}

//            tvTitle.setOnClickListener {
//                edtProductName.setText("Test Product ${CommonUtils.getCurrentDate()}")
//                edtProductColor.setText("Test Color ${CommonUtils.getCurrentDate()}")
//                edtProductRangeLowStock.setText("10")
//                edtProductPriceS.setText("10000")
//                edtProductStockS.setText("10")
//                edtProductPriceM.setText("10000")
//                edtProductStockM.setText("10")
//                edtProductPriceL.setText("10000")
//                edtProductStockL.setText("10")
//                edtProductPriceXl.setText("10000")
//                edtProductStockXl.setText("10")
//                edtProductPriceXxl.setText("10000")
//                edtProductStockXxl.setText("10")
//                edtProductPriceAll.setText("10000")
//                edtProductStockAll.setText("10")
//
//                CommonUtils.showToast(
//                    this@AddProductActivity, "Uri : $currentImageUri, Path : $filePathImage"
//                )
//            }
            btnSave.setOnClickListener {
                showConfirmationDialog()
            }
        }

        productViewModel.addProductResult.observe(this) { result ->
            result.onSuccess {
                hideLoading()
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                finish()
            }.onFailure {
                hideLoading()
                Toast.makeText(this, "Failed to add product", Toast.LENGTH_SHORT).show()
                Log.e("AddProductActivity", "Failed to add product", it)
            }
        }


    }

    private fun uploadToCloudinary(uri: Uri) {
        MediaManager.get().upload(uri).unsigned("rlida6g3").callback(object : UploadCallback {
            override fun onSuccess(requestId: String?, resultData: MutableMap<Any?, Any?>?) {
                resultData?.let {
                    val imageUrl = it["url"] as? String
                    if (imageUrl != null) {
                        // Simpan URL untuk digunakan nanti
                        penampungStringDariCloudinary = imageUrl
                        // Setelah berhasil upload gambar, proses penyimpanan produk dilakukan
                        saveProduct()
                    }
                }
            }

            override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {
                // Optional: Update progress UI
            }

            override fun onReschedule(requestId: String?, error: ErrorInfo?) {
                // Optional: Handle rescheduling
            }

            override fun onError(requestId: String?, error: ErrorInfo?) {
                Toast.makeText(applicationContext, "Upload failed: $error", Toast.LENGTH_SHORT)
                    .show()
                Log.e("Cloudinary", "Error: $error")
                // Jika upload gambar gagal, jangan lanjutkan penyimpanan produk
                hideLoading()
            }

            override fun onStart(requestId: String?) {
                // Optional: Handle start
            }
        }).dispatch()
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
            val inputStream = contentResolver.openInputStream(it)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            binding.ivProductImage.setImageBitmap(bitmap)
            filePathImage = it.path // Menyimpan path file
        }
    }

    private fun validateInput(
        productName: String,
        color: String,
        lowStockAlert: Int,
    ): Boolean {
        if (productName.isEmpty() || color.isEmpty() || lowStockAlert <= 0) {
            CommonUtils.showToast(
                this@AddProductActivity, "Please fill in all the fields correctly"
            )
            hideLoading()
            return false
        }
        return true
    }

    @SuppressLint("SetTextI18n")
    private fun showLoading() {
        binding.apply {
            btnSave.isEnabled = false
            btnSave.text = "Loading....."
            btnSave.setBackgroundColor(
                ContextCompat.getColor(
                    this@AddProductActivity, android.R.color.darker_gray
                )
            )
            btnSave.setTextColor(
                ContextCompat.getColor(
                    this@AddProductActivity, android.R.color.white
                )
            )
        }
    }

    @SuppressLint("SetTextI18n")
    private fun hideLoading() {
        binding.apply {
            btnSave.isEnabled = true
            btnSave.text = "Simpan"
            btnSave.setBackgroundColor(
                ContextCompat.getColor(
                    this@AddProductActivity, android.R.color.black
                )
            )
            btnSave.setTextColor(
                ContextCompat.getColor(
                    this@AddProductActivity, android.R.color.white
                )
            )
        }
    }

//    private fun showConfirmationDialog() {
//        val builder = AlertDialog.Builder(this)
//        builder.setTitle("Konfirmasi")
//        builder.setMessage("Apakah Anda yakin ingin menambahkan produk ini?")
//
//        builder.setPositiveButton("Ya") { dialog, _ ->
//            // Menampilkan Loading Sebelum Proses
//            showLoading()
//            binding.apply {
//                val name = edtProductName.text.toString().trim()
//                val color = edtProductColor.text.toString().trim()
//                val lowStockAlert = edtProductRangeLowStock.text.toString().toIntOrNull() ?: 0
//                val productImageUri = penampungStringDariCloudinary ?: DEFAULT_IMAGE_URI
//
//                // Mengambil harga dan stok untuk setiap ukuran produk dengan default 0 jika tidak diisi
////                val sizeS = "S"
////                val pricesS = edtProductPriceS.text.toString().toIntOrNull() ?: 0
////                val stockS = edtProductStockS.text.toString().toIntOrNull() ?: 0
////
////                val sizeM = "M"
////                val pricesM = edtProductPriceM.text.toString().toIntOrNull() ?: 0
////                val stockM = edtProductStockM.text.toString().toIntOrNull() ?: 0
////
////                val sizeL = "L"
////                val pricesL = edtProductPriceL.text.toString().toIntOrNull() ?: 0
////                val stockL = edtProductStockL.text.toString().toIntOrNull() ?: 0
////
////                val sizeXL = "XL"
////                val pricesXL = edtProductPriceXl.text.toString().toIntOrNull() ?: 0
////                val stockXL = edtProductStockXl.text.toString().toIntOrNull() ?: 0
////
////                val sizeXXL = "XXL"
////                val pricesXXL = edtProductPriceXxl.text.toString().toIntOrNull() ?: 0
////                val stockXXL = edtProductStockXxl.text.toString().toIntOrNull() ?: 0
////
////                val sizeALL = "ALL"
////                val pricesALL = edtProductPriceAll.text.toString().toIntOrNull() ?: 0
////                val stockALL = edtProductStockAll.text.toString().toIntOrNull() ?: 0
//
//                if (validateInput(name, color, lowStockAlert)) {
//                    val productTypeS = ProductType(sizeS, pricesS, stockS)
//                    val productTypeM = ProductType(sizeM, pricesM, stockM)
//                    val productTypeL = ProductType(sizeL, pricesL, stockL)
//                    val productTypeXL = ProductType(sizeXL, pricesXL, stockXL)
//                    val productTypeXXL = ProductType(sizeXXL, pricesXXL, stockXXL)
//                    val productTypeALL = ProductType(sizeALL, pricesALL, stockALL)
//                    val product = Product(
//                        name,
//                        listOf(productImageUri),
//                        color = color,
//                        productType = listOf(
//                            productTypeS,
//                            productTypeM,
//                            productTypeL,
//                            productTypeXL,
//                            productTypeXXL,
//                            productTypeALL
//                        )
//                    )
//                    val token = CommonUtils.showToken(this@AddProductActivity)
//                    productViewModel.addProduct(token, product)
//                }
//            }
//            dialog.dismiss()
//        }
//
//        builder.setNegativeButton("Batal") { dialog, _ ->
//            dialog.dismiss()
//        }
//
//        val dialog: AlertDialog = builder.create()
//        dialog.show()
//    }

    private fun showConfirmationDialog() {
        AlertDialog.Builder(this).apply {
            setTitle("Confirmation")
            setMessage("Are you sure you want to add this product?")
            setPositiveButton("Yes") { _, _ ->
                showLoading()
                if (currentImageUri != null) {
                    uploadToCloudinary(currentImageUri!!)
                } else {
                    // Jika gambar belum dipilih, simpan produk tanpa gambar
                    saveProduct()
                }
            }
            setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            create()
            show()
        }
    }

    private fun saveProduct() {
        val productName = binding.edtProductName.text.toString()
        val color = binding.edtProductColor.text.toString()
        val lowStockAlert = binding.edtProductRangeLowStock.text.toString().toIntOrNull() ?: 0
        val DEFAULT_IMAGE_URI = "wkwk"
        val productImageUri = penampungStringDariCloudinary ?: DEFAULT_IMAGE_URI

        if (!validateInput(productName, color, lowStockAlert)) return

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
            name = productName,
            listOf(productImageUri),
            color = color,
            productType = listOf(
                productTypeS,
                productTypeM,
                productTypeL,
                productTypeXL,
                productTypeXXL,
                productTypeALL
            )
        )

        val token = CommonUtils.showToken(this@AddProductActivity)
        productViewModel.addProduct(token, newProduct)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.apply {
            btnBack.setOnClickListener(null)
            btnGallery.setOnClickListener(null)
            btnCamera.setOnClickListener(null)
            btnSave.setOnClickListener(null)
        }
    }


}