package com.nazimovaleksandr.films.details

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.nazimovaleksandr.films.R
import com.nazimovaleksandr.films.main.MainActivity
import com.nazimovaleksandr.films.main.movie_list.MovieItem

class DetailsActivity : AppCompatActivity() {

    private val imageFilm: ImageView by lazy { findViewById(R.id.full_image) }
    private val detailsFilm: TextView by lazy { findViewById(R.id.full_text) }

    private val buttonEmail: Button by lazy { findViewById(R.id.button_email) }
    private val buttonSms: Button by lazy { findViewById(R.id.button_sms) }
    private val buttonMessage: Button by lazy { findViewById(R.id.button_message) }

    private val isLike: CheckBox by lazy { findViewById(R.id.checkbox) }
    private val comment: EditText by lazy { findViewById(R.id.comment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        supportActionBar?.title = getString(R.string.details)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        intent.apply {
            val movie = getSerializableExtra(MainActivity.KEY_MOVIE) as MovieItem

            imageFilm.setImageResource(movie.image)
            detailsFilm.text = movie.details
        }

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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        setResult(
            RESULT_OK,
            Intent().apply {
                putExtra(MainActivity.KEY_IS_LIKE, isLike.isChecked)
                putExtra(MainActivity.KEY_COMMENT, comment.text.toString())
            }
        )

        super.onBackPressed()
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