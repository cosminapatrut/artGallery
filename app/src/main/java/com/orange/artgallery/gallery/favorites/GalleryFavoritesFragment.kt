package com.orange.artgallery.gallery.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.orange.artgallery.R
import com.orange.artgallery.gallery.GalleryAdapter
import com.orange.data.room.GalleryDatabase
import com.orange.domain.model.GalleryItem
import kotlinx.android.synthetic.main.fragment_gallery.*
import org.koin.android.ext.android.get

class GalleryFavoritesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = GridLayoutManager(context, 2)
        gallery_fragment_recycler_view.layoutManager = layoutManager
        getFavorites()
    }

    private fun getFavorites() {
        gallery_progressbar.visibility = View.VISIBLE
        val database: GalleryDatabase = get()
        val items = database.galleryDao().getAllFavorites().map {
            GalleryItem(
                it.objectId,
                it.primaryImage,
                it.title
            )
        }

        val adapter = context?.let { GalleryAdapter(it, items) {} }
        gallery_fragment_recycler_view.adapter = adapter
        gallery_progressbar.visibility = View.GONE
    }

    companion object {

        @JvmStatic
        fun newInstance() = GalleryFavoritesFragment()
    }
}
