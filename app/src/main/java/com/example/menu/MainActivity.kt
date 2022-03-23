package com.example.menu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.menu.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.appTitle, PizzaMenuFragment())
        transaction.commit()
    }
}