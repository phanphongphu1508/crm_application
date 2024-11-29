package service;

import java.util.List;

import entity.RoleEntity;
import repository.RoleRepository;

public class RoleService {
	private RoleRepository roleRepository = new RoleRepository();

	public List<RoleEntity> role() {
		return roleRepository.findAll();
	}

	public boolean roleAdd(String roleName, String description) {
		return roleRepository.insertRole(roleName, description) > 0;
	}

	public boolean deleteRole(int id) {
		return roleRepository.deleteById(id) > 0;
	}

	// show nội dung trước đó lên trang update
	public RoleEntity showRoleEdit(int id) {
		return roleRepository.findById(id);
	}

	// Lấy id để update roleName và description
	public boolean roleEdit(int id, String roleName, String description) {
		return roleRepository.updateRoleById(id, roleName, description) > 0;
	}

}
