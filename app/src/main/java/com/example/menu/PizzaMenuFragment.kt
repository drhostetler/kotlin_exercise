package com.example.menu

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import com.example.menu.databinding.FragmentPizzaMenuBinding
import org.json.JSONArray
import java.io.IOException
import java.util.concurrent.Executors

class PizzaMenuFragment: Fragment(R.layout.fragment_pizza_menu) {
    data class MenuInfo(
        val name: String,
        val ingredients: ArrayList<String>,
        val imageUrl: String = "",
        val price: String = "",
        val discount: String = ""
    )

    lateinit var binding: FragmentPizzaMenuBinding
    private val menuInfoList = ArrayList<MenuInfo>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPizzaMenuBinding.bind(view)
        val handler = Handler(Looper.getMainLooper())
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            processJson()
            handler.post {
                binding.menuListView.adapter = PizzaMenuAdapter(menuInfoList)
            }
        }
    }

    private fun loadData(): String? {
        return try {
            activity?.assets?.open("menuData.json")?.let { inputStream ->
                val size: Int = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                inputStream.close()
                String(buffer)
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
            null
        }
    }

    private fun processJson() {
        loadData()?.let { jsonData ->
            val menuArray = JSONArray(jsonData)
            for (index in 0 until menuArray.length()) {
                val menuItem = menuArray.getJSONObject(index)
                val name = menuItem.getString("name")
                val ingredientArray = ArrayList<String>()
                if (menuItem.has("ingredients")) {
                    val ingredientJsonArray = menuItem.getJSONArray("ingredients")
                    for (ingredientIndex in 0 until ingredientJsonArray.length()) {
                        ingredientArray.add(ingredientJsonArray.getString(ingredientIndex))
                    }
                }

                val images = menuItem.getJSONObject("images")
                val lowResImage = images.getString("lowResImage")
                val price = menuItem.getString("price")
                val discount = menuItem.getString("discountPercent")
                menuInfoList.add(
                    MenuInfo(
                        name,
                        ingredientArray,
                        lowResImage,
                        price,
                        discount
                    )
                )
            }
        }
    }
}