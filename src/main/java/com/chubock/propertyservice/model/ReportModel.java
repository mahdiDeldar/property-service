package com.chubock.propertyservice.model;


import com.chubock.propertyservice.entity.Report;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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

    @NotEmpty
    private String text;

    private UserModel reporter;

    @Override
    public void fill(Report entity) {
        super.fill(entity);

        setType(entity.getType());
        setText(entity.getText());

    }
}