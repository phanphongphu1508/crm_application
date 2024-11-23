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
@WebServlet(name = "rolesController", urlPatterns = { "/roles", "/role-add" })
public class RolesController extends HttpServlet {
	private RoleService roleService = new RoleService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();

		if (path.equals("/roles")) {
			role(req, resp);
		} else if (path.equals("/role-add")) {
			req.getRequestDispatcher("role-add.jsp").forward(req, resp);	
		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		roleAdd(req, resp);
	}
	

	private void role(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		if (id != null) {
			roleService.deleteRole(Integer.parseInt(id));
		}
		List<RoleEntity> roles = roleService.role();
		req.setAttribute("roles", roles);
		req.getRequestDispatcher("role-table.jsp").forward(req, resp);
	}

	private void roleAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");
		String roleName = req.getParameter("roleName");
		String description = req.getParameter("description");
		roleService.roleAdd(roleName, description);
		String context = req.getContextPath();
		resp.sendRedirect(context + "/roles");
		
	}

}
