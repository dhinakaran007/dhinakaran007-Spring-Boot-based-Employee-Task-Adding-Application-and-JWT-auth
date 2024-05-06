package com.employee.timetrack.service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.timetrack.bean.Project;
import com.employee.timetrack.bean.Task;
import com.employee.timetrack.exception.EditTaskValidationException;
import com.employee.timetrack.exception.TaskValidationException;
import com.employee.timetrack.repo.ProjectRepository;
import com.employee.timetrack.repo.TaskRepository;
import org.springframework.transaction.annotation.Transactional;

@Service

public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private ProjectRepository projectRepository;
	private Task task;

	@Override
	@Transactional
	public Task saveTask(Task task) {
		// TODO Auto-generated method stub
		try {
			long taskDuration = calculateTaskDuration(task.getStarTime(), task.getEndTime());
			long taskDurationOne = 0;
			System.out.println("1st validation");
			if(task.getStarTime().toLocalTime().isAfter(task.getEndTime().toLocalTime())) {
				System.out.println("1st validation error");
				throw new TaskValidationException("Start Time should be before task end time."); 
			}

			System.out.println("2nd validation");
			if (taskDuration > 8 * 60 * 60 * 1000) { 
				System.out.println("2nd validation error");
				throw new TaskValidationException("Task hours should not exceed above 8 hours."); 
			}

			System.out.println("3rd validation");
			if(task.getStarTime().toLocalTime().isAfter(task.getEndTime().toLocalTime())) {
				System.out.println("3rd validation error");
				throw new TaskValidationException("Start Time should be before task end time."); 
			}

			//	        if (!isValidDateForTask(task.getTaskDate())) {
			//	        	 throw new TaskValidationException("Task date should not be past or future date.");  // Invalid task date
			//	        }

			System.out.println("4th validation");

			if (!isWithinWorkingHours(task.getStarTime(), task.getEndTime())) {
				System.out.println("4th validation error");
				throw new TaskValidationException("Task duration should be 9A.M to 5P.M & Task time should not be less than system time."); // Task timings are outside working hours
			}
			System.out.println("5th validation");
			if(!isWithingFreeTimeSlots(task)) {
				System.out.println("5th validation error");
				throw new EditTaskValidationException("Task should not overlap existing task's work hours");
			} 

			//	        if (isTaskOverlap(task.getAssociateId(), task.getStarTime(), task.getEndTime())) {
			//	        	throw new TaskValidationException("Task durations should not overlap with other task durations."); // Task timings overlap with existing tasks for the same associate
			//	        }
			//	        
			System.out.println("6th validation");
			if (isDailyWorkHoursExceeded(task.getAssociateId(), task.getTaskDate(), taskDurationOne)) {
				System.out.println("6th validation eror");
				throw new TaskValidationException("Task duration should not exceed above 8 hours.");
			}

		} catch (TaskValidationException ex) {
			// TODO: handle exception
			ex.printStackTrace();
			throw ex;
		} catch (RuntimeException ex) {
			// TODO: handle exception
			ex.printStackTrace();
			throw ex;
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("service"+ task);
		//return associateTaskDao.addTask(task);
		return taskRepository.save(task);

	}

	private long calculateTaskDuration(Time startTime, Time endTime) {
		long startMillis = startTime.getTime();
		long endMillis = endTime.getTime();

		return endMillis - startMillis;
	}

	// Checking for valid Date
	private boolean isValidDateForTask(Date taskDate) {
		Date currentDate = getCurrentDate();
		return !taskDate.before(currentDate) && !taskDate.after(currentDate);
	}

	// Getting Date
	private Date getCurrentDate() {
		LocalDate localDate = LocalDate.now();
		return Date.valueOf(localDate);
	}

	//GetDate with Time 
	private Time getTimeWithTime(int hour, int minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return new Time(calendar.getTimeInMillis());
	}


	private boolean isWithinWorkingHours(Time startTime, Time endTime) {
		// Assuming your working hours are from 9 AM to 5 PM
		Time workingStartTime = getTimeWithTime(9, 0);
		Time workingEndTime = getTimeWithTime(17, 0);

		int startHour = startTime.getHours();
		int startMinute = startTime.getMinutes();
		int endHour = endTime.getHours();
		int endMinute = endTime.getMinutes();

		int workingStartHour = workingStartTime.getHours();
		int workingStartMinute = workingStartTime.getMinutes();
		int workingEndHour = workingEndTime.getHours();
		int workingEndMinute = workingEndTime.getMinutes();

		System.out.println("Task Start Time: " + startTime);
		System.out.println("Task End Time: " + endTime);
		System.out.println("Working Start Time: " + workingStartTime);
		System.out.println("Working End Time: " + workingEndTime);


		//Getting Local Time
		LocalTime currentTime = LocalTime.now();
		System.out.println("Current time: " + currentTime);

		boolean withinWorkingHours =
				(startHour > workingStartHour || (startHour == workingStartHour && startMinute >= workingStartMinute))
				&& (endHour < workingEndHour || (endHour == workingEndHour && endMinute <= workingEndMinute));

		boolean isAfterCurrentTime = startTime.toLocalTime().isBefore(currentTime);

		boolean withinWorkingHoursAndFutureTime = withinWorkingHours && isAfterCurrentTime;

		System.out.println("Is within working hours and future time? " + withinWorkingHoursAndFutureTime);

		return withinWorkingHoursAndFutureTime;
	}

	private boolean isTaskOverlap(long associateId, Time startTime, Time endTime) {
		List<Task> existingTasks = taskRepository.getTasksByAssociateId(associateId);

		for (Task existingTask : existingTasks) {
			if (isToday(existingTask.getTaskDate())) {
				if (isTimeOverlap(startTime, endTime, existingTask.getStarTime(), existingTask.getEndTime())) {
					return true; // Task time overlaps with an existing task for the same associate
				}
			}
		}

		return false;
	}

	private boolean isTimeOverlap(Time startTime1, Time endTime1, Time startTime2, Time endTime2) {
		long startMillis1 = startTime1.getTime();
		long endMillis1 = endTime1.getTime();
		long startMillis2 = startTime2.getTime();
		long endMillis2 = endTime2.getTime();

		return !(endMillis1 <= startMillis2 || startMillis1 >= endMillis2);
	}

	private boolean isToday(Date date) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date);

		// Set time to the beginning of the day
		cal2.set(Calendar.HOUR_OF_DAY, 0);
		cal2.set(Calendar.MINUTE, 0);
		cal2.set(Calendar.SECOND, 0);
		cal2.set(Calendar.MILLISECOND, 0);

		return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
				cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
				cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
	}


	private boolean isDailyWorkHoursExceeded(long associateId, Date taskDate, long taskDurationOne) {
		Time loggedhours = taskRepository.getTasksByAssociateIdAndTaskDate(associateId, taskDate);
		if(loggedhours!=null) {
			long hours = Time.valueOf("08:00:00").getTime();
			System.out.println("Time compare "+Long.compare(loggedhours.getTime(), hours));
			if( Long.compare(loggedhours.getTime(), hours)>0) {
				return true;
			}
			return false;
		}
		return false;

		/*
		 * long totalWorkHoursForDate = calculateTotalWorkHours(tasksForDate);
		 * 
		 * // Assuming 8 hours is the maximum work hours for a day long
		 * remainingWorkHours = 8 * 60 * 60 * 1000 - totalWorkHoursForDate;
		 * 
		 * return taskDurationOne > remainingWorkHours;
		 */
	}

	private long calculateTotalWorkHours(List<Task> tasks) {
		long totalWorkHours = 0;

		for (Task task : tasks) {
			totalWorkHours += calculateTaskDuration(task.getStarTime(), task.getEndTime());
		}

		return totalWorkHours;
	}
	
	@Override
	public Task updateTask(Task task) {
		// TODO Auto-generated method stub
		return taskRepository.save(task);
	}

	@Override
	public void deleteTask(long taskId) {
		// TODO Auto-generated method stub
		taskRepository.deleteById(taskId);
	}

	@Override
	public List<Task> getTaskList() {
		// TODO Auto-generated method stub
		return taskRepository.findAll();
	}

	@Override
	public List<Task> getTasksByAssociateId(long associateId) {
		// TODO Auto-generated method stub
		return taskRepository.getTasksByAssociateId(associateId);
	}

	@Override
	public Task getTaskById(long taskId) {
		// TODO Auto-generated method stub
		return taskRepository.findByTaskId(taskId);
	}



	@Override
	public Date getTaskDate(long taskId) {
	    Task task = taskRepository.findById(taskId)
	                             .orElseThrow(() -> new RuntimeException("Task not found with ID: " + taskId));
	    return task.getTaskDate();
	}

	
	@Override
	public Task editTask(Task task) {
		this.task = task;
		Date taskDateFromDB = getTaskDate(task.getTaskId());
		long assIdfromDB = taskRepository.getTaskByTaskId(task.getTaskId());
		
		Task existingTask = taskRepository.findById(task.getTaskId())
                .orElseThrow(() -> new RuntimeException("Task not found with ID: " + task.getTaskId()));
		long projectId = existingTask.getProjectId();
		task.setProjectId(projectId);
		this.task.setProjectId(projectId);
		this.task.setTaskDate(taskDateFromDB);
		this.task.setAssociateId(assIdfromDB);
		//TODO: taskDate, AssociateId
		int validationResult = taskValidation();

//		if (validationResult != 0) {
//			// Handle validation error, log, or return an appropriate value
//			return validationResult;
//		}

		return taskRepository.save(task);
		
	}



	private int taskValidation() {

		try {
			long taskDuration = calculateTaskDuration(task.getStarTime(), task.getEndTime());
			long taskDurationOne = 0;
			if (taskDuration > 8 * 60 * 60 * 1000) { 
				throw new EditTaskValidationException("Task hours should not exceed above 8 hours."); 
			}

			if (!isValidDateForTask(task.getTaskDate())) {
				throw new EditTaskValidationException("Task date should not be past or future date."); // Invalid task date
			}

			if (!isWithinWorkingHours(task.getStarTime(), task.getEndTime())) {
				throw new EditTaskValidationException("Task duration should be 9A.M to 5P.M & Task time should not be less than system time."); // Task timings are outside working hours
			}

			/*
			 * if (isTaskOverlap(task.getAssociateId(), task.getStarTime(),
			 * task.getEndTime())) { throw new
			 * EditTaskValidationException("Task durations should not overlap with other task durations."
			 * ); // Task timings overlap with existing tasks for the same associate }
			 */

			if (isDailyWorkHoursExceeded(task.getAssociateId(), task.getTaskDate(), taskDurationOne)) {
				throw new EditTaskValidationException("Task duration should not exceed above 8 hours.");
			}
			if(!isWithingFreeTimeSlots(task)) {
				throw new EditTaskValidationException("Task should not overlap existing task's work hours");
			}
			return 0;

		} catch (TaskValidationException ex) {
			// TODO: handle exception
			throw ex;
		} catch (RuntimeException ex) {
			// TODO: handle exception
			throw ex;
		}

		//return 0;

	}

	public boolean isWithingFreeTimeSlots(Task task) {
		List<Task> freeSlotsTaskList = taskRepository.findAll();
		if(freeSlotsTaskList.size()>0) {
			for (Task t1 : freeSlotsTaskList) {
				System.out.println(t1.getStarTime()+" --> "+t1.getEndTime());
				System.out.println(task.getStarTime() +" --> "+task.getEndTime());

				if((task.getStarTime().toLocalTime().isBefore(t1.getEndTime().toLocalTime())&&task.getEndTime().toLocalTime().isBefore(t1.getStarTime().toLocalTime()))	) {
					System.out.println("before");
				}
				else if(task.getStarTime().toLocalTime().isAfter(t1.getEndTime().toLocalTime())&&task.getEndTime().toLocalTime().isAfter(t1.getStarTime().toLocalTime())){
					System.out.println("after");
				}
				else {
					System.out.println("nothing");
				}
			}

			return freeSlotsTaskList.stream().allMatch(t1->
			(task.getStarTime().toLocalTime().isBefore(t1.getEndTime().toLocalTime())&&task.getEndTime().toLocalTime().isBefore(t1.getStarTime().toLocalTime()))	
			|| (task.getStarTime().toLocalTime().isAfter(t1.getEndTime().toLocalTime())&&task.getEndTime().toLocalTime().isAfter(t1.getStarTime().toLocalTime()))
					);
		}
		return true;
	}

	public List<Project> getListofAllProjects(){
		return projectRepository.findAll();
	}


}
