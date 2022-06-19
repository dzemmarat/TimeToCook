package ru.meowtee.timetocook.ui.rand_receipt

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.coroutines.flow.collect
import ru.meowtee.timetocook.R
import ru.meowtee.timetocook.core.extensions.show
import ru.meowtee.timetocook.databinding.FragmentRandomReceiptBinding
import ru.meowtee.timetocook.ui.adapter.ReceiptsAdapter
import ru.meowtee.timetocook.ui.custom.TypeWriterListener
import ru.meowtee.timetocook.viewmodels.RandomReceiptViewModel
import kotlin.properties.Delegates

class RandomReceiptFragment : Fragment(), TypeWriterListener {
    private var binding: FragmentRandomReceiptBinding by Delegates.notNull()
    private val viewModel: RandomReceiptViewModel by viewModels()

    private val receiptsAdapter by lazy { ReceiptsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentRandomReceiptBinding.inflate(inflater)
        setupStartAnimation()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTypeWriter()
        binding.btnClose.setOnClickListener {
            findNavController().navigate(R.id.action_randomReceiptFragment_to_homeFragment)
        }

        setupRecycler()
    }

    /**
     * Move tvLogo from splash
     */
    private fun setupStartAnimation() {
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.tvLogo
            duration = 400.toLong()
            scrimColor = Color.TRANSPARENT

            setAllContainerColors(requireContext().getColor(R.color.pink_dark))
        }
    }

    /**
     * Write text "Сейчас"
     */
    private fun setupTypeWriter() {
        binding.tvNow.apply {
            setDelay(400)
            animateText("Сейчас")
            setTypeWriterListener(this@RandomReceiptFragment)
        }
    }

    private fun setupRecycler() {
        binding.rvRandomReceipt.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = receiptsAdapter
        }
        receiptsAdapter.setOnItemClickListener { receipt ->
            findNavController().navigate(RandomReceiptFragmentDirections.actionRandomReceiptFragmentToReceiptInfoFragment(
                receipt = receipt
            ))
        }
        receiptsAdapter.setOnHeartClickListener { receipt ->
            viewModel.changeReceipt(receipt)
        }
        viewModel.startDatabase(requireContext())
        setItems()
        viewModel.findReceipts()
    }

    private fun setItems() {
        lifecycleScope.launchWhenCreated {
            viewModel.receipts.collect {
                receiptsAdapter.setItems(it)
            }
        }
    }

    override fun onTypingEnd(text: String?) {
        super.onTypingEnd(text)

        binding.tvRandomReceipt.show()
        binding.rvRandomReceipt.show()
    }
}