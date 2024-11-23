package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.ProjectEntity;
import entity.StatusEntity;
import entity.TaskEntity;
import entity.UserEntity;
import service.TaskService;

@SuppressWarnings("serial")
@WebServlet(name = "taskController", urlPatterns = { "/tasks", "/task-add" })

public class TaskController extends HttpServlet {
	private TaskService taskService = new TaskService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		if (path.equals("/tasks")) {
			task(req, resp);
		} else if (path.equals("/task-add")) {
			List<ProjectEntity> projects = taskService.project();
			List<UserEntity> users = taskService.user();
			List<StatusEntity> status = taskService.status();
			req.setAttribute("projects", projects);
			req.setAttribute("users", users);
			req.setAttribute("status", status);
			req.getRequestDispatcher("task-add.jsp").forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		taskAdd(req, resp);
	}

	private void task(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		if (id != null) {
			taskService.deleteTask(Integer.parseInt(id));
		}
		List<TaskEntity> tasks = taskService.task();
		req.setAttribute("tasks", tasks);
		req.getRequestDispatcher("task.jsp").forward(req, resp);
	}

	private void taskAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");
		String taskName = req.getParameter("taskName");
		String startDate = req.getParameter("startDate");
		String endDate = req.getParameter("endDate");
		int userID = Integer.parseInt(req.getParameter("userID") != null ? req.getParameter("userID") : "0");
		int projectID = Integer.parseInt(req.getParameter("projectID") != null ? req.getParameter("projectID") : "0");
		int statusID = Integer.parseInt(req.getParameter("statusID") != null ? req.getParameter("statusID") : "0");
		taskService.taskAdd(taskName, startDate, endDate, userID, projectID, statusID);
		String context = req.getContextPath();
		resp.sendRedirect(context + "/tasks");
	}

}
