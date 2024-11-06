package controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import entity.JobEntity;
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
			addTask(req, resp);
		}
	}

	private void task(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		if (id != null) {
			taskService.deleteTask(Integer.parseInt(id));
		}

		List<TaskEntity> listTasks = taskService.task();
		req.setAttribute("listTasks", listTasks);
		req.getRequestDispatcher("task.jsp").forward(req, resp);
	}

	private void addTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<JobEntity> listJob = taskService.listJob();
		List<UserEntity> listUser = taskService.listUser();
		List<StatusEntity> listStatus = taskService.listStatus();

		req.setAttribute("listJob", listJob);
		req.setAttribute("listUser", listUser);
		req.setAttribute("listStatus", listStatus);

		String name = req.getParameter("name");
		String startDate = req.getParameter("startDate");
		String endDate = req.getParameter("endDate");
		int userId = Integer.parseInt(req.getParameter("userId") != null ? req.getParameter("userId") : "0");
		int jobId = Integer.parseInt(req.getParameter("jobId") != null ? req.getParameter("jobId") : "0");
		int statusId = Integer.parseInt(req.getParameter("statusId") != null ? req.getParameter("statusId") : "0");

		taskService.addTask(name, startDate, endDate, userId, jobId, statusId);

		req.getRequestDispatcher("task-add.jsp").forward(req, resp);

	}
}
