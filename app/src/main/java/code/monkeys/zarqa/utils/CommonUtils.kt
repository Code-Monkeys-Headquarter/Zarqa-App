package code.monkeys.zarqa.utils

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object CommonUtils {
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(Date())
    }

    fun formatCurrency(value: Int): String {
        val formatter = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
        return formatter.format(value).replace("Rp", "Rp ").replace(",00", "")
    }

    fun showLoading(view: View, lottieLoading: LottieAnimationView) {
        view.visibility = View.GONE
        view.setBackgroundColor(Color.parseColor("#80000000"))
        lottieLoading.visibility = View.VISIBLE
        lottieLoading.playAnimation()
    }


}