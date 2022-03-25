package com.example.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.menu.databinding.MenuItemBinding
import com.squareup.picasso.Picasso

class PizzaMenuAdapter(private val menuItemList: ArrayList<PizzaMenuFragment.MenuInfo>): RecyclerView.Adapter<PizzaMenuAdapter.MenuItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuItemViewHolder {
        return MenuItemViewHolder(MenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MenuItemViewHolder, position: Int) {
        holder.binding.itemTitle.text = menuItemList[position].name
        
        /** The following Picasso call does not have any errors **/
        Picasso.get().load(menuItemList[position].imageUrl).fit().into(holder.binding.itemImage)
        if (menuItemList[position].ingredients.isNotEmpty()) {
            val ingredientBuffer = StringBuffer()
            for (index in 0 until menuItemList[position].ingredients.size) {
                ingredientBuffer.append(", ${menuItemList[position].ingredients[index]}")
            }

            holder.binding.ingredients.text = ingredientBuffer.toString()
        }

        holder.binding.price.text = menuItemList[position].price
        holder.binding.discount.text = menuItemList[position].discount
    }

    override fun getItemCount(): Int {
        return menuItemList.size
    }

    class MenuItemViewHolder(val binding: MenuItemBinding): RecyclerView.ViewHolder(binding.root)
}
