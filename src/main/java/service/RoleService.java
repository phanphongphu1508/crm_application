package service;

import java.util.List;
import entity.RoleEntity;
import repository.RoleRepository;

public class RoleService {
	private RoleRepository roleRepository = new RoleRepository();

	public List<RoleEntity> listRole() {
		List<RoleEntity> listRoles = roleRepository.findAll();

		return listRoles;
	}

	public boolean insertRole(String name, String description) {
		return roleRepository.insert(name, description) > 0;
	}

	public boolean deleteRole(int id) {
		return roleRepository.deleteById(id) > 0;
	}

}
