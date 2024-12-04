package controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.RoleEntity;
import entity.TaskEntity;
import entity.UserEntity;
import service.UserService;

@SuppressWarnings("serial")
@WebServlet(name = "userServlet", urlPatterns = { "/users", "/user-add", "/user-edit", "/user-detail", "/user-delete" })

public class UserController extends HttpServlet {
	private UserService userService = new UserService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		if (path.equals("/users")) {
			user(req, resp);
		} else if (path.equals("/user-add")) {
			// Hiển thị danh sách role cho người dùng chọn
			List<RoleEntity> roles = userService.roles();
			req.setAttribute("roles", roles);
			req.getRequestDispatcher("user-add.jsp").forward(req, resp);
			userAdd(req, resp);
		} else if (path.equals("/user-detail")) {
			userDetail(req, resp);
		} else if (path.equals("/user-edit")) {
			userEdit(req, resp);
		}
	}

	// Thêm nhân viên
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		if (path.equals("/user-delete")) {
			userDelete(req, resp);
		} else if (path.equals("/user-add")) {
			userAdd(req, resp);
		} else if (path.equals("/user-edit")) {
			userEditPost(req, resp);
		}
	}

	// Hiển thi tất cả nhân viên
	private void user(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<UserEntity> users = userService.users();
		req.setAttribute("users", users);
		req.getRequestDispatcher("users.jsp").forward(req, resp);
	}

	// thêm nhân viên với phương thức post
	private void userAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		int role = Integer.parseInt(req.getParameter("role") != null ? req.getParameter("role") : "0");
		userService.userAdd(firstName, lastName, email, password, role);
		resp.sendRedirect(req.getContextPath() + "/users");
	}

	private void userDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		int id = Integer.parseInt(req.getParameter("id") != null ? req.getParameter("id") : "0");
		userService.deleteUser(id);
		String context = req.getContextPath();
		resp.sendRedirect(context + "/users");
	}

	private void userDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Lấy id từ URL
		int id = Integer.parseInt(req.getParameter("id") != null ? req.getParameter("id") : "0");
		// Lấy chi tiết người dùng từ service
		UserEntity user = userService.userDetail(id);
		List<TaskEntity> tasks = userService.tasks(id);

		Map<String, List<TaskEntity>> groupedTasks = tasks.stream()
				.collect(Collectors.groupingBy(task -> task.getStatus().getStatusName()));

		req.setAttribute("user", user);
		req.setAttribute("groupedTasks", groupedTasks);
		// Chuyển hướng tới trang chi tiết người dùng
		req.getRequestDispatcher("user-details.jsp").forward(req, resp);
	}

	// Show list role edit
	// Show thông tin user trước đó để edit
	private void userEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id") != null ? req.getParameter("id") : "0");
		UserEntity user = userService.showUserEdit(id);
		List<RoleEntity> roles = userService.roles();
		req.setAttribute("user", user);
		req.setAttribute("roles", roles);
		req.getRequestDispatcher("user-edit.jsp").forward(req, resp);
	}

	// Edit user
	private void userEditPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");
		int userId = Integer.parseInt(req.getParameter("userId") != null ? req.getParameter("userId") : "0");
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		int role = Integer.parseInt(req.getParameter("role") != null ? req.getParameter("role") : "0");
		userService.userEdit(firstName, lastName, email, password, role, userId);
		resp.sendRedirect(req.getContextPath() + "/users");
	}

}
