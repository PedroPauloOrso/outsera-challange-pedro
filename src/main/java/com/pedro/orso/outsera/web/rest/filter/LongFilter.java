package com.pedro.orso.outsera.web.rest.filter;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LongFilter {

    private Long equals;
    private Long greaterThan;
    private Long lessThan;

    public LongFilter(LongFilter filter) {
        this.equals = filter.equals;
        this.greaterThan = filter.greaterThan;
        this.lessThan = filter.lessThan;
    }
}