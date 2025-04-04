package com.emeka.delivery.Exceptions;


import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorObject {
    private Integer statusCode;
    private String message;
    private Date timestamp;
   

}
