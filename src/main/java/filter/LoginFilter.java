package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/*") // Áp dụng cho tất cả các URL
public class LoginFilter implements Filter {

	@Override
	public void doFilter(javax.servlet.ServletRequest request, javax.servlet.ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		// Lấy URL đang được yêu cầu
		String requestURI = req.getRequestURI();

		// Nếu yêu cầu là trang /login, cho phép truy cập
		if (requestURI.endsWith("/login")) {
			chain.doFilter(request, response); // Cho phép truy cập vào trang login
			return;
		}
		// Kiểm tra nếu URL là /logout (người dùng muốn đăng xuất)
		if (requestURI.endsWith("/logout")) {
			// Xóa session
			req.getSession().invalidate();

			// Xóa cookie nếu có
			Cookie[] cookies = req.getCookies();
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if ("userEmail".equals(cookie.getName())) {
						cookie.setMaxAge(0); // Đặt tuổi thọ cookie thành 0 để xóa nó
						cookie.setPath(req.getContextPath());
						resp.addCookie(cookie); // Gửi cookie xóa về trình duyệt
					}
				}
			}

			// Chuyển hướng về trang login sau khi đăng xuất
			resp.sendRedirect(req.getContextPath() + "/login");
			return; // Không tiếp tục filter chain nữa, đã thực hiện đăng xuất
		}

		// Nếu yêu cầu không phải là đăng xuất, kiểm tra session để xem người dùng đã
		// đăng nhập chưa
		HttpSession session = req.getSession(false); // Kiểm tra session nếu có
		boolean isLoggedIn = session != null && session.getAttribute("user") != null;

		// Nếu chưa đăng nhập, kiểm tra cookie "userEmail"
		if (!isLoggedIn) {
			Cookie[] cookies = req.getCookies();
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if ("userEmail".equals(cookie.getName())) {
						String userEmail = cookie.getValue();
						// Nếu cookie tồn tại, bạn có thể xác nhận và khôi phục thông tin người dùng
						// (Ví dụ: tìm người dùng trong cơ sở dữ liệu bằng email)
						// Sau đó, thêm thông tin người dùng vào session
						session = req.getSession(true); // Tạo session mới nếu chưa có
						session.setAttribute("user", userEmail);
						isLoggedIn = true; // Đánh dấu người dùng đã đăng nhập
						break;
					}
				}
			}
		}

		// Nếu người dùng chưa đăng nhập, chuyển hướng về trang login
		if (!isLoggedIn) {
			resp.sendRedirect(req.getContextPath() + "/login"); // Chuyển hướng đến trang đăng nhập
		} else {
			// Nếu đã đăng nhập, cho phép yêu cầu tiếp tục
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {
		// Hủy các tài nguyên nếu cần
	}
}
