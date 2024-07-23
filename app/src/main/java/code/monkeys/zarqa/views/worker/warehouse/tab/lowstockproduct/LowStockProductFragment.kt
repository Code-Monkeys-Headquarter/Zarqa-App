package code.monkeys.zarqa.views.worker.warehouse.tab.lowstockproduct

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import code.monkeys.zarqa.R

class LowStockProductFragment : Fragment() {

    companion object {
        fun newInstance() = LowStockProductFragment()
    }

    private val viewModel: LowStockProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_low_stock_product, container, false)
    }
}