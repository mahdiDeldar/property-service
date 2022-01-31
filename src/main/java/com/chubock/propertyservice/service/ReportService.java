package com.chubock.propertyservice.service;

import com.chubock.propertyservice.component.EmailSender;
import com.chubock.propertyservice.entity.Report;
import com.chubock.propertyservice.model.ModelFactory;
import com.chubock.propertyservice.model.ReportModel;
import com.chubock.propertyservice.repository.ReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReportService {

    private static final String REPORT_EMAIL_RECEIVER = "info@housemate.space";

    private final ReportRepository reportRepository;

    private final UserService userService;

    private final EmailSender emailSender;

    public ReportService(ReportRepository reportRepository, UserService userService, EmailSender emailSender) {
        this.reportRepository = reportRepository;
        this.userService = userService;
        this.emailSender = emailSender;
    }

    @Transactional
    public ReportModel save(String userId, ReportModel model) {

        Report report = Report.builder()
                .type(model.getType())
                .text(model.getText())
                .reporter(userService.get(userId))
                .build();

        reportRepository.save(report);

        emailSender.send(REPORT_EMAIL_RECEIVER, "Housemate Report: " + report.getType(), report.getText());

        return ModelFactory.of(report, ReportModel.class);

    }

}
