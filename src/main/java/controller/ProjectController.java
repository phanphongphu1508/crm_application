package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.ProjectEntity;
import service.ProjectService;

@SuppressWarnings("serial")
@WebServlet(name = "jobController", urlPatterns = { "/project", "/project-add", "/project-detail" })
public class ProjectController extends HttpServlet {
	private ProjectService projectService = new ProjectService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();

		if (path.equals("/project")) {
			project(req, resp);
		} else if (path.equals("/project-add")) {
			req.getRequestDispatcher("project-add.jsp").forward(req, resp);
		} else if (path.equals("/project-detail")) {
			projectDetail(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		projectAdd(req, resp);
	}

	public void project(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		if (id != null) {
			projectService.deleteProject(Integer.parseInt(id));
		}
		List<ProjectEntity> projects = projectService.project();
		req.setAttribute("projects", projects);
		req.getRequestDispatcher("project.jsp").forward(req, resp);
	}

	public void projectAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");
		String projectName = req.getParameter("projectName");
		String startDate = req.getParameter("startDate");
		String endDate = req.getParameter("endDate");
		projectService.projectAdd(projectName, startDate, endDate);
		String context = req.getContextPath();
		resp.sendRedirect(context + "/project");
	}

	public void projectDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		int id = Integer.parseInt(req.getParameter("id") != null ? req.getParameter("id") : "0");
		req.getRequestDispatcher("project-detail.jsp").forward(req, resp);

	}
}
