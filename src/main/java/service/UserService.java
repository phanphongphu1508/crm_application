package service;

import java.util.List;

import entity.RoleEntity;
import entity.TaskEntity;
import entity.UserEntity;
import repository.RoleRepository;
import repository.TaskRepository;
import repository.UserRepository;
import util.MD5;

public class UserService {
	private UserRepository userRepository = new UserRepository();
	private RoleRepository roleRepository = new RoleRepository();
	private TaskRepository taskRepository = new TaskRepository();

	public List<UserEntity> users() {
		return userRepository.findAllUserWithRole();
	}

	public boolean userAdd(String firstName, String lastName, String email, String password, int role) {
		return userRepository.insertUser(firstName, lastName, email, MD5.getMd5(password), role) > 0;
	}

	public List<RoleEntity> roles() {
		return roleRepository.findAll();
	}

	public boolean deleteUser(int id) {
		return userRepository.deleteById(id) > 0;
	}
	// trả tên user và email ra user-detail
	public UserEntity userDetail(int id) {
		return userRepository.findUserById(id);
	}
	// trả task ra user-detail
	public List<TaskEntity> tasks(int id) {
		return taskRepository.taskById(id);
	}
}