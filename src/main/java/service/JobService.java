package service;

import java.util.List;

import entity.JobEntity;
import repository.JobRepository;

public class JobService {
	private JobRepository jobRepository = new JobRepository();

	public List<JobEntity> listJob() {
		List<JobEntity> listJob = jobRepository.findAll();

		return listJob;
	}

	public boolean insertJob(String name, String startDate, String endDate) {
		return jobRepository.insertJob(name, startDate, endDate) > 0;
	}

	public boolean deleteJob(int id) {
		return jobRepository.deleteById(id) > 0;
	}

}
