package controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import entity.RoleEntity;
import entity.UserEntity;
import service.UserService;

@SuppressWarnings("serial")
@WebServlet(name = "userServlet", urlPatterns = { "/users", "/user-add", "/user-edit", "/user-detail" })

public class UserController extends HttpServlet {
	private UserService userService = new UserService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Trả ra đường dẫn servlet đang gọi
		String path = req.getServletPath();

		if (path.equals("/users")) {
			user(req, resp);
		} else if (path.equals("/user-add")) {
			addUser(req, resp);
		} else if (path.equals("/user-details")) {
			userDetail(req, resp);
		}

		/*
		 * else if (path.equals("/user-edit")) { updateUser(req, resp); }
		 */
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		if (path.equals("/user-add")) {
			addUserPost(req, resp);
		}
	}

	private void addUserPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String firstname = req.getParameter("firstname");
		String lastname = req.getParameter("lastname");
		String phone = req.getParameter("phone");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		int role = Integer.parseInt(req.getParameter("role") != null ? req.getParameter("role") : "0");

		userService.insertUser(firstname, lastname, phone, email, password, role);
		List<RoleEntity> listRole = userService.allRole();
		req.setAttribute("listRole", listRole);
		req.getRequestDispatcher("user-add.jsp").forward(req, resp);
	}

	private void user(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		if (id != null) {
			userService.deleteUser(Integer.parseInt(id));
		}
		List<UserEntity> listUsers = userService.listUser();
		req.setAttribute("listUsers", listUsers);
		req.getRequestDispatcher("user-table.jsp").forward(req, resp);
	}

	private void addUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<RoleEntity> listRole = userService.allRole();
		req.setAttribute("listRole", listRole);
		req.getRequestDispatcher("user-add.jsp").forward(req, resp);
	}

	private void userDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		List<UserEntity> userDetail = userService.userDetail();
		req.setAttribute("userDetail", userDetail);
		req.getRequestDispatcher("user-details.jsp").forward(req, resp);
	}
	
	/*
	 * private void updateUser(HttpServletRequest req, HttpServletResponse resp)
	 * throws ServletException, IOException {
	 * 
	 * req.getRequestDispatcher("user-edit.jsp").forward(req, resp);
	 * 
	 * }
	 */
}



