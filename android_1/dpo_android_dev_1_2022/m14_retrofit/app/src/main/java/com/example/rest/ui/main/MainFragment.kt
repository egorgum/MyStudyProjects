package com.example.rest.ui.main
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.rest.Data.Result
import com.example.rest.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels{Factory()}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDisplay()
        binding.btRestart.setOnClickListener {
            getDisplay()
        }


    }
    private fun displayData(user: Result?){
        Log.d(TAG, "Пришел $user")
        if(user != null){
            binding.fName.text = user.name.first
            binding.fName.visibility = VISIBLE

            binding.lName.text = user.name.last
            binding.lName.visibility = VISIBLE

            binding.email.text = user.email
            binding.email.visibility = VISIBLE

            binding.phone.text = user.phone
            binding.phone.visibility = VISIBLE

            binding.btRestart.visibility = VISIBLE

            Glide.with(binding.avatar).load(user.picture.thumbnail).into(binding.avatar)
            binding.avatar.visibility = VISIBLE

        }
    }
    private fun getDisplay(){
        viewModel.dataIsOk()
        viewModel.userData.observe(viewLifecycleOwner){
            displayData(it.results.first())
        }
    }
}