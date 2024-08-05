package com.adrian.culinary

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import androidx.fragment.app.DialogFragment
import com.google.firebase.database.FirebaseDatabase

class AddReviewDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = requireActivity().layoutInflater.inflate(R.layout.activity_add_review, null)


        val dialog = Dialog(requireActivity())
        dialog.setContentView(view)


        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val usernameInput: EditText = view.findViewById(R.id.username_input)
        val reviewText: EditText = view.findViewById(R.id.review_text)
        val reviewRating: RatingBar = view.findViewById(R.id.review_rating)
        val submitButton: Button = view.findViewById(R.id.submit_review_button)

        submitButton.setOnClickListener {
            val userName = usernameInput.text.toString()
            val reviewContent = reviewText.text.toString()
            val rating = reviewRating.rating

            if (reviewContent.isNotEmpty() && userName.isNotEmpty()) {
                submitReview(userName, reviewContent, rating) {
                    dismiss()
                }
            } else {
                if (reviewContent.isEmpty()) reviewText.error = "Review cannot be empty"
                if (userName.isEmpty()) usernameInput.error = "Username cannot be empty"
            }
        }

        return dialog
    }

    private fun submitReview(userName: String, reviewContent: String, rating: Float, onSuccess: () -> Unit) {
        val database = FirebaseDatabase.getInstance("https://culinary-21f0b-default-rtdb.asia-southeast1.firebasedatabase.app")
        val reviewId = database.reference.child("reviews").push().key ?: return
        val review = Review(reviewId, reviewContent, rating, userName)

        database.reference.child("reviews").child(reviewId).setValue(review)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    targetFragment?.let { fragment ->
                        if (fragment is HomeFragment) {
                            fragment.refreshReviews()
                        }
                    }
                    onSuccess()
                }
            }
    }
}
