package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.LoginService;

@SuppressWarnings("serial")
@WebServlet(name = "LoginController", urlPatterns = { "/login" })
public class LoginController extends HttpServlet {

	private LoginService loginService = new LoginService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String remember = req.getParameter("remember");

		boolean isSuccess = loginService.checkLogin(email, password, remember, req, resp);

		String context = req.getContextPath();

		if (isSuccess) {
			resp.sendRedirect(context + "/index");
		} else {
			resp.sendRedirect(context + "/login");
		}
		System.out.println("Check Login: " + isSuccess);

	}
}
