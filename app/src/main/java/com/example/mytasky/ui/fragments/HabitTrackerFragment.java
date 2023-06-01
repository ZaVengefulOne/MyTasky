package com.example.mytasky.ui.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.mytasky.R;
import com.example.mytasky.databinding.FragmentHabitTrackerBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HabitTrackerFragment extends Fragment {

    public FragmentHabitTrackerBinding binding;
    public LinearLayout layoutHabitTracker;
    public List<String> habits;


    private void saveData() {
        SharedPreferences sharedPrefWrite =
                requireActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefWrite.edit();
        Gson gson = new Gson();
        String habitsjson = gson.toJson(habits);
        editor.putString("habits", habitsjson);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPrefRead =
                requireActivity().getPreferences(Context.MODE_PRIVATE);
        String habitsjson = sharedPrefRead.getString("habits", null);
        if (habitsjson != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<String>>() {}.getType();
            habits = gson.fromJson(habitsjson, type);
        } else {
            habits = new ArrayList<>();
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHabitTrackerBinding.inflate(getLayoutInflater());
        layoutHabitTracker = binding.habitLayout;
        loadData();
//        habits = new ArrayList<>();
        for (int i = 1; i <= habits.size(); i++) {
            LinearLayout Checkbox_container = new LinearLayout(requireContext());
            Checkbox_container.setOrientation(LinearLayout.HORIZONTAL);
            CheckBox checkBox = new CheckBox(requireContext());
            String checkBoxText = habits.get(i - 1);
            checkBox.setText(checkBoxText);
            checkBox.setTextSize(16);
            checkBox.setPadding(8, 8, 8, 8);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 8, 0, 0);
            checkBox.setLayoutParams(params);
            // Создаем кнопку с иконкой крестика
            ImageButton deleteButton = new ImageButton(requireContext());
            deleteButton.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
            LinearLayout.LayoutParams deleteButtonParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            deleteButton.setLayoutParams(deleteButtonParams);

            // Добавляем обработчик нажатия на кнопку
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Удаляем контейнер из макета
                    layoutHabitTracker.removeView(Checkbox_container);
                    habits.remove(checkBoxText);
                }
            });

            // Добавляем чек-бокс и кнопку в контейнер
            Checkbox_container.addView(checkBox);
            Checkbox_container.addView(deleteButton);

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // Обработка изменения состояния Checkbox'а
                    Toast.makeText(getContext(),"Привычка поддержана!", Toast.LENGTH_LONG).show();
                    checkBox.setPaintFlags(checkBox.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    if (!isChecked)
                    {
                        checkBox.setPaintFlags(0);
                    }

                }
            });
            LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            containerParams.setMargins(0, 8, 0, 0);
            Checkbox_container.setLayoutParams(containerParams);

            // Добавляем контейнер в макет
            layoutHabitTracker.addView(Checkbox_container);
            layoutHabitTracker.setOrientation(LinearLayout.VERTICAL);
        }
//
//        // Создаем и добавляем CheckBox'ы для отслеживания привычек
//        for (int i = 1; i <= 7; i++) {
//            CheckBox checkBox = createCheckBox();
//
//
//        }



        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.fragment_container, MenuFragment.class, null)
                        .commit();
//                Navigation.findNavController(v).navigate(R.id.action_habits_to_menu);
            }
        });

        binding.buttonAddHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createCheckBox();
            }
        });

        return binding.getRoot();
    }

    public CheckBox createCheckBox() {
        LinearLayout container = new LinearLayout(requireContext());
        container.setOrientation(LinearLayout.HORIZONTAL);
        CheckBox checkBox = new CheckBox(requireContext());
        String checkBoxText = binding.addhabitText.getText().toString();
        checkBox.setText(binding.addhabitText.getText());
        checkBox.setTextSize(16);
        checkBox.setPadding(8, 8, 8, 8);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 8, 0, 0);
        checkBox.setLayoutParams(params);
        // Создаем кнопку с иконкой крестика
        ImageButton deleteButton = new ImageButton(requireContext());
        deleteButton.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
        LinearLayout.LayoutParams deleteButtonParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        deleteButton.setLayoutParams(deleteButtonParams);

        // Добавляем обработчик нажатия на кнопку
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Удаляем контейнер из макета
                layoutHabitTracker.removeView(container);
                habits.remove(checkBoxText);
            }
        });

        // Добавляем чек-бокс и кнопку в контейнер
        container.addView(checkBox);
        container.addView(deleteButton);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Обработка изменения состояния Checkbox'а
                Toast.makeText(getContext(),"Привычка поддержана!", Toast.LENGTH_LONG).show();
                checkBox.setPaintFlags(checkBox.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                if (!isChecked)
                {
                   checkBox.setPaintFlags(0);
                }

            }
        });
        LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        containerParams.setMargins(0, 8, 0, 0);
        container.setLayoutParams(containerParams);

        // Добавляем контейнер в макет
        layoutHabitTracker.addView(container);
        habits.add(binding.addhabitText.getText().toString());
        binding.addhabitText.setText("");
        layoutHabitTracker.setOrientation(LinearLayout.VERTICAL);
//        layoutHabitTracker.addView(checkBox);
        return checkBox;
    }

    private String getHabitString(int habit) {
        String[] habits = {"Почистить зубы", "Лечь спать вовремя", "Сделать зарядку", "Поесть фруктов", "Выпить 1.5 литра воды"};
        return habits[habit - 1];
    }


}
