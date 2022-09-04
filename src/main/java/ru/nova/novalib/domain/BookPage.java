package ru.nova.novalib.domain;

import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
public class BookPage {
    private int pageNumber = 0;
    private int pageSize = 24;
    private Sort.Direction sortDirection = Sort.Direction.ASC;
    private String sortBy = "id";



}
