package com.naveen.springboot.cashcardrestapi;

import org.springframework.data.annotation.Id;

public record CashCard(@Id Long id, Double amount, String owner) {

}
