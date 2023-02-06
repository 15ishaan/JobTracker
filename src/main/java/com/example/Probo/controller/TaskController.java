package com.example.Probo.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.Probo.model.JobDescription;

@RestController
public class TaskController {

  private List<JobDescription> jobs = new ArrayList<JobDescription>();

  @PostMapping(value = "/post/add")
  public ResponseEntity<?> addValue(@RequestParam("jobValue") Float jobValue,
      @RequestParam("jobId") Long jobId) throws Exception {
    try {
      jobs.add(new JobDescription(jobId, jobValue));
      return new ResponseEntity<>("Adding of Data Successful", HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping(value = "/get/all")
  public ResponseEntity<?> getValues(@RequestParam("jobValue") Float jobValue) throws Exception {
    try {
      List<JobDescription> result =
          jobs.stream().filter(jobDescription -> jobDescription.getJobValue() >= jobValue)
              .collect(Collectors.toList());

      Comparator<JobDescription> jobValueComparator =
          (c1, c2) -> c1.getJobValue().compareTo(c2.getJobValue());

      result.sort(jobValueComparator);
      return new ResponseEntity<>(result, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping(value = "/post/remove")
  public ResponseEntity<?> removeValue(@RequestParam("jobId") Long jobId) throws Exception {
    try {
      List<JobDescription> result =
          jobs.stream().filter(jobDescription -> jobDescription.getJobId() != jobId)
              .collect(Collectors.toList());
      if (result.size() == jobs.size()) {
        return new ResponseEntity<>("No such data present", HttpStatus.OK);
      }
      jobs = result;
      return new ResponseEntity<>("Removing of Data Successful", HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

}
