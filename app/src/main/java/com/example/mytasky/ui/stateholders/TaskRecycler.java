package com.example.mytasky.ui.stateholders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytasky.data.database.entity.TaskEntity;
import com.example.mytasky.data.datasourse.TasksDataSource;
import com.example.mytasky.databinding.ItemTasklistBinding;

import java.util.List;

public class TaskRecycler extends RecyclerView.Adapter<TaskRecycler.TaskViewHolder> {

    public OnTaskListClickListener onTaskListClickListener = null;

    private List<TaskEntity> taskList;

    public TaskRecycler(List<TaskEntity> taskList)
    {
        this.taskList = taskList;

    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskViewHolder(ItemTasklistBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent,false));
    }

    public void setData(List<TaskEntity> taskList) {
        this.taskList = taskList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull TaskRecycler.TaskViewHolder holder, int position) {
        TaskEntity task = taskList.get(position);
        holder.binding.taskText.setText(task.taskText);
        holder.binding.creationDate.setText(task.date.toString());
        holder.binding.expirationDate.setText(task.expirationDate.toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTaskListClickListener.OnTaskItemListClickListener(holder.getAdapterPosition());
            }
        });
    }


    @Override
    public int getItemCount() {
        return taskList.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder{
        ItemTasklistBinding binding;

        public TaskViewHolder(@NonNull ItemTasklistBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
        public interface OnTaskListClickListener {
        void OnTaskItemListClickListener(int position);
    }
}
