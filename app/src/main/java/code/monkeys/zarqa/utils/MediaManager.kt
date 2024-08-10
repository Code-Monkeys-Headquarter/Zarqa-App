package code.monkeys.zarqa.utils

import android.app.Application
import com.cloudinary.android.MediaManager

class MediaManager : Application() {
    override fun onCreate() {
        super.onCreate()

        // Inisialisasi Cloudinary hanya sekali
        val configCloudinary = HashMap<String, String>()
        configCloudinary["cloud_name"] = "dhqpsn90p"
        configCloudinary["api_key"] = "219489315573247"
        configCloudinary["api_secret"] = "UsXscbAhfTZPJm1RraNbpDFv9ng"
        MediaManager.init(this, configCloudinary)
    }
}