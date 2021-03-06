package com.videoconference.dto.pagination;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaginationParams {
    @Positive
    private int page;
    @Positive
    private int size;
    private String[] sort;
    private String keyword;

    public PaginationParams() {
        this.page = 1;
        this.size = 10;
        this.sort = null;
        this.keyword = "";
    }
}
