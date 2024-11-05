package service;

import java.util.List;
import entity.JobEntity;
import entity.StatusEntity;
import entity.TaskEntity;
import entity.UserEntity;
import repository.JobRepository;
import repository.StatusRepository;
import repository.TaskRepository;
import repository.UserRepository;

public class TaskService {
	private TaskRepository taskRepository = new TaskRepository();
	private JobRepository jobRepository = new JobRepository();
	private UserRepository userRepository = new UserRepository();
	private StatusRepository statusRepository = new StatusRepository();

	public List<TaskEntity> task() {
		List<TaskEntity> listTasks = taskRepository.findAll();
		for (TaskEntity taskEntity : listTasks) {
			String fullName = taskEntity.getFirstName() + " " + taskEntity.getLastName();
			taskEntity.setFullName(fullName);

		}
		return listTasks;
	}
	
	public boolean addTask(String name, String projectName, String startDate, String endDate, int userId, int jobId, int statusId) {
		return taskRepository.insertTask(name, projectName, startDate, endDate, userId, jobId, statusId) > 0;
	}

	public List<JobEntity> listJob() {
		List<JobEntity> listJob = jobRepository.findAll();
		return listJob;
	}

	public List<UserEntity> listUser() {
		List<UserEntity> listUser = userRepository.findAll();
		for(UserEntity userEntity : listUser) {
			String fullName = userEntity.getFirstName() + " " + userEntity.getLastName();
			userEntity.setFullName(fullName);
		}
		return listUser;
	}
	public List<StatusEntity> listStatus() {
		List<StatusEntity> listStatus = statusRepository.findAll();
		return listStatus;
	}

}
