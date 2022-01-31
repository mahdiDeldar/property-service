package com.chubock.propertyservice.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class AbstractEntity {

    @Id
    @Builder.Default
    String id = UUID.randomUUID().toString();
    @Builder.Default
    @CreatedDate
    private LocalDateTime createDate = LocalDateTime.now();
    @Builder.Default
    @LastModifiedDate
    private LocalDateTime lastModifiedDate = LocalDateTime.now();
    @Version
    private Integer version;
}
