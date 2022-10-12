package net.leonemc.lithium.reports.objects;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Report {

    private UUID id;
    private String sender;
    private String target;
    private ReportCategory category;
    private String reason;

}