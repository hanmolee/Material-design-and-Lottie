package com.material.hanmo.material_design_strudy.activity

import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Pair
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import android.widget.Toolbar
import com.material.hanmo.material_design_strudy.R
import com.material.hanmo.material_design_strudy.adapter.TravelListAdapter
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by hanmo on 2018. 3. 2..
 */
class MainActivity : AppCompatActivity() {

    private lateinit var staggeredGridLayoutManager: StaggeredGridLayoutManager
    private lateinit var menu: Menu
    private lateinit var adapter : TravelListAdapter
    private var isListView: Boolean = false

    private val onItemClickListener = object : TravelListAdapter.OnItemClickListener {
        override fun onItemClick(view: View, position: Int) {

            val intent = DetailActivity.newIntent(this@MainActivity, position)

            val placeImage = view.findViewById<ImageView>(R.id.placeImage)
            val placeNameHolder = view.findViewById<LinearLayout>(R.id.placeNameHolder)

            val imagePair = android.support.v4.util.Pair.create(placeImage as View, "tImage")
            val holderPair = android.support.v4.util.Pair.create(placeNameHolder as View, "tNameHolder")

            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this@MainActivity, imagePair, holderPair)

            ActivityCompat.startActivity(this@MainActivity, intent, options.toBundle())
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //setUpActionBar()

        staggeredGridLayoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        list.layoutManager = staggeredGridLayoutManager

        adapter = TravelListAdapter(this)
        adapter.setOnItemClickListener(onItemClickListener)
        list.adapter = adapter

        isListView = true
    }

    private fun setUpActionBar() {
        val toolbar = findViewById<android.support.v7.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.elevation = 7.0f
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        this.menu = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_toggle) {
            toggle()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun toggle() {
        if (isListView) {
            staggeredGridLayoutManager.spanCount = 2
            showGridView()
        } else {
            staggeredGridLayoutManager.spanCount = 1
            showListView()
        }
    }

    private fun showListView() {
        val item = menu.findItem(R.id.action_toggle)
        item.setIcon(R.drawable.ic_action_grid)
        item.title = getString(R.string.show_as_grid)
        isListView = true
    }

    private fun showGridView() {
        val item = menu.findItem(R.id.action_toggle)
        item.setIcon(R.drawable.ic_action_list)
        item.title = getString(R.string.show_as_list)
        isListView = false
    }
}