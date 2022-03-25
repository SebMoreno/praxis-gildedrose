package com.perficient.praxis.gildedrose.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class RepeatedItemsException extends RuntimeException{
	public RepeatedItemsException(String message) {
		super(message);
	}
}
