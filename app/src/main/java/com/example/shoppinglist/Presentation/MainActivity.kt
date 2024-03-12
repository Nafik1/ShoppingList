package com.example.shoppinglist.Presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.Presentation.viewmodel.MainViewModel
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), shopItemFragment.onEditingFinishedListenner {
    private lateinit var viewmodel: MainViewModel
    private lateinit var adapter: shopListAdapter
    private var shopItemContainer : FragmentContainerView? = null
    private var _binding : ActivityMainBinding? = null
    private val binding : ActivityMainBinding
        get() = _binding ?: throw RuntimeException("oao")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        shopItemContainer = binding.shopItemContainer
        setupRw()
        viewmodel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewmodel.shoplist.observe(this) {
            adapter.submitList(it)
        }
        val buttonAddItem = binding.buttonAddShopItem
        buttonAddItem.setOnClickListener {
            if (isOnePaneMode()) {
                val intent = shop_item_activity.newIntentAdd(this)
                startActivity(intent)
            } else {
                launchFragment(shopItemFragment.newInstanceAddItem())
            }
        }
    }

    override fun onEditingFinished() {
        Toast.makeText(this@MainActivity,"Success",Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }

    private fun setupRw() {
        val rvShopList = binding.rvShopList
        //val rvShopList = findViewById<RecyclerView>(R.id.rv_shop_list)
        adapter = shopListAdapter()
        rvShopList.adapter = adapter
        rvShopList.recycledViewPool.setMaxRecycledViews(
            shopListAdapter.ENABLED_VIEW_TYPE,
            shopListAdapter.MAX_POOL_SIZE
        )
        rvShopList.recycledViewPool.setMaxRecycledViews(
            shopListAdapter.DISABLED_VIEW_TYPE,
            shopListAdapter.MAX_POOL_SIZE
        )
        longclick_activeItem()
        itemclick_infolist()
        swipe(rvShopList)
    }

    private fun longclick_activeItem() {
        adapter.onShopItemLongClick = {
            viewmodel.changeEnableState(it)
        }
    }

    private fun itemclick_infolist() {
        adapter.onShopItemClick = {
            if(isOnePaneMode()) {
                val intent = shop_item_activity.newIntentEdit(this, it.id)
                startActivity(intent)
            } else {
                launchFragment(shopItemFragment.newInstanceEditItem(it.id))
            }
        }
    }

    private fun swipe(rvShopList: RecyclerView?) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = adapter.currentList[viewHolder.adapterPosition]
                viewmodel.deleteItem(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvShopList)
    }
    private fun isOnePaneMode() : Boolean {
        return shopItemContainer == null
    }
    private fun launchFragment(fragment : Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.shopItemContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    companion object {

    }

}


