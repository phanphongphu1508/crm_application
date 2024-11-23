package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import config.MysqlConfig;
import entity.ProjectEntity;

public class ProjectRepository {
	public List<ProjectEntity> findAll() {
		List<ProjectEntity> projects = new ArrayList<ProjectEntity>();
		String query = "SELECT * FROM projects";
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

	public int insertProject(String projectName, String startDate, String endDate) {
		int rowInsert = 0;
		String query = "INSERT INTO projects(project_name, start_date, end_date) VALUES (?,?,?)";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, projectName);
			statement.setString(2, startDate);
			statement.setString(3, endDate);
			rowInsert = statement.executeUpdate();
		} catch (Exception e) {
			System.out.println("ProjectRepository - insertProject: " + e.getLocalizedMessage());
		}
		return rowInsert;
	}

	
}
