package org.gig.withpet.core.domain.attachment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.gig.withpet.core.domain.attachment.types.FileType;
import org.gig.withpet.core.domain.attachment.types.UsageType;
import org.gig.withpet.core.domain.common.BaseTimeEntity;

import javax.persistence.*;

/**
 * @author : JAKE
 * @date : 2022/07/24
 */
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Attachment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attachment_id")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private FileType fileType;

    private String orgFilename;

    private String savedFilename;

    private String fullPath;

    @Enumerated(value = EnumType.STRING)
    private UsageType usageType;

    public static Attachment Of(UsageType usageType, FileType fileType, String orgFilename, String savedFilename, String fullPath) {
        return Attachment.builder()
                .usageType(usageType)
                .fileType(fileType)
                .orgFilename(orgFilename)
                .savedFilename(savedFilename)
                .fullPath(fullPath)
                .build();
    }

}
