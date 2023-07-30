package com.example.dgpays

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.dgpays.databinding.ActivityMainBinding
import com.example.dgpays.ui.home.HomeFragment
import com.example.dgpays.ui.Profile
import com.example.dgpays.ui.bag.ShoppingBagFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId){

                R.id.homeFragment -> replaceFragment(HomeFragment())
                R.id.orderFragment -> replaceFragment(Profile())
                R.id.bagFragment -> replaceFragment(ShoppingBagFragment())

                else ->{}
            }
            true
        }

        replaceFragment(HomeFragment())
    }

    private fun replaceFragment(fragment : Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.flFragment, fragment)
            .commit()
    }
}