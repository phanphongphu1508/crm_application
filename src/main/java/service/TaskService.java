package service;

import java.util.List;

import entity.ProjectEntity;
import entity.StatusEntity;
import entity.TaskEntity;
import entity.UserEntity;
import repository.ProjectRepository;
import repository.StatusRepository;
import repository.TaskRepository;
import repository.UserRepository;

public class TaskService {
	private TaskRepository taskRepository = new TaskRepository();
	private UserRepository UserRepository = new UserRepository();
	private ProjectRepository projectRepository = new ProjectRepository();
	private StatusRepository statusRepository = new StatusRepository();

	public List<TaskEntity> task() {
		return taskRepository.findAll();
	}

	public boolean deleteTask(int id) {
		return taskRepository.deleteById(id) > 0;
	}

	public boolean taskAdd(String taskName, String startDate, String endDate, int userID, int projectID, int statusID) {
		return taskRepository.insertTask(taskName, startDate, endDate, userID, projectID, statusID) > 0;
	}

	public List<UserEntity> user() {
		return UserRepository.findAllUserWithRole();
	}

	public List<ProjectEntity> project() {
		return projectRepository.findAll();
	}

	public List<StatusEntity> status() {
		return statusRepository.findAll();
	}

	public TaskEntity showTaskEdit(int id) {
		return taskRepository.findById(id);
	}

	public boolean taskEdit(String taskName, String startDate, String endDate, int userId, int projectId, int statusId,
			int taskId) {
		return taskRepository.updateById(taskName, startDate, endDate, userId, projectId, statusId, taskId) > 0;
	}

}
