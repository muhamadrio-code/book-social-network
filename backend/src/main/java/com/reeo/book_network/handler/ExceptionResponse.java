package com.reeo.book_network.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.Set;

@Getter
@Builder
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class ExceptionResponse {
  private Integer businessErrorCode;
  private String businessErrorDescription;
  private String error;
  private Set<String> validationErrors;
  private Map<String, String> errors;
}
