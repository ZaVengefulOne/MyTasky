package com.example.mytasky.ui.stateholders.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytasky.data.database.entity.TaskEntity;
import com.example.mytasky.databinding.ItemTasklistBinding;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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

    @Override
    public void onBindViewHolder(@NonNull TaskRecycler.TaskViewHolder holder, int position) {
        if (taskList.size() != 0) {
            TaskEntity task = taskList.get(position);
            CheckBox checkBox = holder.binding.checkBox;
            checkBox.setText(task.taskText);
            if (task.date != null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy", Locale.getDefault());
                String dateString = dateFormat.format(task.date.getDate());
                holder.binding.creationDate.setText(dateString);
                holder.binding.creationDate.setPadding(0,35,0,0);
            } else {
                holder.binding.creationDate.setText("");
                holder.binding.creationDate.setPadding(0,35,0,0);
            }
            // добавляем проверку на null перед вызовом метода toString()
            if (task.expirationDate != null) {
//            String expirationDateString = task.expirationDate != null ? task.expirationDate.toString() : "";
//            holder.binding.expirationDate.setText(expirationDateString);
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
//                    holder.binding.expirationDate.setText(task.expirationDate.format(formatter));
//                }
                holder.binding.expirationDate.setText("09:37");
                holder.binding.expirationDate.setPadding(0,35,0,0);
            }
            else {
                Calendar calendar = Calendar.getInstance();
                calendar.set(task.date.getYear(), task.date.getMonth(), task.date.getDay());
                calendar.add(Calendar.HOUR_OF_DAY, 1);
                holder.binding.expirationDate.setText(CalendarDay.from(calendar.getTime()).getDay());
            }
        }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onTaskListClickListener.OnTaskItemListClickListener(holder.getAdapterPosition());
                }
            });
        }


    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }


    static class TaskViewHolder extends RecyclerView.ViewHolder{
        public CheckBox checkBox;
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
