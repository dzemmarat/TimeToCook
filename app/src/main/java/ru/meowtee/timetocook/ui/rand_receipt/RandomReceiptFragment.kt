package ru.meowtee.timetocook.ui.rand_receipt

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedDispatcher
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.MaterialContainerTransform
import ru.meowtee.timetocook.R
import ru.meowtee.timetocook.core.extensions.show
import ru.meowtee.timetocook.data.model.Receipt
import ru.meowtee.timetocook.databinding.FragmentRandomReceiptBinding
import ru.meowtee.timetocook.ui.custom.TypeWriterListener
import ru.meowtee.timetocook.ui.rand_receipt.adapter.ReceiptsAdapter
import kotlin.properties.Delegates

class RandomReceiptFragment : Fragment(), TypeWriterListener {
    private var binding: FragmentRandomReceiptBinding by Delegates.notNull()

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
            animateText("Сегодня")
            setTypeWriterListener(this@RandomReceiptFragment)
        }
    }

    private fun setupRecycler() {
        binding.rvRandomReceipt.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = receiptsAdapter
        }
        receiptsAdapter.setItems(
            listOf(
                Receipt(R.drawable.ic_dish_2,"Французскиe круасаны с шоколадным соусом", false, emptyList()),
                Receipt(R.drawable.ic_dish,"Пряный тыквенный суп", false, emptyList()),
            )
        )
    }

    override fun onTypingEnd(text: String?) {
        super.onTypingEnd(text)

        Handler(Looper.getMainLooper()).postDelayed({
            binding.tvRandomReceipt.show()
            binding.rvRandomReceipt.show()

            setupRecycler()
        }, 400)
    }
}