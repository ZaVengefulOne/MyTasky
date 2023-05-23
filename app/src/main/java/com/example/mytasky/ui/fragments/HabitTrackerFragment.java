package com.example.mytasky.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mytasky.R;

import java.util.ArrayList;
import java.util.List;

public class HabitTrackerFragment extends Fragment {

    private LinearLayout layoutHabitTracker;
    private List<CheckBox> checkBoxList;

    private Button buttonBack;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_habit_tracker, container, false);

        layoutHabitTracker = rootView.findViewById(R.id.layoutHabitTracker);
        buttonBack = rootView.findViewById(R.id.button_back);
        checkBoxList = new ArrayList<>();

        // Создаем и добавляем CheckBox'ы для отслеживания привычек
        for (int i = 1; i <= 7; i++) {
            CheckBox checkBox = createCheckBox(i);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // Обработка изменения состояния Checkbox'а
                    Toast.makeText(getContext(),"Привычка выработана!", Toast.LENGTH_LONG).show();
                }
            });
            checkBoxList.add(checkBox);
            layoutHabitTracker.addView(checkBox);
        }

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.fragment_container, MenuFragment.class, null)
                        .commit();
            }
        });
        return rootView;
    }

    private CheckBox createCheckBox(int dayOfWeek) {
        CheckBox checkBox = new CheckBox(requireContext());
        checkBox.setText(getDayOfWeekString(dayOfWeek));
        checkBox.setTextSize(16);
        checkBox.setPadding(8, 8, 8, 8);
        checkBox.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return checkBox;
    }

    private String getDayOfWeekString(int dayOfWeek) {
        String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        return daysOfWeek[dayOfWeek - 1];
    }


}
