package service;

import java.util.List;

import entity.StatusEntity;
import entity.TaskEntity;
import entity.UserEntity;
import repository.StatusRepository;
import repository.TaskRepository;
import repository.UserRepository;

public class ProfileService {
	private UserRepository userRepository = new UserRepository();
	private TaskRepository taskRepository = new TaskRepository();
	private StatusRepository statusRepository = new StatusRepository();

	public UserEntity userProfile(int id) {
		return userRepository.findUserById(id);
	}

	public List<TaskEntity> profile(int id) {
		return taskRepository.findTaskAndProjectAndStatusByUserId(id);
	}

	public TaskEntity taskProfileEdit(int id) {
		return taskRepository.findTaskAndProjectById(id);
	}

	public List<StatusEntity> statusProfileEdit() {
		return statusRepository.findAll();
	}

	public boolean profileEdit(int taskId, int statusId) {
		return taskRepository.alterTask(taskId, statusId) > 0;
	}
}
