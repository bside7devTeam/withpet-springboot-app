package org.gig.withpet.core.domain.user.administrator.dto;

import lombok.Getter;
import lombok.Setter;
import org.gig.withpet.core.domain.common.dto.BaseSearchDto;
import org.gig.withpet.core.domain.user.UserStatus;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * @author : JAKE
 * @date : 2022/05/29
 */
@Getter
@Setter
public class AdminSearchDto extends BaseSearchDto {

    private String username;
    private String name;
    private UserStatus userStatus;

    public PageRequest getPageableWithSort() {
        return PageRequest.of(getPage(), getSize(), Sort.by(new Sort.Order(Sort.Direction.DESC, "id")));
    }
}
