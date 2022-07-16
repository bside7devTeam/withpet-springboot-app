package org.gig.withpet.core.domain.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PageResponseDto<T> {
    int page;
    int size;
    Long totalCount;
    List<T> list;
}
