package com.example.mytasky.ui.fragments;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.mytasky.R;
import com.example.mytasky.data.models.Task;
import com.example.mytasky.ui.stateholders.TaskAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment {

    private EditText editTextTask;
    private Button buttonAddTask;
    private Button buttonHabit;
    private ListView listViewTasks;
    private TaskAdapter taskAdapter;
    private List<Task> taskList;
    private void saveData() {
//        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("TaskManagerPrefs",MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        Gson gson = new Gson();
//        String taskListJson = gson.toJson(taskList);
//        editor.putString("taskList", taskListJson);
//
        SharedPreferences sharedPrefWrite =
                requireActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefWrite.edit();
        Gson gson = new Gson();
        String taskListJson = gson.toJson(taskList);
        editor.putString("taskList", taskListJson);
        editor.apply();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_menu, container, false);
        editTextTask = rootView.findViewById(R.id.editTextTask);
        buttonAddTask = rootView.findViewById(R.id.buttonAddTask);
        buttonHabit = rootView.findViewById(R.id.habit);
        loadData();
        listViewTasks = rootView.findViewById(R.id.listViewTasks);
        MaterialCalendarView calendarView = rootView.findViewById(R.id.calendarView);
        taskAdapter = new TaskAdapter(requireContext(), taskList);
        listViewTasks.setAdapter(taskAdapter);
        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskText = editTextTask.getText().toString().trim();
                if (!taskText.isEmpty()) {
                    CalendarDay selectedDate = calendarView.getSelectedDate();
                    Task task = new Task(taskText, selectedDate);
                    taskList.add(task);
                    taskAdapter.notifyDataSetChanged();
                    editTextTask.setText("");
                } else {
                    Toast.makeText(getActivity(), "Please enter a task", Toast.LENGTH_SHORT).show();
                }
            }
        });
        buttonHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.fragment_container, HabitTrackerFragment.class, null)
                        .commit();
            }
        });
        return rootView;
    }


    private void loadData() {
//        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("TaskManagerPrefs", MODE_PRIVATE);
        SharedPreferences sharedPrefRead =
                requireActivity().getPreferences(Context.MODE_PRIVATE);
        String taskListJson = sharedPrefRead.getString("taskList", null);
        if (taskListJson != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Task>>() {}.getType();
            taskList = gson.fromJson(taskListJson, type);
        } else {
            taskList = new ArrayList<>();
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        saveData();
    }
}
