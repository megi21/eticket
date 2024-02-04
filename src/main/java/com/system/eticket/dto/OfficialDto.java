package com.system.eticket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OfficialDto {

	private Long officialId;
	private String name;
	private String officialCode;
	private String birthDate;

}
