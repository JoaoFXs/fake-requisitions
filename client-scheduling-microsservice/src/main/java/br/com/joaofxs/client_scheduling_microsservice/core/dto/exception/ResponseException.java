package br.com.joaofxs.client_scheduling_microsservice.core.dto.exception;

public record ResponseException(String status, int code, String message) {
}
