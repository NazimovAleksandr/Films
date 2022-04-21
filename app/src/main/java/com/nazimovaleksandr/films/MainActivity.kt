package com.nazimovaleksandr.films

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {

    companion object {
        const val KEY_IMAGE = "KEY_IMAGE"
        const val KEY_DETAILS = "KEY_DETAILS"

        const val KEY_IS_LIKE = "KEY_IS_LIKE"
        const val KEY_COMMENT = "KEY_COMMENT"

        val TAG = MainActivity::class.simpleName
    }

    private val nameFilm1: TextView by lazy { findViewById(R.id.name_film_1) }
    private val nameFilm2: TextView by lazy { findViewById(R.id.name_film_2) }
    private val nameFilm3: TextView by lazy { findViewById(R.id.name_film_3) }
    private val nameFilm4: TextView by lazy { findViewById(R.id.name_film_4) }

    private val detailsFilm1: Button by lazy { findViewById(R.id.details_film_1) }
    private val detailsFilm2: Button by lazy { findViewById(R.id.details_film_2) }
    private val detailsFilm3: Button by lazy { findViewById(R.id.details_film_3) }
    private val detailsFilm4: Button by lazy { findViewById(R.id.details_film_4) }

    private val resultForDetailsActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            Log.d("__$TAG", "isLike: ${it.data?.getBooleanExtra(KEY_IS_LIKE, false)}")
            Log.d("__$TAG", "comment: ${it.data?.getStringExtra(KEY_COMMENT)}")
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        detailsFilm1.setOnClickListener {
            changeTextColor(nameFilm1)
            openDetailsActivity(R.drawable.image_1, R.string.film_details_1)
        }
        detailsFilm2.setOnClickListener {
            changeTextColor(nameFilm2)
            openDetailsActivity(R.drawable.image_2, R.string.film_details_2)
        }
        detailsFilm3.setOnClickListener {
            changeTextColor(nameFilm3)
            openDetailsActivity(R.drawable.image_3, R.string.film_details_3)
        }
        detailsFilm4.setOnClickListener {
            changeTextColor(nameFilm4)
            openDetailsActivity(R.drawable.image_4, R.string.film_details_4)
        }
    }

    private fun changeTextColor(view: TextView) {
        view.setTextColor(application.getColor(R.color.purple_200))
    }

    private fun openDetailsActivity(image: Int, filmDetails: Int) {
        val intent = Intent(this, DetailsActivity::class.java).apply {
            putExtra(KEY_IMAGE, image)
            putExtra(KEY_DETAILS, filmDetails)
        }

//        startActivity(intent)
        resultForDetailsActivity.launch(intent)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(nameFilm1.id.toString(), nameFilm1.currentTextColor)
        outState.putInt(nameFilm2.id.toString(), nameFilm2.currentTextColor)
        outState.putInt(nameFilm3.id.toString(), nameFilm3.currentTextColor)
        outState.putInt(nameFilm4.id.toString(), nameFilm4.currentTextColor)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        savedInstanceState.apply {
            nameFilm1.setTextColor(getInt(nameFilm1.id.toString()))
            nameFilm2.setTextColor(getInt(nameFilm2.id.toString()))
            nameFilm3.setTextColor(getInt(nameFilm3.id.toString()))
            nameFilm4.setTextColor(getInt(nameFilm4.id.toString()))
        }
    }
}