package com.adrian.culinary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class HomeFragment : Fragment() {

    private lateinit var reviewsRecyclerView: RecyclerView
    private lateinit var reviewsAdapter: ReviewsAdapter
    private lateinit var database: FirebaseDatabase
    private val reviewsList = mutableListOf<Review>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        reviewsRecyclerView = view.findViewById(R.id.reviewsRecyclerViewer)
        reviewsRecyclerView.layoutManager = LinearLayoutManager(context)
        reviewsAdapter = ReviewsAdapter(reviewsList)
        reviewsRecyclerView.adapter = reviewsAdapter

        view.findViewById<Button>(R.id.add_review_button).setOnClickListener {
            // Show the AddReviewDialogFragment
            val dialog = AddReviewDialogFragment()
            dialog.setTargetFragment(this, 0) // Set target fragment for communication
            dialog.show(parentFragmentManager, "AddReviewDialogFragment")
        }

        database = FirebaseDatabase.getInstance("https://culinary-21f0b-default-rtdb.asia-southeast1.firebasedatabase.app")
        loadReviews()

        return view
    }

    private fun loadReviews() {
        database.reference.child("reviews").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                reviewsList.clear()
                for (reviewSnapshot in snapshot.children) {
                    val review = reviewSnapshot.getValue(Review::class.java)
                    review?.let { reviewsList.add(it) }
                }
                reviewsAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }

    fun refreshReviews() {
        loadReviews() // Refresh the list of reviews
    }
}
