package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import config.MysqlConfig;
import entity.UserEntity;

public class UserRepository {
	public UserEntity findByEmailAndPassword(String email, String password) {
		UserEntity userEntity = null;

		String query = "SELECT u.id, u.firstname, u.lastname, u.email, r.name AS role_name FROM users u JOIN roles r ON u.role_id = r.id WHERE u.email = ? AND u.password = ?";

		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, email);
			statement.setString(2, password);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				userEntity = new UserEntity();
				userEntity.setId(result.getInt("id"));
				userEntity.setEmail(result.getString("email"));
				userEntity.setRoleName(result.getString("role_name"));
			}
		} catch (Exception e) {
			System.out.println("UsersRepository" + e.getLocalizedMessage());
		}
		return userEntity;
	}

	public List<UserEntity> findAll() {
		List<UserEntity> listUserEntity = new ArrayList<UserEntity>();
		String query = "SELECT u.id, u.firstname, u.lastname, u.email, u.avatar, r.name FROM users u INNER JOIN roles r ON u.role_id = r.id";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				UserEntity userEntity = new UserEntity();
				userEntity.setId(result.getInt("id"));
				userEntity.setFirstName(result.getString("firstname"));
				userEntity.setLastName(result.getString("lastname"));
				userEntity.setEmail(result.getString("email"));
				userEntity.setRoleName(result.getString("name"));

				listUserEntity.add(userEntity);
			}
		} catch (Exception e) {
			System.out.println("findAll " + e.getLocalizedMessage());
		}
		return listUserEntity;
	}

	public List<UserEntity> findUserAndTask() {
		List<UserEntity> listUserAndTaskAndStatus = new ArrayList<UserEntity>();
		String query = "SELECT u.firstname, u.lastname, u.avatar, u.email, t.name AS task_name, t.start_date, t.end_date FROM users u JOIN tasks t ON u.id = t.user_id JOIN status s ON s.id = t.status_id";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				UserEntity userEntity = new UserEntity();
				userEntity.setFirstName(result.getString("firstname"));
				userEntity.setLastName(result.getString("lastname"));
				userEntity.setEmail(result.getString("email"));
				userEntity.setAvatar(result.getString("avatar"));
				userEntity.setStartDate(result.getString("start_date"));
				userEntity.setEndDate(result.getString("end_date"));

				listUserAndTaskAndStatus.add(userEntity);
			}
		} catch (Exception e) {
			System.out.println("findUserAndTask " + e.getLocalizedMessage());
		}

		return listUserAndTaskAndStatus;
	}

	public int insertUser(String firstname, String lastname, String phone, String email, String password, int role) {
		int rowInsert = 0;
		String query = "INSERT INTO users(firstname, lastname, phone, email, password, role_id) VALUES (?,?,?,?,?,?)";

		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, firstname);
			statement.setString(2, lastname);
			statement.setString(3, phone);
			statement.setString(4, email);
			statement.setString(5, password);
			statement.setInt(6, role);

			rowInsert = statement.executeUpdate();

		} catch (Exception e) {
			System.out.println("insertUser " + e.getLocalizedMessage());
		}

		return rowInsert;
	}

	public int deleteById(int id) {
		int rowDeleted = 0;
		String query = "DELETE FROM users u WHERE u.id = ?;";
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

	/*
	 * public int updateUser(int id, String firstname, String lastname, String
	 * email, String password, int role) { int rowUpdate = 0; String query =
	 * "UPDATE users SET firstname = ?, lastname = ?, email = ?, password = ?, role_id = ? WHERE id = ?"
	 * ; Connection connection = MysqlConfig.getConnection(); try {
	 * PreparedStatement statement = connection.prepareStatement(query);
	 * statement.setInt(1, id); statement.setString(2, firstname);
	 * statement.setString(3, lastname); statement.setString(4, email);
	 * statement.setString(5, password); statement.setInt(6, role);
	 * 
	 * rowUpdate = statement.executeUpdate(); } catch (Exception e) {
	 * System.out.println("findAll " + e.getLocalizedMessage()); } return rowUpdate;
	 * }
	 */
}