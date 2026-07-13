package com.rajendraelectronics.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rajendraelectronics.dto.DashboardResponseDto;
import com.rajendraelectronics.service.DashboardService;

@RestController
@RequestMapping("dashboard")
public class DashboardController {

	 private final DashboardService dashboardService;

	 public DashboardController(
	      DashboardService dashboardService) {
	      this.dashboardService = dashboardService;
	 }

	 @GetMapping
	 public DashboardResponseDto getDashboardData() {
	      return dashboardService.getDashboardData();
	 }
}
