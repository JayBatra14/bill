package com.jay.api.utilities;

import com.jay.api.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiRequest {

    private String name;

    private String gstNo;

    private String address;

    private String mobileNumber;

    private String invoiceNo;

    private String date;

    private List<Item> items = new ArrayList<>();

}

/*
{
  "name": "Jay",
  "gstNo": "ABC",
  "address": "Kamla Nagar",
  "mobileNumber": "9557",
  "invoiceNo": "101",
  "date": "25-02-24",
  "items": [
    {
      "hsnCode":"P101",
      "name": "Black Salt",
      "price": 10.0,
      "quantity": 2.5,
      "gstPercentage": 5.0
    },
    {
      "hsnCode":"P102",
      "name": "Boora",
      "price": 50.5,
      "quantity": 1.0,
      "gstPercentage": 5.0
    }
  ]
}

{
  "name": "Deep",
  "gstNo": "DEF",
  "address": "Kamla Nagar",
  "mobileNumber": "7500",
  "invoiceNo": "102",
  "date": "25-02-24",
  "items": [
    {
      "hsnCode":"P101",
      "name": "Black Salt",
      "price": 10.0,
      "quantity": 4.5,
      "gstPercentage": 5.0
    },
    {
      "hsnCode":"P102",
      "name": "Boora",
      "price": 50.5,
      "quantity": 2.0,
      "gstPercentage": 5.0
    }
  ]
}
 */