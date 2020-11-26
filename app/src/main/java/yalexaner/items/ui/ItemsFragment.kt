package yalexaner.items.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import yalexaner.items.R
import yalexaner.items.data.ItemsViewModel
import yalexaner.items.databinding.FragmentItemsBinding

class ItemsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding: FragmentItemsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_items, container, false)
        val viewModel: ItemsViewModel by activityViewModels()

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.addNewOrder.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_itemsFragment_to_addingItemsFragment)
        }

        return binding.root
    }
}
