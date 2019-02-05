package com.test.mojdemo.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
	
	private String id;

	@NotNull(message = "firstName can not be null")
	@NotBlank(message = "firstName can not be blank")
	private String firstName;

	@NotNull(message = "secondName can not be null")
	@NotBlank(message = "secondName can not be blank")
	private String secondName;

	@NotNull(message = "accountNumber can not be null")
	@NotBlank(message = "accountNumber can not be blank")
	private String accountNumber;
}
