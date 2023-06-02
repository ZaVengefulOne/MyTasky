package com.example.mytasky.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mytasky.R;
import com.example.mytasky.databinding.FragmentAuthBinding;
import com.example.mytasky.ui.stateholders.viewModels.AuthViewModel;

public class AuthFragment extends Fragment {

    private FragmentAuthBinding binding;
    private AuthViewModel viewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAuthBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewModel.loginAccount(
                        binding.LoginText.getText().toString(),
                        binding.PasswordText.getText().toString()
                )){
                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .addToBackStack(null)
                            .replace(R.id.fragment_container, MenuFragment.class, null)
                            .commit();
                }
                if(viewModel.adminLogin(binding.LoginText.getText().toString(),binding.PasswordText.getText().toString())){
                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .addToBackStack(null)
                            .replace(R.id.fragment_container, AdminFragment.class, null)
                            .commit();
                }
            }

        });
    }
}
