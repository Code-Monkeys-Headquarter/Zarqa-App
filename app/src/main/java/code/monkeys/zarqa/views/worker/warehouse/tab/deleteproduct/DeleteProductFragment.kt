package code.monkeys.zarqa.views.worker.warehouse.tab.deleteproduct

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import code.monkeys.zarqa.R

class DeleteProductFragment : Fragment() {

    companion object {
        fun newInstance() = DeleteProductFragment()
    }

    private val viewModel: DeleteProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_delete_product, container, false)
    }
}