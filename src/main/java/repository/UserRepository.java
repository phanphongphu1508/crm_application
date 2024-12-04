package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import config.MysqlConfig;
import entity.RoleEntity;
import entity.UserEntity;

public class UserRepository {
	// login
	public UserEntity findByEmailAndPassword(String email, String password) {
		UserEntity userEntity = null;
		String query = "SELECT * FROM users u JOIN roles r ON r.id = u.role_id WHERE email = ? AND password = ?";
		try {
			Connection connection = MysqlConfig.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, email);
			statement.setString(2, password);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				userEntity = new UserEntity();
				userEntity.setId(result.getInt("id"));
				userEntity.setEmail(result.getString("email"));
				RoleEntity roleEntity = new RoleEntity();
				roleEntity.setRoleName(result.getString("role_name"));
				userEntity.setRole(roleEntity);
			}
		} catch (Exception e) {
			System.out.println("UserRepository - findByEmailAndPassword: " + e.getMessage());
		}
		return userEntity;
	}

	// show user name for project
	public List<UserEntity> findAll() {
		List<UserEntity> users = new ArrayList<UserEntity>();
		String query = "SELECT u.id, u.first_name, u.last_name FROM users u";
		try {
			Connection connection = MysqlConfig.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				UserEntity userEntity = new UserEntity();
				userEntity.setId(result.getInt("id"));
				userEntity.setFirstName(result.getString("first_name"));
				userEntity.setLastName(result.getString("last_name"));
				users.add(userEntity);
			}
		} catch (Exception e) {
			System.out.println("UserRepository - findAll: " + e.getMessage());
		}
		return users;
	}

	// show user update
	public UserEntity findUserById(int id) {
		UserEntity userEntity = null;
		String query = "SELECT * FROM users u  WHERE id = ?";
		try {
			Connection connection = MysqlConfig.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();
			while(result.next()) {
				userEntity = new UserEntity();
				userEntity.setId(result.getInt("id"));
				userEntity.setFirstName(result.getString("first_name"));
				userEntity.setLastName(result.getString("last_name"));
				userEntity.setEmail(result.getString("email"));
			}
		} catch (Exception e) {
			System.out.println("UserRepository - findUserById: " + e.getMessage());
		}
		return userEntity;
	}

	// Find All
	public List<UserEntity> findAllUserWithRole() {
		List<UserEntity> users = new ArrayList<UserEntity>();
		String query = "SELECT * FROM users u JOIN roles r ON u.role_id = r.id";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				UserEntity userEntity = new UserEntity();
				userEntity.setId(result.getInt("id"));
				userEntity.setFirstName(result.getString("first_name"));
				userEntity.setLastName(result.getString("last_name"));
				userEntity.setEmail(result.getString("email"));
				userEntity.setRoleId(result.getInt("role_id"));

				RoleEntity roleEntity = new RoleEntity();
				roleEntity.setId(result.getInt("id"));
				roleEntity.setRoleName(result.getString("role_name"));
				roleEntity.setDescription(result.getString("description"));

				// Gắn RoleEntity vào UserEntity
				userEntity.setRole(roleEntity);

				// Thêm UserEntity vào danh sách
				users.add(userEntity);
			}
		} catch (Exception e) {
			System.out.println("UserRepository - findAllUserWithRole " + e.getLocalizedMessage());
		}
		return users;
	}

	public int insertUser(String firstName, String lastName, String email, String password, int role) {
		int rowInsert = 0;
		String query = "INSERT INTO users(first_name, last_name, email, password, role_id) VALUES (?,?,?,?,?)";

		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, firstName);
			statement.setString(2, lastName);
			statement.setString(3, email);
			statement.setString(4, password);
			statement.setInt(5, role);

			rowInsert = statement.executeUpdate();

		} catch (Exception e) {
			System.out.println("UserRepository - insertUser " + e.getLocalizedMessage());
		}

		return rowInsert;
	}

	public int deleteById(int id) {
		int rowDeleted = 0;
		String query = "DELETE FROM users u WHERE u.id = ?";
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

	// Lấy id để update roleName và description
	public int updateUserById(String firstName, String lastName, String email, String newPassword, int roleId,
			int userId) {
		int userUpdate = 0;
		String query;
		boolean updatePassword = true;
		if (newPassword.isEmpty()) {
			query = "UPDATE users SET first_name = ?, last_name = ?, email = ?, role_id  = ? WHERE id = ?";
			updatePassword = false;
		} else {
			query = "UPDATE users SET first_name = ?, last_name = ?, email = ?, password = ?, role_id  = ? WHERE id = ?";
		}

		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			if (updatePassword) {
				statement.setString(1, firstName);
				statement.setString(2, lastName);
				statement.setString(3, email);
				statement.setString(4, newPassword);
				statement.setInt(5, roleId);
				statement.setInt(6, userId);
			} else {
				statement.setString(1, firstName);
				statement.setString(2, lastName);
				statement.setString(3, email);
				statement.setInt(4, roleId);
				statement.setInt(5, userId);
			}
			userUpdate = statement.executeUpdate();
		} catch (Exception e) {
			System.out.println("UserRepository - updateUserById " + e.getLocalizedMessage());
		}
		return userUpdate;
	}
}