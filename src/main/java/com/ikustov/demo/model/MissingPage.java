package com.ikustov.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class MissingPage {
    private int id;
    private int bookId;
}
