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
		String query = "SELECT t.id, t.name AS task_name, j.name AS job_name, u.firstname, u. lastname, t.start_date, t.end_date, s.name AS status_name FROM tasks t JOIN users u ON t.user_id = u.id JOIN jobs j ON t.job_id = j.id JOIN status s ON t.status_id = s.id";

		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				TaskEntity taskEntity = new TaskEntity();
				taskEntity.setId(result.getInt("id"));
				taskEntity.setTaskName(result.getString("task_name"));
				taskEntity.setJobName(result.getString("job_name"));
				taskEntity.setFirstName(result.getString("firstname"));
				taskEntity.setLastName(result.getString("lastname"));
				taskEntity.setStartDate(result.getString("start_date"));
				taskEntity.setEndDate(result.getString("end_date"));
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

	public int insertTask(String name, String startDate, String endDate, int userId, int jobId,
			int statusId) {
		int rowInsert = 0;
		String query = "INSERT INTO tasks(name, start_date, end_date, user_id, job_id, status_id) VALUES (?,?,?,?,?,?)";

		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, name);
			statement.setString(2, startDate);
			statement.setString(3, endDate);
			statement.setInt(4, userId);
			statement.setInt(5, jobId);
			statement.setInt(6, statusId);

			rowInsert = statement.executeUpdate();

		} catch (Exception e) {
			System.out.println("insertUser " + e.getLocalizedMessage());
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
			System.out.println("deleteById " + e.getLocalizedMessage());
		}
		return rowDeleted;
	}

}
