package com.pedro.orso.outsera.web.rest.filter;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
public class ZonedDateTimeFilter {

    private ZonedDateTime equals;
    private ZonedDateTime greaterThanOrEqual;
    private ZonedDateTime lessThanOrEqual;

    public ZonedDateTimeFilter(ZonedDateTimeFilter filter) {
        this.equals = filter.equals;
        this.greaterThanOrEqual = filter.greaterThanOrEqual;
        this.lessThanOrEqual = filter.lessThanOrEqual;
    }
}