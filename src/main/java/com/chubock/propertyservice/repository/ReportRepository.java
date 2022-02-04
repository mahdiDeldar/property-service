package com.chubock.propertyservice.repository;

import com.chubock.propertyservice.entity.Report;
import com.chubock.propertyservice.entity.User;

import java.util.List;

public interface ReportRepository extends AbstractRepository<Report> {
    List<Report> findReportsByReporter(User user);
}
