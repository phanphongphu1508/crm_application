package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import config.MysqlConfig;
import entity.ProjectEntity;
import entity.UserEntity;

public class ProjectRepository {
	public List<ProjectEntity> findAll() {
		List<ProjectEntity> projects = new ArrayList<ProjectEntity>();
		String query = "SELECT * FROM projects p JOIN users u ON p.manager_id = u.id";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				ProjectEntity projectEntity = new ProjectEntity();
				projectEntity.setId(result.getInt("id"));
				projectEntity.setProjectName(result.getString("project_name"));
				projectEntity.setStartDate(result.getString("start_date"));
				projectEntity.setEndDate(result.getString("End_date"));

				UserEntity userEntity = new UserEntity();
				userEntity.setFirstName(result.getString("first_name"));
				userEntity.setLastName(result.getString("last_name"));
				projectEntity.setUser(userEntity);
				projects.add(projectEntity);
			}
		} catch (Exception e) {
			System.out.println("ProjectRepository - findAll: " + e.getLocalizedMessage());
		}
		return projects;
	}

	public int deleteById(int id) {
		int rowDeleted = 0;
		String query = "DELETE FROM projects p WHERE p.id = ?";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate();
		} catch (Exception e) {
			System.out.println("ProjectRepository - deleteById: " + e.getLocalizedMessage());
		}
		return rowDeleted;
	}

	public int insertProject(String projectName, String startDate, String endDate, int managerId) {
		int rowInsert = 0;
		String query = "INSERT INTO projects(project_name, start_date, end_date, manager_id) VALUES (?,?,?,?)";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, projectName);
			statement.setString(2, startDate);
			statement.setString(3, endDate);
			statement.setInt(4, managerId);
			rowInsert = statement.executeUpdate();
		} catch (Exception e) {
			System.out.println("ProjectRepository - insertProject: " + e.getLocalizedMessage());
		}
		return rowInsert;
	}

	// Show project update
	public ProjectEntity findById(int id) {
		ProjectEntity projectEntity = null;
		String query = "SELECT * FROM projects WHERE id = ?";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				projectEntity = new ProjectEntity();
				projectEntity.setId(result.getInt("id"));
				projectEntity.setProjectName(result.getString("project_name"));
				projectEntity.setStartDate(result.getString("start_date"));
				projectEntity.setEndDate(result.getString("end_date"));
			}
		} catch (Exception e) {
			System.out.println("ProjectRepository - findById: " + e.getLocalizedMessage());
		}
		return projectEntity;
	}

	// Update project
	public int updateProjectById(int projectId, String projectName, String startDate, String endDate, int managerId) {
		int projectUpdate = 0;
		String query = "UPDATE projects SET project_name = ?, start_date = ?, end_date = ?, manager_id = ? WHERE id = ?";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, projectName);
			statement.setString(2, startDate);
			statement.setString(3, endDate);
			statement.setInt(4, managerId);
			statement.setInt(5, projectId);

			projectUpdate = statement.executeUpdate();
		} catch (Exception e) {
			System.out.println("ProjectRepository - updateProjectById " + e.getLocalizedMessage());
		}
		return projectUpdate;
	}

}
