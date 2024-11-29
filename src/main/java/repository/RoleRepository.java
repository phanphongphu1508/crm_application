package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import config.MysqlConfig;
import entity.RoleEntity;

public class RoleRepository {
	// show role
	public List<RoleEntity> findAll() {
		List<RoleEntity> roles = new ArrayList<RoleEntity>();
		String query = "SELECT * FROM roles";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				RoleEntity roleEntity = new RoleEntity();
				roleEntity.setId(result.getInt("id"));
				roleEntity.setRoleName(result.getString("role_name"));
				roleEntity.setDescription(result.getString("description"));
				roles.add(roleEntity);
			}
		} catch (Exception e) {
			System.out.println("RoleRepository - findAll: " + e.getLocalizedMessage());
		}
		return roles;
	}

	// thêm role
	public int insertRole(String roleName, String description) {
		int rowInsert = 0;
		String query = "INSERT INTO roles(role_name, description) VALUES(?, ?)";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, roleName);
			statement.setString(2, description);
			rowInsert = statement.executeUpdate();
		} catch (Exception e) {
			System.out.println("RoleRepository - insert:" + e.getLocalizedMessage());
		}
		return rowInsert;
	}

	// Delete role
	public int deleteById(int id) {
		int delete = 0;
		String query = "DELETE FROM roles r WHERE r.id=?";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			delete = statement.executeUpdate();
		} catch (Exception e) {
			System.out.println("RoleRepository - deleteById: " + e.getLocalizedMessage());
		}
		return delete;
	}

	// show nội dung trước đó lên trang update
	public RoleEntity findById(int id) {
		RoleEntity roleEntity = null;
		String query = "SELECT * FROM roles WHERE id = ?";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				roleEntity = new RoleEntity();
				roleEntity.setId(result.getInt("id"));
				roleEntity.setRoleName(result.getString("role_name"));
				roleEntity.setDescription(result.getString("description"));
			}
		} catch (Exception e) {
			System.out.println("RoleRepository - findAllById: " + e.getLocalizedMessage());
		}
		return roleEntity;
	}

	// Lấy id để update roleName và description
	public int updateRoleById(int roleId, String roleName, String description) {
		int roleUpdate = 0;
		String query = "UPDATE roles SET role_name = ?, description = ? WHERE id = ?";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, roleName);
			statement.setString(2, description);
			statement.setInt(3, roleId);

			roleUpdate = statement.executeUpdate();
		} catch (Exception e) {
			System.out.println("RoleRepository - updateRoleById " + e.getLocalizedMessage());
		}
		return roleUpdate;
	}
}
