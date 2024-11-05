package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import config.MysqlConfig;
import entity.JobEntity;

public class JobRepository {
	public List<JobEntity> findAll() {
		List<JobEntity> listJobs = new ArrayList<JobEntity>();
		String query = "SELECT * FROM jobs";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				JobEntity jobEntity = new JobEntity();
				jobEntity.setId(result.getInt("id"));
				jobEntity.setName(result.getString("name"));
				jobEntity.setStartDate(result.getString("start_date"));
				jobEntity.setEndDate(result.getString("End_date"));

				listJobs.add(jobEntity);
			}
		} catch (Exception e) {
			System.out.println("findAll " + e.getLocalizedMessage());
		}

		return listJobs;
	}

	public int insertJob(String name, String startDate, String endDate) {
		int rowInsert = 0;
		String query = "INSERT INTO jobs(name, start_date, end_date) VALUES (?,?,?)";

		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, name);
			statement.setString(2, startDate);
			statement.setString(3, endDate);
			rowInsert = statement.executeUpdate();

		} catch (Exception e) {
			System.out.println("insertJob " + e.getLocalizedMessage());
		}

		return rowInsert;
	}

	public int deleteById(int id) {
		int rowDeleted = 0;
		String query = "DELETE FROM jobs u WHERE u.id = ?;";
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
