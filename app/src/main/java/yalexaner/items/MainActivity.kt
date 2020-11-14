package yalexaner.items

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import yalexaner.items.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var itemsCount = 0
        binding.itemsCountText.text = itemsCount.toString()

        binding.addNewOrderBtn.setOnClickListener {
            itemsCount += 7
            binding.itemsCountText.text = itemsCount.toString()
        }
    }
}