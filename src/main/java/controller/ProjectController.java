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

import entity.ProjectEntity;
import entity.TaskEntity;
import entity.UserEntity;
import service.ProjectService;

@SuppressWarnings("serial")
@WebServlet(name = "jobController", urlPatterns = { "/project", "/project-add", "/project-detail", "/project-edit" })
public class ProjectController extends HttpServlet {
	private ProjectService projectService = new ProjectService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		if (path.equals("/project")) {
			project(req, resp);
		} else if (path.equals("/project-add")) {
			List<UserEntity> users = projectService.users();
			req.setAttribute("users", users);
			req.getRequestDispatcher("project-add.jsp").forward(req, resp);
		} else if (path.equals("/project-detail")) {
			projectDetail(req, resp);
		} else if (path.equals("/project-edit")) {
			List<UserEntity> users = projectService.users();
			req.setAttribute("users", users);
			projectEdit(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		if (path.equals("/project-edit")) {
			projectEditPost(req, resp);
		} else if (path.equals("/project-add")) {
			projectAdd(req, resp);
		}
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
		int managerId = Integer.parseInt(req.getParameter("managerId") != null ? req.getParameter("managerId") : "0");
		projectService.projectAdd(projectName, startDate, endDate, managerId);
		String context = req.getContextPath();
		resp.sendRedirect(context + "/project");
	}

	public void projectEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id") != null ? req.getParameter("id") : "0");
		ProjectEntity project = projectService.showProjectEdit(id);
		req.setAttribute("project", project);
		req.getRequestDispatcher("project-edit.jsp").forward(req, resp);
	}

	private void projectEditPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");
		int projectId = Integer.parseInt(req.getParameter("projectId") != null ? req.getParameter("projectId") : "0");
		String projectName = req.getParameter("projectName");
		String startDate = req.getParameter("startDate");
		String endDate = req.getParameter("endDate");
		int managerId = Integer.parseInt(req.getParameter("managerId") != null ? req.getParameter("managerId") : "0");
		projectService.projectEdit(projectId, projectName, startDate, endDate, managerId);
		resp.sendRedirect(req.getContextPath() + "/project");
	}

	// show user detail
	public void projectDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id") != null ? req.getParameter("id") : "0");
		List<TaskEntity> tasks = projectService.projectDetail(id);
		
		// Nhóm tasks theo user.fullName và task.status.statusName
	    Map<String, Map<String, List<TaskEntity>>> groupedTasks = tasks.stream()
	        .collect(Collectors.groupingBy(
	            task -> task.getUser().getFullName(), // Nhóm theo user.fullName
	            Collectors.groupingBy(task -> task.getStatus().getStatusName()) // Nhóm con theo statusName
	        ));

	    req.setAttribute("groupedTasks", groupedTasks);
		req.getRequestDispatcher("project-detail.jsp").forward(req, resp);
	}
}