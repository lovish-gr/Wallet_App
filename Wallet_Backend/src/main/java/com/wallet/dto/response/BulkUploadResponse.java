package com.wallet.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BulkUploadResponse {
    private List<String> successEntries;
    private List<ErrorRow> errorEntries;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ErrorRow {
        private int rowNumber;
        private String errorMessage;
    }
}