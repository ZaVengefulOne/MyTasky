package com.example.mytasky.ui.stateholders.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytasky.data.database.entity.TaskEntity;
import com.example.mytasky.data.datasourse.TasksDataSource;

import java.util.List;

public class TaskAdapter extends ArrayAdapter<TaskEntity> {

    private Context context;
    private List<TaskEntity> taskList;


    public TaskAdapter(Context context, List<TaskEntity> taskList) {
        super(context, 0, taskList);
        this.context = context;
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
//            timeButton = LayoutInflater.from(context).inflate(android.R.layout.)
        }
        TasksDataSource tasksDataSource = TasksDataSource.getInstance(getContext());
        TaskEntity currentTask = taskList.get(position);

        TextView textViewTask = itemView.findViewById(android.R.id.text1);
        textViewTask.setText(currentTask.getTaskText());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskList.remove(position);
                tasksDataSource.deleteTask(position);
                notifyDataSetChanged();
            }
        });
        return itemView;
    }
//    @NonNull
//    @Override
//    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return new TaskViewHolder(ItemTaskListBinding.inflate(
//                LayoutInflater.from(parent.getContext()),
//                parent,
//                false
//        ));
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull TaskAdapter.TaskViewHolder holder, int position) {
//        TaskEntity task = taskList.get(position);
//        holder.b
//    }

//    @Override
//    public int getItemCount() {
//        return 0;
//    }
//
}