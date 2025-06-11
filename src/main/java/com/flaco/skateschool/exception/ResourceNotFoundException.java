package com.flaco.skateschool.exception;

// Custom exception for when a resource is not found
public class ResourceNotFoundException extends RuntimeException {
  public ResourceNotFoundException(String message) {
    super(message);
  }
}