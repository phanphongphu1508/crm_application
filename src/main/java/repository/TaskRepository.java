package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import config.MysqlConfig;
import entity.ProjectEntity;
import entity.StatusEntity;
import entity.TaskEntity;
import entity.UserEntity;

public class TaskRepository {
	public List<TaskEntity> findAll() {
		List<TaskEntity> tasks = new ArrayList<TaskEntity>();
		String query = "SELECT * FROM tasks t JOIN users u On t.user_id = u.id JOIN projects p ON t.project_id = p.id JOIN status s ON t.status_id = s.id";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				TaskEntity taskEntity = new TaskEntity();
				taskEntity.setId(result.getInt("id"));
				taskEntity.setTaskName(result.getString("task_name"));
				taskEntity.setStartDate(result.getString("start_date"));
				taskEntity.setEndDate(result.getString("end_date"));

				UserEntity userEntity = new UserEntity();
				userEntity.setFirstName(result.getString("first_name"));
				userEntity.setLastName(result.getString("last_name"));
				taskEntity.setUser(userEntity);

				ProjectEntity projectEntity = new ProjectEntity();
				projectEntity.setProjectName(result.getString("project_name"));
				taskEntity.setProject(projectEntity);

				StatusEntity statusEntity = new StatusEntity();
				statusEntity.setStatusName(result.getString("status_name"));
				taskEntity.setStatus(statusEntity);

				tasks.add(taskEntity);
			}
		} catch (Exception e) {
			System.out.println("TaskRepository - findAll: " + e.getLocalizedMessage());
		}
		return tasks;
	}

	public int insertTask(String taskName, String startDate, String endDate, int userID, int projectID, int statusID) {
		int rowInsert = 0;
		String query = "INSERT INTO tasks(task_name, start_date, end_date, user_id, project_id, status_id) VALUES (?,?,?,?,?,?)";

		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, taskName);
			statement.setString(2, startDate);
			statement.setString(3, endDate);
			statement.setInt(4, userID);
			statement.setInt(5, projectID);
			statement.setInt(6, statusID);
			rowInsert = statement.executeUpdate();
		} catch (Exception e) {
			System.out.println("TaskRepository - insertTask " + e.getLocalizedMessage());
		}
		return rowInsert;
	}

	public int deleteById(int id) {
		int rowDeleted = 0;
		String query = "DELETE FROM tasks t WHERE t.id = ?";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate();
		} catch (Exception e) {
			System.out.println("TaskRepository - deleteById: " + e.getLocalizedMessage());
		}
		return rowDeleted;
	}

	public List<TaskEntity> findTaskAndProjectAndStatusByUserId(int id) {
		List<TaskEntity> tasks = new ArrayList<TaskEntity>();
		String query = "SELECT * FROM tasks t JOIN projects p ON t.project_id = p.id JOIN status s ON t.status_id = s.id WHERE t.user_id = ?";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				TaskEntity taskEntity = new TaskEntity();
				taskEntity.setId(result.getInt("id"));
				taskEntity.setTaskName(result.getString("task_name"));
				taskEntity.setStartDate(result.getString("start_date"));
				taskEntity.setEndDate(result.getString("end_date"));

				ProjectEntity projectEntity = new ProjectEntity();
				projectEntity.setProjectName(result.getString("project_name"));
				taskEntity.setProject(projectEntity);

				StatusEntity statusEntity = new StatusEntity();
				statusEntity.setStatusName(result.getString("status_name"));
				taskEntity.setStatus(statusEntity);

				tasks.add(taskEntity);
			}
		} catch (Exception e) {
			System.out.println("TaskRepository - findTaskAndProjectAndStatusByUserId: " + e.getLocalizedMessage());
		}
		return tasks;
	}

	public List<TaskEntity> taskByUserId(int id) {
		List<TaskEntity> tasks = new ArrayList<TaskEntity>();
		String query = "SELECT t.task_name, t.start_date, t.end_date, t.status_id FROM tasks t WHERE t.user_id = ?";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				TaskEntity taskEntity = new TaskEntity();
				taskEntity.setTaskName(result.getString("task_name"));
				taskEntity.setStartDate(result.getString("start_date"));
				taskEntity.setEndDate(result.getString("end_date"));
				taskEntity.setStatusID(result.getInt("status_id"));

				tasks.add(taskEntity);
			}
		} catch (Exception e) {
			System.out.println("TaskRepository - findTaskAndProjectAndStatusByUserId: " + e.getLocalizedMessage());
		}
		return tasks;
	}

	public TaskEntity findTaskAndProjectById(int id) {
		TaskEntity taskEntity = null;
		String query = "SELECT * FROM tasks t JOIN projects p ON t.project_id = p.id WHERE t.id = ?";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				taskEntity = new TaskEntity();
				taskEntity.setId(result.getInt("id"));
				taskEntity.setTaskName(result.getString("task_name"));
				taskEntity.setStartDate(result.getString("start_date"));
				taskEntity.setEndDate(result.getString("end_date"));
				taskEntity.setStatusID(result.getInt("status_id"));
				ProjectEntity projectEntity = new ProjectEntity();
				projectEntity.setProjectName(result.getString("project_name"));
				taskEntity.setProject(projectEntity);

			}
		} catch (Exception e) {
			System.out.println("TaskRepository - findTaskAndProjectAndStatusByUserId: " + e.getLocalizedMessage());
		}
		return taskEntity;
	}

	public int updateTaskById(int taskId, int statusId) {
		int taskUpdate = 0;
		String query = "UPDATE tasks SET status_id = ? WHERE id = ?";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, statusId);
			statement.setInt(2, taskId);
			taskUpdate = statement.executeUpdate();
		} catch (Exception e) {
			System.out.println("TaskRepository - alterTask " + e.getLocalizedMessage());
		}
		return taskUpdate;
	}

	// Show task update
	public TaskEntity findById(int id) {
		TaskEntity taskEntity = null;
		String query = "SELECT * FROM tasks WHERE id = ?";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				taskEntity = new TaskEntity();
				taskEntity.setId(result.getInt("id"));
				taskEntity.setTaskName(result.getString("task_name"));
				taskEntity.setStartDate(result.getString("start_date"));
				taskEntity.setEndDate(result.getString("end_date"));
			}
		} catch (Exception e) {
			System.out.println("TaskRepository - findTaskAndProjectAndStatusByUserId: " + e.getLocalizedMessage());
		}
		return taskEntity;
	}

	public int updateById(String taskName, String startDate, String endDate, int userId, int projectId, int statusId,
			int taskId) {
		int rowInsert = 0;
		String query = "UPDATE tasks SET task_name = ?, start_date = ?, end_date = ?, user_id  = ?, project_id = ?, status_id = ? WHERE id = ?";

		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, taskName);
			statement.setString(2, startDate);
			statement.setString(3, endDate);
			statement.setInt(4, userId);
			statement.setInt(5, projectId);
			statement.setInt(6, statusId);
			statement.setInt(7, taskId);
			rowInsert = statement.executeUpdate();
		} catch (Exception e) {
			System.out.println("TaskRepository - updateTask " + e.getLocalizedMessage());
		}
		return rowInsert;
	}

	// project detail
	public List<TaskEntity> findUserAndTaskAndProjectAndStatusByProjectId(int id) {
		List<TaskEntity> projects = new ArrayList<TaskEntity>();
		String query = "SELECT t.task_name, t.start_date, t.end_date, u.first_name, u.last_name, us.first_name AS mfirst_name, us.last_name AS mlast_name, s.status_name FROM tasks t JOIN users u ON t.user_id = u.id JOIN projects p ON t.project_id = p.id JOIN status s ON t.status_id = s.id LEFT JOIN users us ON p.manager_id = us.id WHERE t.project_id = ?";
		try {
			Connection connection = MysqlConfig.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				TaskEntity taskEntity = new TaskEntity();
				taskEntity.setTaskName(result.getString("task_name"));
				taskEntity.setStartDate(result.getString("start_date"));
				taskEntity.setEndDate(result.getString("end_date"));

				UserEntity userEntity = new UserEntity();
				userEntity.setFirstName(result.getString("first_name"));
				userEntity.setLastName(result.getString("last_name"));
				taskEntity.setUser(userEntity);

				ProjectEntity projectEntity = new ProjectEntity();
				projectEntity.setFirstName(result.getString("mfirst_name"));
				projectEntity.setLastName(result.getString("mlast_name"));
				taskEntity.setProject(projectEntity);

				StatusEntity statusEntity = new StatusEntity();
				statusEntity.setStatusName(result.getString("status_name"));
				taskEntity.setStatus(statusEntity);

				projects.add(taskEntity);
			}

		} catch (Exception e) {
			System.out.println(
					"TaskRepository - findUserAndTaskAndProjectAndStatusByProjectId " + e.getLocalizedMessage());
		}

		return projects;
	}
}
