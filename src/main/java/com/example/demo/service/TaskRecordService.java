package com.example.demo.service;

import com.example.demo.model.TaskRecord;

public interface TaskRecordService {
    TaskRecord createTask(TaskRecord task);
    TaskRecord getTaskById(Long id);
}