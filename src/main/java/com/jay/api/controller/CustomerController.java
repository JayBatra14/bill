package com.jay.api.controller;

import com.jay.api.entity.Customer;
import com.jay.api.service.CustomerService;
import com.jay.api.utilities.ApiRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    // POST - Create Customer
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/")
    public ResponseEntity<String> createConsumer(@RequestBody ApiRequest apiRequest) {
        return new ResponseEntity<String>(customerService.createCustomer(apiRequest), HttpStatus.CREATED);
    }

    // PUT - Update Customer By Customer Id
    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/{customerId}")
    public ResponseEntity<String> updateCustomer(@RequestBody ApiRequest apiRequest,
                                                   @PathVariable("customerId") Long customerId) {
        return ResponseEntity.ok(customerService.updateCustomer(apiRequest, customerId));
    }

    // GET - Get Customer By Customer Id
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> getCustomer(@PathVariable("customerId") Long customerId) {
        return ResponseEntity.ok(customerService.getCustomerById(customerId));
    }

    // GET - Get All Customers
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    // GET - Get Customer By GST Number
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/byGstNumber/{gstNo}")
    public ResponseEntity<Customer> getCustomerByGstNumber(@PathVariable("gstNo") String gstNo) {
        return ResponseEntity.ok(customerService.getCustomerByGstNumber(gstNo));
    }

    // GET - Get Customer By Invoice Number
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/byInvoiceNumber/{invoiceNo}")
    public ResponseEntity<Customer> getCustomerByInvoiceNumber(
            @PathVariable("invoiceNo") String invoiceNo) {
        return ResponseEntity.ok(customerService.getCustomerByInvoiceNumber(invoiceNo));
    }

    // DELETE - Delete Customer By Customer Id
    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("customerId") Long customerId) {
        customerService.deleteCustomer(customerId);
        return new ResponseEntity<String>("Customer Deleted Successfully", HttpStatus.OK);
    }

    // DELETE - Delete Bill By Invoice Number
    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/byInvoiceNumber/{invoiceNo}")
    public ResponseEntity<String> deleteBillByInvoiceNumber(
            @PathVariable("invoiceNo") String invoiceNo) {
        return ResponseEntity.ok(customerService.deleteBillByInvoiceNumber(invoiceNo));
    }

    // GET - Generate Bill Using Hard Coded Values
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/generateBill")
    public ResponseEntity<String> generateBill() {
        return new ResponseEntity<String>(customerService.generateBill(), HttpStatus.OK);
    }

    // PUT - Update Payment Status
    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/updatePaymentStatus/{billId}")
    public ResponseEntity<String> updatePaymentStatus(@PathVariable("billId") Long billId) {
        return ResponseEntity.ok(customerService.updatePaymentStatus(billId));
    }

}
