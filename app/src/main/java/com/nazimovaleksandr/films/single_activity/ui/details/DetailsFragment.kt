package com.nazimovaleksandr.films.single_activity.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.nazimovaleksandr.films.databinding.FragmentDetailsBinding
import com.nazimovaleksandr.films.single_activity.data.entities.ui.MovieUI
import com.nazimovaleksandr.films.single_activity.SingleActivity
import com.nazimovaleksandr.films.single_activity.SingleActivity.Companion.KEY_TOOLBAR

class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var movie: MovieUI? = null

        arguments?.apply {
            movie = getSerializable(SingleActivity.KEY_MOVIE) as MovieUI

            movie?.let { movie ->
                binding.fullImage.setImageResource(movie.image)
                binding.fullText.text = movie.details
                binding.comment.setText(movie.comment)
                binding.checkbox.isChecked = movie.isFavorite
            }
        }

        binding.apply {
            buttonEmail.setOnClickListener {
                sendLink("mailto:")
            }
            buttonSms.setOnClickListener {
                sendLink("sms:")
            }
            buttonMessage.setOnClickListener {
                sendLink("")
            }
        }

        binding.checkbox.setOnCheckedChangeListener { _, isLike ->
            movie?.isFavorite = isLike

            setFragmentResult(
                SingleActivity.KEY_DETAILS,
                Bundle().apply {
                    putSerializable(SingleActivity.KEY_MOVIE, movie as java.io.Serializable)
                }
            )
        }

        binding.comment.addTextChangedListener {
            movie?.comment = it.toString()

            setFragmentResult(
                SingleActivity.KEY_DETAILS,
                Bundle().apply {
                    putSerializable(SingleActivity.KEY_MOVIE, movie as java.io.Serializable)
                }
            )
        }

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onStart() {
        super.onStart()
        requireActivity().supportFragmentManager.setFragmentResult(
            KEY_TOOLBAR,
            Bundle().apply {
                putBoolean(KEY_TOOLBAR, false)
            }
        )
    }

    override fun onStop() {
        super.onStop()
        requireActivity().supportFragmentManager.setFragmentResult(
            KEY_TOOLBAR,
            Bundle().apply {
                putBoolean(KEY_TOOLBAR, true)
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun sendLink(data: String) {
        val intent = Intent().apply {
            action = if (data != "") Intent.ACTION_SENDTO else Intent.ACTION_SEND

            putExtra(Intent.EXTRA_TEXT, "Присоединяйся :) http://otus.ru/")
            putExtra(Intent.EXTRA_SUBJECT, "Films App")

            if (data != "") this.data = Uri.parse(data) else type = "text/plain"
        }

        startActivity(intent)
    }
}