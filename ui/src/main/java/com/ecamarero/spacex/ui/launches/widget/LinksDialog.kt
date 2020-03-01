package com.ecamarero.spacex.ui.launches.widget

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ecamarero.spacex.ui.launches.model.LaunchUI
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textview.MaterialTextView
import kotlinx.android.synthetic.main.fragment_links_dialog.*


class LinksDialog : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            com.ecamarero.spacex.ui.R.layout.fragment_links_dialog,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val wikipediaLink = arguments?.getString(WIKIPEDIA_KEY)
        setLinkToView(wikipediaLink, wikipedia_link)

        val videoLink = arguments?.getString(VIDEO_KEY)
        setLinkToView(videoLink, video_link)

        val articleLink = arguments?.getString(ARTICLE_KEY)
        setLinkToView(articleLink, article_link)

        if (wikipediaLink == null && videoLink == null && articleLink == null) {
            no_data_text.visibility = View.VISIBLE
        } else {
            no_data_text.visibility = View.GONE
        }
    }

    private fun setLinkToView(
        url: String?,
        view: MaterialTextView
    ) {
        if (url != null) {
            view.visibility = View.VISIBLE
            view.setOnClickListener {
                Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(url)
                    startActivity(this)
                }
            }
        } else {
            view.visibility = View.GONE
        }
    }

    companion object {
        internal fun newInstance(launchUI: LaunchUI): LinksDialog {
            val linksDialog = LinksDialog()
            val arguments = Bundle()
            arguments.putString(WIKIPEDIA_KEY, launchUI.wikipediaUrlString)
            arguments.putString(VIDEO_KEY, launchUI.videoPagesUrlString)
            arguments.putString(ARTICLE_KEY, launchUI.articleUrlString)
            linksDialog.arguments = arguments
            return linksDialog
        }

        internal val TAG = LinksDialog::class.java.simpleName

        private const val WIKIPEDIA_KEY = "WIKIPEDIA_LINK"
        private const val VIDEO_KEY = "VIDEO_LINK"
        private const val ARTICLE_KEY = "ARTICLE_LINK"
    }

}