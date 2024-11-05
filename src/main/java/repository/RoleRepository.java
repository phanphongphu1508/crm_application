package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import config.MysqlConfig;
import entity.RoleEntity;

public class RoleRepository {
	public List<RoleEntity> findAll() {
		List<RoleEntity> listRoles = new ArrayList<RoleEntity>();
		String query = "SELECT * FROM roles";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				RoleEntity roleEntity = new RoleEntity();
				roleEntity.setId(result.getInt("id"));
				roleEntity.setName(result.getString("name"));
				roleEntity.setDescription(result.getString("description"));

				listRoles.add(roleEntity);
			}
		} catch (Exception e) {
			System.out.println("findAll " + e.getLocalizedMessage());
		}
		return listRoles;
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

	public int deleteById(int id) {
		int delete = 0;
		String query = "DELETE FROM roles r WHERE r.id=?";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			delete = statement.executeUpdate();
		} catch (Exception e) {
			System.out.println("deleteById " + e.getLocalizedMessage());
		}
		return delete;
	}
}
