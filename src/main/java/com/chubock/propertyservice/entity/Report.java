package com.chubock.propertyservice.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "REPORTS")
@SuperBuilder(toBuilder = true)
public class Report extends AbstractEntity {

    @NotEmpty
    private String type;

    @NotEmpty
    @Column(length = 3000)
    private String cause;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User reporter;

    private String propertyId;

}
