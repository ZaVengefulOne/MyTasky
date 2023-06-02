package com.example.mytasky.ui.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mytasky.R;
import com.example.mytasky.data.database.entity.TaskEntity;
import com.example.mytasky.databinding.FragmentAddTaskBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AddTaskFragment extends Fragment {

//    private ListView listViewTasks;
//    private TaskRecycler taskAdapter;
    private Button addTask;
    private EditText TaskText;
    private List<TaskEntity> taskList;
    private FragmentAddTaskBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddTaskBinding.inflate(getLayoutInflater());
        addTask = binding.addButton;
        TaskText = binding.editTextTask;
        loadData();
        return binding.getRoot();
    }
    public void onClick(View v) {
        Bundle result = new Bundle();
        result.putString("bundleKey", "result");
        getParentFragmentManager().setFragmentResult("requestKey", result);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AddTaskFragment fragment = new AddTaskFragment();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


            addTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MaterialCalendarView calendarView = binding.calendarView;
                    TimePicker timePicker = binding.timePicker;
                    int hour = timePicker.getHour();
                    int minute = timePicker.getMinute();
                    // Создаем экземпляр LocalTime с использованием выбранного времени
                    LocalTime localTime = LocalTime.of(hour, minute);
                    LocalDate localDate = LocalDate.now();
                    LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
                    String taskText = TaskText.getText().toString();
                    if (!taskText.isEmpty()) {
                        CalendarDay selectedDate = calendarView.getSelectedDate();
                        if (selectedDate == null)
                        {
                            Toast.makeText(getActivity(), "Please enter an expiration date", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            // Создаем экземпляр TaskEntity на основе Task
                            TaskEntity taskEntity = new TaskEntity(taskText, selectedDate, localDateTime);
                            // Получаем экземпляр TasksDataSource из TasksDataBase
                            // Вызываем метод addTask() на экземпляре TasksDataSource, передавая в него экземпляр TaskEntity
//                        tasksDataSource.addTask(taskEntity);
                            taskList.add(taskEntity);
//                        adapter.notifyDataSetChanged();
//                            TaskText.setText("");
                            Bundle bundle = new Bundle();
                            bundle.putParcelableArrayList("taskList", new ArrayList<>(taskList));
                            fragment.setArguments(bundle);
                            saveData();
                            requireActivity().getSupportFragmentManager().beginTransaction()
                                    .addToBackStack(null)
                                    .replace(R.id.fragment_container, MenuFragment.class, null)
                                    .commit();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Please enter a task", Toast.LENGTH_SHORT).show();
                    }
                }
            });



        }
    }

    private void loadData() {
        SharedPreferences sharedPrefRead =
                requireActivity().getSharedPreferences(getString(R.string.com_example_mytasky_SHAREDTASKS), Context.MODE_PRIVATE);
        String taskListJson = sharedPrefRead.getString("taskList", null);
        if (taskListJson != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<TaskEntity>>() {}.getType();
            taskList = gson.fromJson(taskListJson, type);
        } else {
            taskList = new ArrayList<>();
        }
    }

    private void saveData() {
        SharedPreferences sharedPrefWrite =
                requireActivity().getSharedPreferences(getString(R.string.com_example_mytasky_SHAREDTASKS), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefWrite.edit();
        Gson gson = new Gson();
        String taskListJson = gson.toJson(taskList);
        editor.putString("taskList", taskListJson);
        editor.apply();
    }
}