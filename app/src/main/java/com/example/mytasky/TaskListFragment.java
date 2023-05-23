package com.example.mytasky;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class TaskListFragment extends Fragment {

    private ListView listViewTasks;
    private TaskAdapter taskAdapter;
    private List<Task> taskList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_task_list, container, false);

        listViewTasks = rootView.findViewById(R.id.listViewTasks);
        taskList = new ArrayList<>();
        taskAdapter = new TaskAdapter(requireContext(), taskList);
        listViewTasks.setAdapter(taskAdapter);

        return rootView;
    }
}