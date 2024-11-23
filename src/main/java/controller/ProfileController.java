package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import entity.StatusEntity;
import entity.TaskEntity;
import entity.UserEntity;
import service.ProfileService;

@WebServlet(name = "profileController", urlPatterns = { "/profile", "/profile-edit" })
@SuppressWarnings("serial")
public class ProfileController extends HttpServlet {
	ProfileService profileService = new ProfileService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		if (path.equals("/profile")) {
			profile(req, resp);
		} else if (path.equals("/profile-edit")) {
			profileEdit(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		if (path.equals("/profile-edit")) {
			profileEditPost(req, resp);
		}
	}

	public void profile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Lấy cookie người dùng
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("userId".equals(cookie.getName())) {
					// Giải mã ID người dùng từ cookie (giả sử bạn đã mã hóa ID khi tạo cookie)
					int userId = Integer.parseInt(cookie.getValue());
					UserEntity user = profileService.userProfile(userId);
					List<TaskEntity> tasks = profileService.profile(userId);
					req.setAttribute("user", user);
					req.setAttribute("tasks", tasks);
					break;
				}
			}
		}
		req.getRequestDispatcher("profile.jsp").forward(req, resp);
	}

	public void profileEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id") != null ? req.getParameter("id") : "0");
		TaskEntity task = profileService.taskProfileEdit(id);
		List<StatusEntity> status = profileService.statusProfileEdit();
		req.setAttribute("task", task);
		req.setAttribute("status", status);
		req.getRequestDispatcher("profile-edit.jsp").forward(req, resp);
	}

	public void profileEditPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		int taskId = Integer.parseInt(req.getParameter("taskId") != null ? req.getParameter("taskId") : "0");
		int statusId = Integer.parseInt(req.getParameter("statusId") != null ? req.getParameter("statusId") : "0");
		profileService.profileEdit(taskId, statusId);
		String context = req.getContextPath();
		resp.sendRedirect(context + "/profile");
	}

}
