package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import entity.JobEntity;
import service.JobService;

@SuppressWarnings("serial")
@WebServlet(name = "jobController", urlPatterns = { "/jobs", "/groupwork-add" })
public class JobController extends HttpServlet {

	private JobService jobService = new JobService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();

		if (path.equals("/jobs")) {
			Job(req, resp);
		} else if (path.equals("/groupwork-add")) {
			addJob(req, resp);
		}
	}

	public void Job(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		if (id != null) {
			jobService.deleteJob(Integer.parseInt(id));
		}
		List<JobEntity> listJobs = jobService.listJob();
		req.setAttribute("listJobs", listJobs);
		req.getRequestDispatcher("groupwork.jsp").forward(req, resp);
	}

	public void addJob(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("groupwork-add.jsp").forward(req, resp);
		String name = req.getParameter("jobName");
		String startDate = req.getParameter("startDate");
		String endDate = req.getParameter("endDate");

		jobService.insertJob(name, startDate, endDate);

	}

}
