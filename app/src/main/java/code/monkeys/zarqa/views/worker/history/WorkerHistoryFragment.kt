package code.monkeys.zarqa.views.worker.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import code.monkeys.zarqa.R
import code.monkeys.zarqa.databinding.FragmentWorkerHistoryBinding

class WorkerHistoryFragment : Fragment() {

    private lateinit var binding: FragmentWorkerHistoryBinding

    companion object {
        fun newInstance(): WorkerHistoryFragment {
            val fragment = WorkerHistoryFragment()
            fragment.arguments = Bundle()
            return fragment
        }
    }

    private val viewModel: WorkerHistoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWorkerHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}