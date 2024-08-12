package code.monkeys.zarqa.views.worker.warehouse.product.detail

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import code.monkeys.zarqa.R
import code.monkeys.zarqa.databinding.ActivityAllProductBinding
import code.monkeys.zarqa.databinding.ActivityDetailProductBinding
import code.monkeys.zarqa.utils.DataStoreManager

class DetailProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailProductBinding
    private lateinit var detailViewModel: DetailProductViewModel
    private lateinit var dataStoreModel: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dataStoreModel = DataStoreManager.getInstance(this@DetailProductActivity)


    }
}