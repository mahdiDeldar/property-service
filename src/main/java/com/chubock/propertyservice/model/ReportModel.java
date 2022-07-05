package com.chubock.propertyservice.model;


import com.chubock.propertyservice.entity.Report;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class ReportModel extends AbstractModel<Report> {

    @NotEmpty
    private String type;
    @Builder.Default
    private String cause = "-";
    private String text;
    private UserModel reporter;
    private String propertyId;

    @Override
    public void fill(Report entity) {
        super.fill(entity);

        setType(entity.getType());
        setCause(entity.getCause());
        setText(entity.getText());
        setPropertyId(entity.getPropertyId());

    }
}
