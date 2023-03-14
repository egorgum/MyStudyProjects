package com.example.humblr.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.humblr.R
import com.example.humblr.databinding.FragmentProfileBinding
import com.example.humblr.recycler.MyFriendsAdapter
import com.example.humblr.sharedPrefs.SharedPrefToken
import com.example.humblr.viewModels.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findNavController().clearBackStack(R.id.nav_host_fragment_activity_main)
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            //Получение информации и её обработка
            try {
                viewModel.loading()
                viewModel.getMeInfo(requireContext())
                viewModel.getMyFriends(requireContext())
                binding.rv.adapter = MyFriendsAdapter(viewModel.friends.value,
                    onClick = { username -> goToUser(username) })
                binding.tvName.text = viewModel.meInfo.value!!.name
                binding.tvGold.text =
                    if (viewModel.meInfo.value!!.is_gold) {
                        getString(R.string.gold)
                    } else {
                        getString(R.string.not_gold)
                    }
                binding.tvKarma.text = "${getString(R.string.karma)} ${viewModel.meInfo.value!!.total_karma}"

                Glide
                    .with(binding.avatar.context)
                    .load(viewModel.meInfo.value!!.icon_img.substringBefore("?"))
                    .into(binding.avatar)
                viewModel.unloading()
            }
            catch (e:Exception){
                Toast.makeText(requireContext(),getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show()
            }
        }

        //Наблюдение за состоянием прогресса
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.isLoading.collect{
                when(it){
                    true -> {
                        binding.rv.visibility = View.GONE
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    false ->{
                        binding.rv.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                    }
                }
            }
        }
        //Выход из аккаунта
        binding.btExit.setOnClickListener {
            AlertDialog
                .Builder(requireContext())
                .setTitle(R.string.exit)
                .setMessage(R.string.message)
                .setPositiveButton(R.string.yes){_, _ -> onClickYes()}
                .setNegativeButton(R.string.no){dialog,_ -> dialog.cancel()}.show()
        }
    }
    //Подтверждение "Выйти"
    private fun onClickYes(){
        SharedPrefToken(requireContext()).clearText()
        findNavController().navigate(R.id.action_navigation_profile_to_authFragment)
    }
    //Переход к другу
    private fun goToUser(name: String){
        val bundle = Bundle().apply {
            putString("name", name)
        }
        findNavController().navigate(R.id.userFragment,bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}