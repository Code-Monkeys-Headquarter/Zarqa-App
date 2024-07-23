package code.monkeys.zarqa.views.worker.warehouse.product.add

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import code.monkeys.zarqa.R
import code.monkeys.zarqa.databinding.ActivityOpenCameraBinding
import code.monkeys.zarqa.utils.Tools
import java.util.concurrent.ExecutorService

class OpenCameraActivity : AppCompatActivity() {

    //    Camera X
    private var imageCapture: ImageCapture? = null
    private var currentImage: Uri? = null
    private lateinit var cameraExecutor: ExecutorService
    private var cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    private lateinit var binding: ActivityOpenCameraBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityOpenCameraBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.apply {
            changeCamera.setOnClickListener {
                cameraSelector =
                    if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) CameraSelector.DEFAULT_FRONT_CAMERA
                    else CameraSelector.DEFAULT_BACK_CAMERA
                startCamera()
            }

            imageCapture.setOnClickListener {
                takePhoto()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        startCamera()
    }

    //    Start Camera X
    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }
            imageCapture = ImageCapture.Builder().build()

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )
            } catch (e: Exception) {
                Toast.makeText(this@OpenCameraActivity, "Gagal Membuka Kamera", Toast.LENGTH_SHORT)
                    .show()
                Log.e(TAG, "startCamera: ${e.message}")
            }
        }, ContextCompat.getMainExecutor(this))
    }

    //     Take Photo
    private fun takePhoto() {
        val imageCapture = imageCapture ?: return
        val photoFile = Tools().createCustomTempFile(application)
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    Toast.makeText(
                        this@OpenCameraActivity,
                        "Gambar berhasil diambil",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent()
                    intent.putExtra(EXTRA_CAMERAX_IMAGE, outputFileResults.savedUri.toString())
                    setResult(CAMERAX_RESULT, intent)
                    finish()
                }

                override fun onError(exception: ImageCaptureException) {
                    Toast.makeText(
                        this@OpenCameraActivity,
                        "Gambar gagal diambil",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d(TAG, "onError: ${exception.message}")
                }

            }
        )
    }

    companion object {
        private const val TAG = "OpenCameraActivity"

        //        Keperluan untuk menyimpan photo dan melihat dan juga mengirim photo ke activity yang dituju
        const val EXTRA_CAMERAX_IMAGE = "Camera Image"
        const val CAMERAX_RESULT = 200
    }
}