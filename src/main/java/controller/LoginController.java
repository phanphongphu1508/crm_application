package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.LoginService;

@SuppressWarnings("serial")
@WebServlet(name = "LoginController", urlPatterns = { "/login", "/logout" })
public class LoginController extends HttpServlet {

	private LoginService loginService = new LoginService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String requestURI = req.getRequestURI();

		if (requestURI.endsWith("/logout")) {
			loginService.logout(req, resp); // Đăng xuất và xóa cookie
			resp.sendRedirect(req.getContextPath() + "/login");
		} else {
			req.getRequestDispatcher("login.jsp").forward(req, resp); // Hiển thị trang đăng nhập
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String remember = req.getParameter("remember");

		boolean isSuccess = loginService.checkLogin(email, password, remember, req, resp);
		if (isSuccess) {
			resp.sendRedirect(req.getContextPath() + "/index"); // Chuyển đến trang chính
		} else {
			req.setAttribute("errorMessage", "Invalid email or password!");
			req.getRequestDispatcher("login.jsp").forward(req, resp); // Quay lại trang đăng nhập
		}
	}
}
