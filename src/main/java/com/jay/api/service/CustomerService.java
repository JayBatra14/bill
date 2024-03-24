package com.jay.api.service;

import com.jay.api.entity.Bill;
import com.jay.api.entity.Customer;
import com.jay.api.entity.Item;
import com.jay.api.repository.BillRepository;
import com.jay.api.repository.CustomerRepository;
import com.jay.api.repository.ItemRepository;
import com.jay.api.utilities.ApiRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    BillRepository billRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    private GeneratePdf generatePdf;
    @Value("${paidBillLocation}")
    private String paidBillLocation;
    @Value("${unpaidBillLocation}")
    private String unpaidBillLocation;
    @Value("${previousYear}")
    private String previousYear;
    @Value("${currentYear}")
    private String currentYear;

    public String createCustomer(ApiRequest apiRequest) {

        checkInvoice(previousYear+currentYear+apiRequest.getInvoiceNo());
        Customer customer = new Customer();
        customer.setName(apiRequest.getName());
        customer.setGstNo(apiRequest.getGstNo());
        customer.setAddress(apiRequest.getAddress());
        customer.setMobileNumber(apiRequest.getMobileNumber());

        Bill bill = new Bill();
        bill.setInvoiceNo(previousYear+currentYear+apiRequest.getInvoiceNo());
        bill.setDate(apiRequest.getDate());
        bill.setPaid(false);
        bill.setCustomer(customer);

        customerRepository.save(customer);
        billRepository.save(bill);

        apiRequest.getItems().stream().forEach(item -> item.setBill(bill));
        itemRepository.saveAll(apiRequest.getItems());

        try{
            generatePdf.generateBill(apiRequest);
        }catch(FileNotFoundException e){
            System.out.println(e.getMessage());
        }

        return "Customer Created Successfully";

    }
    public String updateCustomer(ApiRequest apiRequest, Long customerId) {
        checkInvoice(previousYear+currentYear+apiRequest.getInvoiceNo());

        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer with this id is not available"));
        customer.setName(apiRequest.getName());
        customer.setGstNo(apiRequest.getGstNo());
        customer.setAddress(apiRequest.getAddress());
        customer.setMobileNumber(apiRequest.getMobileNumber());

        Bill bill = new Bill();
        bill.setInvoiceNo(previousYear+currentYear+apiRequest.getInvoiceNo());
        bill.setDate(apiRequest.getDate());
        bill.setPaid(false);
        bill.setCustomer(customer);

        customerRepository.save(customer);
        billRepository.save(bill);

        apiRequest.getItems().stream().forEach(item -> item.setBill(bill));
        itemRepository.saveAll(apiRequest.getItems());

        try{
            generatePdf.generateBill(apiRequest);
        }catch(FileNotFoundException e){
            System.out.println(e.getMessage());
        }

        return "Bill Created Successfully";
    }
    public Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer is not available"));
    }
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
    public Customer getCustomerByGstNumber(String gstNo) {
        return customerRepository.findByGstNo(gstNo);
    }
    public Customer getCustomerByInvoiceNumber(String invoiceNo) {
        Bill bill = billRepository.findByInvoiceNo(invoiceNo);
        if(bill==null){
            return null;
        }
        return bill.getCustomer();
    }
    public void deleteCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer is not available"));
        customerRepository.delete(customer);
    }

    public void checkInvoice(String invoiceNo){
        Customer customer = getCustomerByInvoiceNumber(invoiceNo);
        if(customer!=null){
            throw new RuntimeException("Customer with this invoice number already exists");
        }
    }
    public String deleteBillByInvoiceNumber(String invoiceNo) {
        Bill bill = billRepository.findByInvoiceNo(invoiceNo);
        if(bill==null){
            throw new RuntimeException("There is no bill with this invoice number");
        }
        String filename = bill.getCustomer().getName()+"_"+bill.getInvoiceNo()+"_"+bill.getDate()+"_invoice.html";
        if(bill.isPaid()){
            File paid = new File(paidBillLocation+filename);
            paid.delete();
        }
        else{
            File unpaid = new File(unpaidBillLocation+filename);
            unpaid.delete();
        }
        billRepository.delete(bill);
        return "Bill Deleted Successfully";
    }

    public String generateBill() {
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.setName("Kavitha");
        apiRequest.setGstNo("08ACRPL4588K1ZN");
        apiRequest.setAddress("30/10, tiwari gali, rawat para, agra - 282003");
        apiRequest.setMobileNumber("7500196993");
        apiRequest.setInvoiceNo("101");
        apiRequest.setDate("26-02-24");
        Item item1 = new Item();
        item1.setName("Khandsari Sugar");
        item1.setHsnCode("17011420");
        item1.setPrice(60.0f);
        item1.setQuantity(30.0f);
        item1.setGstPercentage(0.0f);
        Item item2 = new Item();
        item2.setName("Bura");
        item2.setHsnCode("17011320");
        item2.setPrice(60.0f);
        item2.setQuantity(5.0f);
        item2.setGstPercentage(5.0f);
        Item item3 = new Item();
        item3.setName("Tagar");
        item3.setHsnCode("17011320");
        item3.setPrice(35.25f);
        item3.setQuantity(5.0f);
        item3.setGstPercentage(5.0f);
        apiRequest.getItems().add(item1);
        apiRequest.getItems().add(item2);
        apiRequest.getItems().add(item3);
        String generated="";
        try{
            generated = generatePdf.generateBill(apiRequest);
        }catch(FileNotFoundException e){
            System.out.println(e.getMessage());
            generated = "Bill Not Generated";
        }
       return generated;
    }

    public String updatePaymentStatus(Long billId) {
        Bill bill = billRepository.findById(billId).orElseThrow(() -> new RuntimeException("There is no bill with this bill id"));
        String filename = bill.getCustomer().getName()+"_"+bill.getInvoiceNo()+"_"+bill.getDate()+"_invoice.html";
        File unpaid = new File(unpaidBillLocation+filename);
        File paid = new File(paidBillLocation+filename);
        billRepository.updatePaymentStatus(billId);
        try{
            Files.move(unpaid.toPath(), paid.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch(Exception e){
            System.out.println("Error Occured while moving the file "+unpaid.getName());
        }
        return "Payment Status Updated Successfully";
    }
}
