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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.example.mytasky.R;
import com.example.mytasky.data.database.entity.TaskEntity;
import com.example.mytasky.data.datasourse.TasksDataSource;
import com.example.mytasky.databinding.FragmentMenuBinding;
import com.example.mytasky.ui.stateholders.adapters.TaskRecycler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment {

    private Button buttonAddTask;
    private Button buttonHabit;
//    private List<TaskEntity> taskList;
private List<TaskEntity> taskList = new ArrayList<>();
    private TaskRecycler adapter;


    public MenuFragment()
    {
        super(R.layout.fragment_menu);
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        com.example.mytasky.databinding.FragmentMenuBinding binding = FragmentMenuBinding.inflate(getLayoutInflater());
        loadData();
        buttonAddTask = binding.addTaskButton;
        buttonHabit = binding.habitButton;
        adapter = new TaskRecycler(taskList);
        binding.RecyclerTasks.setAdapter(adapter);
        binding.RecyclerTasks.setLayoutManager(new LinearLayoutManager(getActivity()));
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            TasksDataSource tasksDataSource = TasksDataSource.getInstance(getContext());
//            MenuViewModel model = new ViewModelProvider(this).get(MenuViewModel.class);
            adapter.onTaskListClickListener = new TaskRecycler.OnTaskListClickListener() {
                @Override
                public void OnTaskItemListClickListener(int position) {
                    if (taskList != null && !taskList.isEmpty() && position >= 0 && position < taskList.size()) {
                        tasksDataSource.deleteTask(position);
                        taskList.remove(position);
                        adapter.notifyDataSetChanged();
                        saveData();
                    }
                }
            };
//            model.listLiveData.observe(getViewLifecycleOwner(), new Observer<List<TaskEntity>>()
//                    {
//                        @Override
//                        public void onChanged(List<TaskEntity> taskList) {
//
////                            adapter.onTaskListClickListener = new TaskRecycler.OnTaskListClickListener() {
////                                @Override
////                                public void OnTaskItemListClickListener(int position) {
////                                    if (taskList != null && !taskList.isEmpty() && position >= 0 && position < taskList.size()) {
////                                        tasksDataSource.deleteTask(position);
////                                        taskList.remove(position);
////                                        adapter.notifyDataSetChanged();
////                                        saveData();
////                                    }
////                                }
////                            };
//                            }
//                        });
                    }



            buttonAddTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveData();
                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .addToBackStack(null)
                            .replace(R.id.fragment_container, AddTaskFragment.class, null)
                            .commit();
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
//                    Navigation.findNavController(v).navigate(R.id.action_menu_to_habits);
                }
            });
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
//private void loadData() {
//    SharedPreferences sharedPrefRead =
//            requireActivity().getSharedPreferences(getString(R.string.com_example_mytasky_SHAREDTASKS), Context.MODE_PRIVATE);
//    String taskListJson = sharedPrefRead.getString("taskList", null);
//    if (taskListJson != null) {
//        Gson gson = new Gson();
//        Type type = new TypeToken<ArrayList<TaskEntity>>() {}.getType();
//        ArrayList<TaskEntity> savedTaskList = gson.fromJson(taskListJson, type);
//        if (taskList == null) {
//            taskList = new ArrayList<>();
//        } else {
//            taskList.clear(); // очищаем список перед добавлением новых элементов
//        }
//        taskList.addAll(savedTaskList); // добавляем сохраненные элементы в список
//    } else {
//        taskList = new ArrayList<>();
//    }
//}
    @Override
    public void onStop() {
        super.onStop();
//        saveData();
    }
}
