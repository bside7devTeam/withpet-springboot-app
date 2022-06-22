package org.gig.withpet.core.domain.Area.emdArea;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.gig.withpet.core.data.vWorldAddress.dto.AddressResDto;
import org.gig.withpet.core.domain.Area.siggArea.SiggArea;
import org.gig.withpet.core.domain.common.BaseTimeEntity;

import javax.persistence.*;

/**
 * @author : JAKE
 * @date : 2022/06/04
 */
@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class EmdArea extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emd_id")
    private Long id;

    private String admCode;

    private String admName;

    private String version;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sigg_id")
    private SiggArea sigg;

    public static EmdArea insertPublicData(AddressResDto address, String admName) {
        return EmdArea
                .builder()
                .admCode(address.getId())
                .admCode(admName)
                .build();
    }

    public void addParent(SiggArea siggArea) {
        this.sigg = siggArea;
        siggArea.getEmdAreas().add(this);
    }
}
