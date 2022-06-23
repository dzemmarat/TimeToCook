package ru.meowtee.timetocook.ui.search.tags

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.meowtee.timetocook.databinding.FragmentSearchByTagBinding
import ru.meowtee.timetocook.ui.adapter.ReceiptsAdapter
import ru.meowtee.timetocook.viewmodels.SearchByTagViewModel
import kotlin.properties.Delegates

class SearchByTagFragment : Fragment() {
    private var binding: FragmentSearchByTagBinding by Delegates.notNull()
    private val viewModel: SearchByTagViewModel by viewModels()

    private var difficultTag = ""
    private var timeTag = ""
    private var typeTag = ""

    private val receiptsAdapter by lazy { ReceiptsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSearchByTagBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        setupChipDifficult()
        setupChipTime()
        setupChipType()
    }

    private fun setupChipDifficult() {
        binding.easy.setOnClickListener {
            difficultTag = "Простой"
            findReceipts()
        }
        binding.medium.setOnClickListener {
            difficultTag = "Продвинутый"
            findReceipts()
        }
        binding.hard.setOnClickListener {
            difficultTag = "Сложный"
            findReceipts()
        }
    }

    private fun setupChipType() {
        with(binding) {
            breakfast.setOnClickListener {
                typeTag = "Завтрак"
                findReceipts()
            }
            soup.setOnClickListener {
                typeTag = "Суп"
                findReceipts()
            }
            base.setOnClickListener {
                typeTag = "Основное блюдо"
                findReceipts()
            }
            snack.setOnClickListener {
                typeTag = "Закуска"
                findReceipts()
            }
            drinks.setOnClickListener {
                typeTag = "Напиток"
                findReceipts()
            }
            desert.setOnClickListener {
                typeTag = "Десерт"
                findReceipts()
            }
        }
    }

    private fun findReceipts() {
        viewModel.findReceipts("$difficultTag $typeTag $timeTag")
    }

    private fun setupChipTime() {
        with(binding) {
            fast.setOnClickListener {
                timeTag = "За 30 минут"
                findReceipts()
            }
            middle.setOnClickListener {
                timeTag = "За 1 час"
                findReceipts()
            }
            middlePlus.setOnClickListener {
                timeTag = "За 1.5 часа"
                findReceipts()
            }
            longest.setOnClickListener {
                timeTag = "Более полутора часов"
                findReceipts()
            }
        }
    }

    private fun setupRecycler() {
        binding.rvReceipts.apply {
            layoutManager = object : LinearLayoutManager(requireContext()) {
                override fun canScrollVertically(): Boolean = false
            }
            adapter = receiptsAdapter
        }
        viewModel.startDatabase(requireContext())
        lifecycleScope.launch {
            viewModel.receipts.collect {
                Log.e("AAAAAAAAAAAAA", it.toString())
                receiptsAdapter.setItems(it)
            }
        }
    }
}