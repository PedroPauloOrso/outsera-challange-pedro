package com.pedro.orso.outsera.web.rest.filter;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StringFilter {

    private String equals;
    private String contains;
    private String startsWith;
    private String endsWith;

    public StringFilter(StringFilter filter) {
        this.equals = filter.equals;
        this.contains = filter.contains;
        this.startsWith = filter.startsWith;
        this.endsWith = filter.endsWith;
    }
}