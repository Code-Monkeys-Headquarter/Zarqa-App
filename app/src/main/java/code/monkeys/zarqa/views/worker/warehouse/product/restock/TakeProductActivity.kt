package code.monkeys.zarqa.views.worker.warehouse.product.restock

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import code.monkeys.zarqa.R
import code.monkeys.zarqa.databinding.ActivityTakeProductBinding
import code.monkeys.zarqa.utils.CommonUtils

class TakeProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTakeProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityTakeProductBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actioinId, event ->

                    searchBar.setText(searchView.text)
                    searchView.hide()
                    CommonUtils.showToast(this@TakeProductActivity, searchView.text.toString())
                    false
                }
        }
    }
}