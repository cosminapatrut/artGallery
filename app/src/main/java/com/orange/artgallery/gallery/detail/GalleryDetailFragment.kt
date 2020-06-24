package com.orange.artgallery.gallery.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.orange.artgallery.MainActivity
import com.orange.artgallery.R
import com.orange.domain.model.GalleryDetailModel
import com.orange.domain.model.GalleryEntityModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_gallery_content.*
import kotlinx.android.synthetic.main.fragment_gallery_detail.*
import org.koin.android.viewmodel.ext.android.viewModel

private const val ARG_ID = "GalleryDetailFragment::id"

class GalleryDetailFragment : Fragment() {
    private var id: Long? = null
    private var galleryItem: GalleryDetailModel? = null
    private val compositeDisposable = CompositeDisposable()
    private val galleryDetailViewModel: GalleryDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getLong(ARG_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_gallery_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getGalleryDetail()

        galleryDetailViewModel.data.observe(this, Observer {
            galleryItem = it
            displayArtwork()
            id?.let {
                galleryDetailViewModel.isItemInFavorites(it)
            }
        })

        galleryDetailViewModel.isLoading.observe(this, Observer {
            it?.let {
                gallery_detail_progressbar.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        galleryDetailViewModel.showError.observe(this, Observer {
            it?.let {
                gallery_detail_error.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        galleryDetailViewModel.isInFavorites.observe(this, Observer {
            add_remove_favorite.apply {
                show()
                setImageResource(if (it) R.drawable.ic_favorite else R.drawable.ic_add_favorite)
            }
        })

        add_remove_favorite.setOnClickListener {
            addOrRemoveFavorites()
        }
    }

    private fun addOrRemoveFavorites() {
        id?.let {
            if (galleryDetailViewModel.isInFavorites.value == true) {
                galleryDetailViewModel.removeFavorite(it)
                Toast.makeText(context, R.string.removed_from_favorites, Toast.LENGTH_SHORT).show()
            } else {
                galleryDetailViewModel.addToFavorites(
                    GalleryEntityModel(
                        it,
                        galleryItem?.image,
                        galleryItem?.title,
                        galleryItem?.artistDisplayName,
                        galleryItem?.artistDisplayBio,
                        galleryItem?.dimensions,
                        galleryItem?.location,
                        galleryItem?.date
                    )
                )
                Toast.makeText(context, R.string.added_to_favorites, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getGalleryDetail() {
        gallery_detail_progressbar.visibility = View.VISIBLE

        id?.let {
            galleryDetailViewModel.getData(it)
        }
    }

    private fun displayArtwork() {
        galleryItem?.title?.let { (activity as MainActivity).setToolbarTitle(it) }

        artwork_title.text = galleryItem?.title
        artwork_artist_name.text = galleryItem?.artistDisplayName
        artwork_artist_bio.text = galleryItem?.artistDisplayBio
        artwork_dimensions.text = galleryItem?.dimensions
        artwork_location.text = galleryItem?.location
        artwork_date.text = galleryItem?.date

        context?.let {
            Glide.with(it)
                .load(galleryItem?.image)
                .placeholder(R.drawable.ic_placeholder)
                .into(gallery_detail_image)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    companion object {
        @JvmStatic
        fun newInstance(id: Long) =
            GalleryDetailFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_ID, id)
                }
            }
    }
}
