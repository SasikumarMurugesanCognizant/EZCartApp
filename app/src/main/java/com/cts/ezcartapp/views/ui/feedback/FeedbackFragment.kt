package com.cts.ezcartapp.views.ui.feedback

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.cts.ezcartapp.data.entities.FeedBackData
import com.cts.ezcartapp.databinding.FeedbackFragmentBinding
import com.cts.ezcartapp.di.viewmodelfactory.ViewModelProviderFactory
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class FeedbackFragment : Fragment() {
    private lateinit var feedBackViewModel: FeedbackViewModel
    private var _binding: FeedbackFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory
    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        feedBackViewModel =
            ViewModelProvider(this, viewModelProviderFactory)[FeedbackViewModel::class.java]
        _binding = FeedbackFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSubmitFeedBack.setOnClickListener {
            val rating = binding.ratingBar.rating.toString()
            val feedBackMessage = binding.etFeedback.text.toString()
            feedBackViewModel.addFeedBackToDB(
                FeedBackData(
                    rating = rating,
                    feedback = feedBackMessage
                )
            )
            displayMessage()
            Navigation.findNavController(binding.root).navigateUp()
        }
    }

    private fun displayMessage() {
        activity?.let {
            Toast.makeText(it, "Feedback Added Successfully", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}