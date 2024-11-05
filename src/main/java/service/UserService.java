package service;

import java.util.List;
import entity.RoleEntity;
import entity.UserEntity;
import repository.RoleRepository;
import repository.UserRepository;
import util.MD5;

public class UserService {

	private UserRepository userRepository = new UserRepository();
	private RoleRepository roleRepository = new RoleRepository();

	public List<UserEntity> listUser() {
		List<UserEntity> listUsers = userRepository.findAll();
		return listUsers;
	}

	public List<RoleEntity> allRole() {
		List<RoleEntity> listRoles = roleRepository.findAll();

		return listRoles;
	}

	public boolean insertUser(String firstname, String lastname, String phone, String email, String password,
			int role) {

		return userRepository.insertUser(firstname, lastname, phone, email, MD5.getMd5(password), role) > 0;
	}

	public boolean deleteUser(int id) {

		int count = userRepository.deleteById(id);

		return count > 0;
	}

	public List<UserEntity> listUserDetail() {
		List<UserEntity> listUserDetail = userRepository.findUserAndTaskAndStatus();
		return listUserDetail;

	}

	/*
	 * public boolean updateUser(int id, String firstname, String lastname, String
	 * email, String password, int role) {
	 * 
	 * return userRepository.updateUser(id, firstname, lastname, email,
	 * MD5.getMd5(password), role) > 0; }
	 */

}
