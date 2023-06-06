package com.example.mytasky.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytasky.R;
import com.example.mytasky.databinding.FragmentAdminBinding;
import com.example.mytasky.ui.stateholders.adapters.AdminAdapter;

public class AdminFragment extends Fragment {

    private FragmentAdminBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAdminBinding.inflate(inflater,container,false);
        String[] data = {"Alex", "Vengeful", "Nakarat", "Korpalo", "Roman", "Lena", "Plusha"};
        AdminAdapter adapter = new AdminAdapter(data);
        binding.AdminRecycler.setAdapter(adapter);
        binding.AdminRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.fragment_container, MenuFragment.class, null)
                        .commit();
            }
        });
    }
}
