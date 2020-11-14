package yalexaner.items

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import yalexaner.items.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: MainViewModel by viewModels()

        viewModel.items.observe(this) {
            binding.itemsCountText.text = viewModel.items.value.toString()
        }

        binding.addNewOrderBtn.setOnClickListener {
            viewModel.addItems(7)
        }
    }
}