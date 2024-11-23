package service;

import java.util.List;

import entity.ProjectEntity;
import repository.ProjectRepository;

public class ProjectService {
	private ProjectRepository ProjectRepository = new ProjectRepository();

	public List<ProjectEntity> project() {
		return ProjectRepository.findAll();
	}

	public boolean deleteProject(int id) {
		return ProjectRepository.deleteById(id) > 0;
	}
	public boolean projectAdd(String projectName, String startDate, String endDate) {
		return ProjectRepository.insertProject(projectName, startDate, endDate) > 0;
	}
}
