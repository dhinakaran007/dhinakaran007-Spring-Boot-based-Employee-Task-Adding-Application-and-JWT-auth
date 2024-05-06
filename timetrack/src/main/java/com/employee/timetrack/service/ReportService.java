package com.employee.timetrack.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.timetrack.bean.Task;
import com.employee.timetrack.repo.TaskRepository;

@Service
public class ReportService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> generateDailyReport(Date date) {
        // Fetch tasks for the given date
        return taskRepository.findByTaskDate(date);
    }

    public List<Task> generateWeeklyReport(Date startDate, Date endDate) {
        // Fetch tasks between the start and end dates
        return taskRepository.findByTaskDateBetween(startDate, endDate);
    }

    public List<Task> generateMonthlyReport(Date startDate, Date endDate, long associateId) {
        // Fetch tasks between the start and end dates for a specific associate
        return taskRepository.findByTaskDateBetweenAndAssociateId(startDate, endDate, associateId);
    }
}
