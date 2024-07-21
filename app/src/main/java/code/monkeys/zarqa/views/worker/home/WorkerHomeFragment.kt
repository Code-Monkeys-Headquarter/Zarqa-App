package code.monkeys.zarqa.views.worker.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import code.monkeys.zarqa.databinding.FragmentWorkerHomeBinding

class WorkerHomeFragment : Fragment() {

    private lateinit var binding: FragmentWorkerHomeBinding

    companion object {
        fun newInstance(): WorkerHomeFragment {
            val fragment = WorkerHomeFragment()
            fragment.arguments = Bundle()
            return fragment
        }
    }

    private val viewModel: WorkerHomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWorkerHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}