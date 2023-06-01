package com.example.mytasky.ui.fragments;


import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.example.mytasky.R;
import com.example.mytasky.data.database.TasksDataBase;
import com.example.mytasky.data.database.entity.TaskEntity;
import com.example.mytasky.data.datasourse.TasksDataSource;
import com.example.mytasky.data.models.Task;
import com.example.mytasky.databinding.FragmentMenuBinding;
import com.example.mytasky.ui.stateholders.TaskAdapter;
import com.example.mytasky.ui.stateholders.TaskRecycler;
import com.example.mytasky.ui.stateholders.viewModels.MenuViewModel;
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

public class MenuFragment extends Fragment {

    private EditText editTextTask;
    private Button buttonAddTask;
    private Button buttonHabit;
    private ListView listViewTasks;
    private TaskAdapter taskAdapter;
    private List<TaskEntity> taskList;
    private MenuViewModel model;
    private FragmentMenuBinding binding;
    private TaskRecycler adapter;

    public MenuFragment()
    {
        super(R.layout.fragment_menu);
    }

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
        binding = FragmentMenuBinding.inflate(getLayoutInflater());
        editTextTask = binding.editTextTask;
        buttonAddTask = binding.buttonAddTask;
        buttonHabit = binding.habit;
        taskList = new ArrayList<>();
        adapter = new TaskRecycler(taskList);
        binding.RecyclerTasks.setAdapter(adapter);
        binding.RecyclerTasks.setLayoutManager(new LinearLayoutManager(getActivity()));

        loadData();
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            MaterialCalendarView calendarView = binding.calendarView;
            TimePicker timePicker = binding.timePicker;
            int hour = timePicker.getHour();
            int minute = timePicker.getMinute();
            // Создаем экземпляр LocalTime с использованием выбранного времени
            LocalTime localTime = LocalTime.of(hour, minute);
            LocalDate localDate = LocalDate.now();
            LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
            TasksDataSource tasksDataSource = TasksDataSource.getInstance(getContext());
            model = new ViewModelProvider(this).get(MenuViewModel.class);
            model.listLiveData.observe(getViewLifecycleOwner(), new Observer<List<TaskEntity>>()
                    {

                        @Override
                        public void onChanged(List<TaskEntity> taskList) {
//                            adapter.setData(taskEntities);

                            adapter.onTaskListClickListener = new TaskRecycler.OnTaskListClickListener() {
                                @Override
                                public void OnTaskItemListClickListener(int position) {
                                    tasksDataSource.deleteTask(position);
                                    taskList.remove(position);
                                    adapter.notifyDataSetChanged();
                                }
                            };
                            buttonAddTask.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String taskText = editTextTask.getText().toString().trim();
                                    if (!taskText.isEmpty()) {
                                        CalendarDay selectedDate = calendarView.getSelectedDate();
                                        // Создаем экземпляр TaskEntity на основе Task
                                        TaskEntity taskEntity = new TaskEntity(taskText, selectedDate, localDateTime);
                                        // Получаем экземпляр TasksDataSource из TasksDataBase
                                        // Вызываем метод addTask() на экземпляре TasksDataSource, передавая в него экземпляр TaskEntity
                                        tasksDataSource.addTask(taskEntity);
                                        taskList.add(taskEntity);
                                        adapter.notifyDataSetChanged();
                                        editTextTask.setText("");
                                    } else {
                                        Toast.makeText(getActivity(), "Please enter a task", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }
                    }
            );





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
        }
    }


    private void loadData() {
//        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("TaskManagerPrefs", MODE_PRIVATE);
        SharedPreferences sharedPrefRead =
                requireActivity().getPreferences(Context.MODE_PRIVATE);
        String taskListJson = sharedPrefRead.getString("taskList", null);
        if (taskListJson != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<TaskEntity>>() {}.getType();
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
