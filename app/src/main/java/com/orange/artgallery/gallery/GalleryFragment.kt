package com.orange.artgallery.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.orange.artgallery.MainActivity
import com.orange.artgallery.R
import kotlinx.android.synthetic.main.fragment_gallery.*
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Use the [GalleryFragment.newInstance] factory method to
 * create an instance of gallery list.
 */
class GalleryFragment : Fragment() {
    private val galleryViewModel by viewModel<GalleryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gridLayoutManager = GridLayoutManager(context, 2)
        gallery_fragment_recycler_view.layoutManager = gridLayoutManager

        galleryViewModel.data.observe(this, Observer {
            context?.let { ctx ->
                it.let {
                    val galleryAdapter = GalleryAdapter(ctx, it) { id ->
                        (activity as MainActivity).openGalleryDetailFragment(id)
                    }
                    gallery_fragment_recycler_view.adapter = galleryAdapter
                }
            }
        })

        galleryViewModel.isLoading.observe(this, Observer {
            it?.let {
                gallery_progressbar.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        galleryViewModel.showError.observe(this, Observer {
            it?.let {
                gallery_error.visibility = if (it) View.VISIBLE else View.GONE
            }
        })
        getData()
    }

    private fun getData() {
        gallery_progressbar.visibility = View.VISIBLE
        galleryViewModel.getData()
    }

    companion object {
        @JvmStatic
        fun newInstance() = GalleryFragment()
    }
}
