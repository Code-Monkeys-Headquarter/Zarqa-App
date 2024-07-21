package code.monkeys.zarqa.views.worker.setting

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import code.monkeys.zarqa.R
import code.monkeys.zarqa.databinding.FragmentWorkerSettingBinding

class WorkerSettingFragment : Fragment() {

    private lateinit var binding: FragmentWorkerSettingBinding

    companion object {
        fun newInstance(): WorkerSettingFragment {
            val fragment = WorkerSettingFragment()
            fragment.arguments = Bundle()
            return fragment
        }
    }

    private val viewModel: WorkerSettingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWorkerSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}