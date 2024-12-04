package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/*") // Áp dụng cho tất cả các URL
public class LoginAndRoleFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(javax.servlet.ServletRequest request, javax.servlet.ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		String requestURI = req.getRequestURI();

		// Cho phép truy cập các URL không cần đăng nhập
		if (requestURI.endsWith("/login") || requestURI.endsWith("/logout")) {
			chain.doFilter(request, response);
			return;
		}
		boolean isLoggedIn = checkLoginFromCookies(req);

		if (isLoggedIn) {
			String userRole = getUserRoleFromCookies(req);

			// Nếu người dùng là ROLE_ADMIN, cho phép truy cập mọi URL
			if ("ROLE_ADMIN".equals(userRole)) {
				chain.doFilter(request, response);
			} else if ("ROLE_MANAGER".equals(userRole)) {
				if (requestURI.endsWith("/index") || requestURI.endsWith("/profile")
						|| requestURI.endsWith("/profile-edit") || requestURI.endsWith("/users")
						|| requestURI.endsWith("/user-detail") || requestURI.endsWith("/user-add")
						|| requestURI.endsWith("/user-edit") || requestURI.endsWith("/project")
						|| requestURI.endsWith("/project-add") || requestURI.endsWith("/project-edit")
						|| requestURI.endsWith("/project-detail") || requestURI.endsWith("/tasks")
						|| requestURI.endsWith("/task-add") || requestURI.endsWith("/task-edit")) {
					chain.doFilter(request, response); // Cho phép truy cập
				} else {
					resp.sendRedirect(req.getContextPath() + "/index"); // Trang thông báo quyền truy cập
				}

			} else if ("ROLE_USER".equals(userRole)) {

				if (requestURI.endsWith("/index") || requestURI.endsWith("/profile")
						|| requestURI.endsWith("/profile-edit")) {
					chain.doFilter(request, response); // Cho phép truy cập
				} else {
					resp.sendRedirect(req.getContextPath() + "/index"); // Trang thông báo quyền truy cập
				}
			}
		} else {
			resp.sendRedirect(req.getContextPath() + "/login"); // Chuyển hướng đến trang đăng nhập
		}
	}

	/**
	 * Kiểm tra trạng thái đăng nhập dựa trên cookie `userId`.
	 */
	private boolean checkLoginFromCookies(HttpServletRequest req) {
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("userId".equals(cookie.getName())) {
					return true; // Có thông tin đăng nhập trong cookie
				}
			}
		}
		return false; // Không tìm thấy thông tin đăng nhập
	}

	private String getUserRoleFromCookies(HttpServletRequest req) {
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("roleName".equals(cookie.getName())) {
					return cookie.getValue(); // Trả về vai trò của người dùng
				}
			}
		}
		return null; // Không có vai trò trong cookie
	}
}