package com.submitter.val.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Submitter {

	private String submitterID;
	private String correlationID;
	private List<String> errorCode = new ArrayList<String>();

}
