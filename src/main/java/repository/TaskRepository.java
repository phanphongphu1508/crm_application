package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import config.MysqlConfig;
import entity.TaskEntity;

public class TaskRepository {
	public List<TaskEntity> findAll() {
		List<TaskEntity> listTasks = new ArrayList<TaskEntity>();
		String query = "SELECT t.id, t.name, t.project_name, u.firstname, u. lastname, t.start_date, t.end_date, s.name AS status_name FROM tasks t JOIN users u ON t.user_id = u.id JOIN status s ON t.status_id = s.id";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				TaskEntity taskEntity = new TaskEntity();
				taskEntity.setId(result.getInt("id"));
				taskEntity.setName(result.getString("name"));
				taskEntity.setProjectName(result.getString("project_name"));
				taskEntity.setStartDate(result.getString("start_date"));
				taskEntity.setEndDate(result.getString("end_date"));
				taskEntity.setFirstName(result.getString("firstname"));
				taskEntity.setLastName(result.getString("lastname"));
				taskEntity.setStatusName(result.getString("status_name"));

				listTasks.add(taskEntity);
			}

		} catch (Exception e) {
			System.out.println("findAll " + e.getLocalizedMessage());

		}
		return listTasks;
	}

	public int insert(String name, String description) {
		int rowInsert = 0;
		String query = "INSERT INTO roles(name, description) VALUES(?, ?)";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, name);
			statement.setString(2, description);

			rowInsert = statement.executeUpdate();

		} catch (Exception e) {
			System.out.println("insert:" + e.getLocalizedMessage());
		}
		return rowInsert;
	}
	public int insertTask(String name, String projectName, String startDate, String endDate, int userId, int jobId,  int statusId) {
		int rowInsert = 0;
		String query = "INSERT INTO tasks(name, project_name, start_date, end_date, user_id, job_id, status_id) VALUES (?,?,?,?,?,?,?)";

		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, name);
			statement.setString(2, projectName);
			statement.setString(3, startDate);
			statement.setString(4, endDate);
			statement.setInt(5, userId);
			statement.setInt(6, jobId);
			statement.setInt(7, statusId);

			rowInsert = statement.executeUpdate();

		} catch (Exception e) {
			System.out.println("insertUser " + e.getLocalizedMessage());
		}

		return rowInsert;
	}


}
