package ru.itis.pdfgenerator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PetTransferTreatyResponse {

    protected byte[] pdf;

    protected Status status;

    public enum Status{
        SUCCESS, FAULT, EXPIRED_TOKEN, INVALID_TOKEN, BANNED_TOKEN
    }

}
