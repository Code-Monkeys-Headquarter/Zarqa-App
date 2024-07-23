package code.monkeys.zarqa.views.worker.warehouse.tab.allproduct

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import code.monkeys.zarqa.R

class AllProductFragment : Fragment() {

    companion object {
        fun newInstance() = AllProductFragment()
    }

    private val viewModel: AllProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_all_product, container, false)
    }
}