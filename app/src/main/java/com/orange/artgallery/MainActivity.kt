package com.orange.artgallery

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.orange.artgallery.gallery.GalleryFragment
import com.orange.artgallery.gallery.contact.GalleryContactFragment
import com.orange.artgallery.gallery.detail.GalleryDetailFragment
import com.orange.artgallery.gallery.favorites.GalleryFavoritesFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configureToolbar()

        navigation_view.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> openHomeFragment()
                R.id.favorites -> openFavoritesFragment()
                R.id.contact -> openContactFragment()
                else -> return@setNavigationItemSelectedListener true
            }
            true
        }

        openHomeFragment()
    }

    private fun openHomeFragment() {
        setToolbarTitle()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, GalleryFragment.newInstance())
            .commit()
        main_drawer.closeDrawer(GravityCompat.START)
    }

    private fun openFavoritesFragment() {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.container, GalleryFavoritesFragment.newInstance())
            .addToBackStack("GalleryFavoritesFragment")
            .commit()
        main_drawer.closeDrawer(GravityCompat.START)
    }

    private fun openContactFragment() {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.container, GalleryContactFragment.newInstance())
            .addToBackStack("GalleryContactFragment")
            .commit()
        main_drawer.closeDrawer(GravityCompat.START)
    }

    fun openGalleryDetailFragment(id: Long) {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.container, GalleryDetailFragment.newInstance(id))
            .addToBackStack("GalleryDetailFragment")
            .commit()
        main_drawer.closeDrawer(GravityCompat.START)
    }

    fun setToolbarTitle(title: String = "") {
        supportActionBar?.title = title
    }

    private fun configureToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            main_drawer,
            R.string.open,
            R.string.close
        )

        main_drawer.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
            setToolbarTitle()
        }
    }
}
