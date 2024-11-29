package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.RoleEntity;
import service.RoleService;

@SuppressWarnings("serial")
@WebServlet(name = "rolesController", urlPatterns = { "/roles", "/role-add", "/role-edit" })
public class RolesController extends HttpServlet {
	private RoleService roleService = new RoleService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();

		if (path.equals("/roles")) {
			role(req, resp);
		} else if (path.equals("/role-add")) {
			req.getRequestDispatcher("role-add.jsp").forward(req, resp);
		} else if (path.equals("/role-edit")) {
			roleEdit(req, resp);
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		if (path.equals("/role-add")) {
			roleAdd(req, resp);
		} else if (path.equals("/role-edit")) {
			roleEditPost(req, resp);
		}

	}
	// show all role
	private void role(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		if (id != null) {
			roleService.deleteRole(Integer.parseInt(id));
		}
		List<RoleEntity> roles = roleService.role();
		req.setAttribute("roles", roles);
		req.getRequestDispatcher("role-table.jsp").forward(req, resp);
	}
	// Thêm role
	private void roleAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");
		String roleName = req.getParameter("roleName");
		String description = req.getParameter("description");
		roleService.roleAdd(roleName, description);
		resp.sendRedirect(req.getContextPath() + "/roles");

	}

	// show nội dung trước đó lên trang update
	private void roleEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id") != null ? req.getParameter("id") : "0");
		RoleEntity role = roleService.showRoleEdit(id);
		req.setAttribute("role", role);
		req.getRequestDispatcher("role-edit.jsp").forward(req, resp);
	}

	// Lấy id để update roleName và description
	private void roleEditPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");
		int roleId = Integer.parseInt(req.getParameter("roleId") != null ? req.getParameter("roleId") : "0");
		String roleName = req.getParameter("roleName");
		String description = req.getParameter("description");
		roleService.roleEdit(roleId, roleName, description);
		resp.sendRedirect(req.getContextPath() + "/roles");

	}
}
