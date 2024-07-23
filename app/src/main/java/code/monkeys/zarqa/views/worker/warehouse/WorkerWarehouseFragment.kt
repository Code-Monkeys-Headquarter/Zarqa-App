package code.monkeys.zarqa.views.worker.warehouse

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import code.monkeys.zarqa.R
import code.monkeys.zarqa.databinding.FragmentWorkerSettingBinding
import code.monkeys.zarqa.databinding.FragmentWorkerWarehouseBinding

class WorkerWarehouseFragment : Fragment() {

    private lateinit var binding: FragmentWorkerWarehouseBinding

    companion object {
        fun newInstance(): WorkerWarehouseFragment {
            val fragment = WorkerWarehouseFragment()
            fragment.arguments = Bundle()
            return fragment
        }
    }

    private val viewModel: WorkerWarehouseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWorkerWarehouseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}