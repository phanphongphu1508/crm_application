package service;

import java.util.List;

import entity.ProjectEntity;
import entity.TaskEntity;
import entity.UserEntity;
import repository.ProjectRepository;
import repository.TaskRepository;
import repository.UserRepository;

public class ProjectService {
	private ProjectRepository projectRepository = new ProjectRepository();
	private UserRepository userRepository = new UserRepository();
	private TaskRepository taskRepository = new TaskRepository();

	public List<ProjectEntity> project() {
		return projectRepository.findAll();
	}

	public boolean deleteProject(int id) {
		return projectRepository.deleteById(id) > 0;
	}

	public boolean projectAdd(String projectName, String startDate, String endDate, int managerId) {
		return projectRepository.insertProject(projectName, startDate, endDate, managerId) > 0;
	}

	// show user name for project
	public List<UserEntity> users() {
		return userRepository.findAll();
	}

	// Show project update
	public ProjectEntity showProjectEdit(int id) {
		return projectRepository.findById(id);
	}

	// Update project
	public boolean projectEdit(int projectId, String projectName, String startDate, String endDate, int managerId) {
		return projectRepository.updateProjectById(projectId, projectName, startDate, endDate, managerId) > 0;
	}
	
	public List<TaskEntity> projectDetail(int id) {
		return taskRepository.findUserAndTaskAndProjectAndStatusByProjectId(id);
		
	}
}
