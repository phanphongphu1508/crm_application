package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import config.MysqlConfig;
import entity.StatusEntity;

public class StatusRepository {
	public List<StatusEntity> findAll() {
		List<StatusEntity> status = new ArrayList<StatusEntity>();
		String query = "SELECT * FROM status";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				StatusEntity statusEntity = new StatusEntity();
				statusEntity.setId(result.getInt("id"));
				statusEntity.setStatusName(result.getString("status_name"));

				status.add(statusEntity);
			}

		} catch (Exception e) {
			System.out.println("findAll " + e.getLocalizedMessage());

		}
		return status;
	}
}
