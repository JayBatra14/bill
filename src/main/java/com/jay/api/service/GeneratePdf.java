package com.jay.api.service;

import com.jay.api.entity.Item;
import com.jay.api.utilities.ApiRequest;
import com.jay.api.utilities.NumberToWords;
import com.lowagie.text.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class GeneratePdf{
    @Value("${companyName}")
    private String companyName;
    @Value("${address}")
    private String address;
    @Value("${gstin}")
    private String gstin;
    @Value("${state}")
    private String state;
    @Value("${mobileNumber}")
    private String mobileNumber;
    @Value("${email}")
    private String email;
    @Value("${bankName}")
    private String bankName;
    @Value("${bankAccountNumber}")
    private String bankAccountNumber;
    @Value("${bankIfscCode}")
    private String bankIfscCode;
    @Value("${paidBillLocation}")
    private String paidBillLocation;
    @Value("${unpaidBillLocation}")
    private String unpaidBillLocation;
    @Value("${previousYear}")
    private String previousYear;
    @Value("${currentYear}")
    private String currentYear;

    Map<String, String> gstCodes = Map.ofEntries(
            Map.entry("01", "Jammu and Kashmir"),
            Map.entry("02", "Himachal Pradesh"),
            Map.entry("03", "Punjab"),
            Map.entry("04", "Chandigarh"),
            Map.entry("05", "Uttarakhand"),
            Map.entry("06", "Haryana"),
            Map.entry("07", "Delhi"),
            Map.entry("08", "Rajasthan"),
            Map.entry("09", "Uttar Pradesh"),
            Map.entry("10", "Bihar"),
            Map.entry("11", "Sikkim"),
            Map.entry("12", "Arunachal Pradesh"),
            Map.entry("13", "Nagaland"),
            Map.entry("14", "Manipur"),
            Map.entry("15", "Mizoram"),
            Map.entry("16", "Tripura"),
            Map.entry("17", "Meghalaya"),
            Map.entry("18", "Assam"),
            Map.entry("19", "West Bengal"),
            Map.entry("20", "Jharkhand"),
            Map.entry("21", "Odisha"),
            Map.entry("22", "Chhattisgarh"),
            Map.entry("23", "Madhya Pradesh"),
            Map.entry("24", "Gujarat"),
            Map.entry("26", "Dadra and Nagar Haveli and Daman and Diu (Newly Merged UT)"),
            Map.entry("27", "Maharashtra"),
            Map.entry("28", "Andhra Pradesh (Before Division)"),
            Map.entry("29", "Karnataka"),
            Map.entry("30", "Goa"),
            Map.entry("31", "Lakshadweep"),
            Map.entry("32", "Kerala"),
            Map.entry("33", "Tamil Nadu"),
            Map.entry("34", "Puducherry"),
            Map.entry("35", "Andaman and Nicobar Islands"),
            Map.entry("36", "Telangana"),
            Map.entry("37", "Andhra Pradesh (Newly added)"),
            Map.entry("38", "Ladakh (Newly added)"),
            Map.entry("97", "Other Territory"),
            Map.entry("99", "Centre Jurisdiction")
    );
    Logger logger = LoggerFactory.getLogger(GeneratePdf.class);
    public String generateBill(ApiRequest apiRequest) throws FileNotFoundException {

        //logger.info(companyName+", "+gstin+", "+state+", "+mobileNumber+", "+email+", "+bankName+", "+bankAccountNumber+", "+bankIfscCode);

        String invoiceNo=previousYear+currentYear+apiRequest.getInvoiceNo();
        String inputDate=apiRequest.getDate();
        String filename = unpaidBillLocation+apiRequest.getName()+"_"+invoiceNo+"_"+inputDate+"_invoice.html";
        try (FileWriter fileWriter = new FileWriter(filename)) {
//          Write HTML content to the file
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yy");
            LocalDate date = LocalDate.parse(inputDate, inputFormatter);
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
            String outputDate = date.format(outputFormatter);
            String customerName = apiRequest.getName().toUpperCase();
            String customerStateCode = apiRequest.getGstNo().substring(0,2);
            String customerState = customerStateCode+"-"+gstCodes.get(customerStateCode);
            fileWriter.write("<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"utf-8\" /><title>Bill</title><meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />");
            fileWriter.write("<link href=\"https://netdna.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css\" rel=\"stylesheet\"/>");
            fileWriter.write("<link href=\"https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css\" rel=\"stylesheet\"/>");
            fileWriter.write("<style type=\"text/css\">body{margin-top:20px;background:#eee;}.invoice .top-left {font-size:65px;color:#3ba0ff;}.invoice .top-right {text-align:right;padding-right:20px;}.invoice .table-row {margin-left:-15px;margin-right:-15px;margin-top:25px;}.invoice .payment-info {font-weight:500;}.invoice .table-row .table>thead {border-top:1px solid #ddd;}.invoice .table-row .table>thead>tr>th {border-bottom:none;}.invoice .table>tbody>tr>td {padding:8px 20px;}.invoice .invoice-total {margin-right:-10px;font-size:16px;}.invoice .last-row {border-bottom:1px solid #ddd;}.invoice-ribbon {width:85px;height:88px;overflow:hidden;position:absolute;top:-1px;right:14px;}.ribbon-inner {text-align:center;-webkit-transform:rotate(45deg);-moz-transform:rotate(45deg);-ms-transform:rotate(45deg);-o-transform:rotate(45deg);position:relative;padding:7px 0;left:-5px;top:11px;width:120px;background-color:#66c591;font-size:15px;color:#fff;}.ribbon-inner:before,.ribbon-inner:after {content:\"\";position:absolute;}.ribbon-inner:before {left:0;}.ribbon-inner:after {right:0;}@media(max-width:575px) {.invoice .top-left,.invoice .top-right,.invoice .payment-details {text-align:center;}.invoice .from,.invoice .to,.invoice .payment-details {float:none;width:100%;text-align:center;margin-bottom:25px;}.invoice p.lead,.invoice .from p.lead,.invoice .to p.lead,.invoice .payment-details p.lead {font-size:22px;}.invoice .btn {margin-top:10px;}}@media print {.invoice {width:900px;height:800px;}}</style>");
            fileWriter.write("</head><body>");
            fileWriter.write("<div class=\"container bootstrap snippets bootdeys\">");
            fileWriter.write("<div class=\"row\">");
            fileWriter.write("<div class=\"col-sm-12\">");
            fileWriter.write("<div class=\"panel panel-default invoice\" id=\"invoice\">");
            fileWriter.write("<div class=\"panel-body\">");
            fileWriter.write("<div class=\"row\">");
            fileWriter.write("<div class=\"col-sm-6 top-left\"><img width=\"210\" height=\"100\" src=\"../Logo.png\" alt=\"Vishudh\"/></div>");
            fileWriter.write("<div class=\"col-sm-6 top-right\"><h3 class=\"marginright\">INVOICE: "+invoiceNo+"</h3><span class=\"marginright\">"+outputDate+"</span></div></div><hr></hr>");
            fileWriter.write("<div class=\"row\">");
            fileWriter.write("<div class=\"col-xs-4 from\">");
            fileWriter.write("<p class=\"lead marginbottom\">From : "+companyName+"</p><p><b>Address : </b>"+address+"</p><p><b>State : </b>"+state+"</p><p><b>GSTIN : </b>"+gstin+"</p><p><b>Phone : </b>"+mobileNumber+"</p><p><b>Email : </b>"+email+"</p></div>");
            fileWriter.write("<div class=\"col-xs-4 to\"><p class=\"lead marginbottom\">To : "+customerName+"</p><p><b>Address : </b>"+apiRequest.getAddress().toUpperCase()+"</p><p><b>State : </b>"+customerState+"</p><p><b>GSTIN : </b>"+apiRequest.getGstNo()+"</p><p><b>Phone : </b>"+apiRequest.getMobileNumber()+"</p></div>");
            fileWriter.write("<div class=\"col-xs-4 text-right payment-details\"><p class=\"lead marginbottom payment-info\">Pay To</p><p><b>Bank Name : </b>"+bankName+"</p><p><b>Bank Account No. : </b>"+bankAccountNumber+"</p><p><b>Bank IFSC Code : </b>"+bankIfscCode+"</p></div></div>");
            fileWriter.write("<div class=\"row table-row\"><table class=\"table table-striped\"><thead><tr><th class=\"text-center\" style=\"width:5%\">#</th><th class=\"text-center\" style=\"width:15%\">Item</th><th class=\"text-center\" style=\"width:15%\">HSN</th><th class=\"text-center\" style=\"width:15%\">Quantity</th><th class=\"text-center\" style=\"width:15%\">Unit Price</th><th class=\"text-center\" style=\"width:15%\">GST</th><th class=\"text-center\" style=\"width:15%\">Amount</th></tr></thead><tbody>");

            TreeMap<Float, Float> gstMap = new TreeMap<>();
            float total = 0.0f;
            float subTotal = 0.0f;
            int i=1;
            List<Item> items = apiRequest.getItems();
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            for(Item item : items) {
                String name = item.getName();
                String hsnCode = item.getHsnCode();
                float quantity = item.getQuantity();
                float price = item.getPrice();
                float gstPercentage = item.getGstPercentage();
                float totalPrice = quantity * price;
                float actualPrice = (totalPrice * 100.0f) / (100.0f + gstPercentage);
                float gst = totalPrice - actualPrice;
                total += totalPrice;
                subTotal += actualPrice;
                //Logger.info(item.getName()+", "+price+", "+totalPrice+", "+actualPrice+", "+gst);

                if (gstMap.containsKey(gstPercentage)) {
                    gstMap.put(gstPercentage, gstMap.get(gstPercentage) + gst);
                } else {
                    gstMap.put(gstPercentage, gst);
                }
                float roundedGst = Float.parseFloat(decimalFormat.format(gst));
                fileWriter.write("<tr><td class=\"text-center\">"+i+"</td>");
                fileWriter.write("<td class=\"text-center\">"+name+"</td>");
                fileWriter.write("<td class=\"text-center\">"+hsnCode+"</td>");
                fileWriter.write("<td class=\"text-center\">"+quantity+" kg</td>");
                fileWriter.write("<td class=\"text-center\">₹"+price+"</td>");
                if(gstPercentage==0.0f){
                    fileWriter.write("<td class=\"text-center\">₹0.00 (Exmp.)</td>");
                }
                else{
                    fileWriter.write("<td class=\"text-center\">₹"+roundedGst+" ("+gstPercentage+"%)</td>");
                }
                fileWriter.write("<td class=\"text-center\">₹"+totalPrice+"</td></tr>");
                i++;
            }
            float roundedSubtotal = Float.parseFloat(decimalFormat.format(subTotal));
            float roundedTotal = Float.parseFloat(decimalFormat.format(total));
            String amountInWords = NumberToWords.convertNumberToWords(String.valueOf(roundedTotal));
            fileWriter.write("</tbody></table></div>");
            fileWriter.write("<div class=\"row\"><div class=\"col-xs-8 margintop\"><p class=\"lead marginbottom text-center\">Invoice Amount In Words : "+amountInWords+"</p><p class=\"lead marginbottom text-center\">THANK YOU FOR YOUR BUSINESS!</p></div>");
            fileWriter.write("<div class=\"col-xs-4 text-right pull-right invoice-total\">");
            fileWriter.write("<p><b>Subtotal :</b> ₹"+roundedSubtotal+"</p>");

            for (Map. Entry<Float, Float> entry: gstMap.entrySet()){
                //Logger.info(entry.getKey()+", "+entry.getValue());
                float key = entry.getKey();
                float value = entry.getValue();
                if(key==0.0f) {
                    fileWriter.write("<p><b>Exmp.@0.0% :</b> ₹0.00 </p>");
                }
                else if(gstin.substring(0,2).equals(customerStateCode)) {
                    key = key / 2;
                    value = Float.parseFloat(decimalFormat.format(value / 2));
                    fileWriter.write("<p><b>SGST @" + key + "% :</b> ₹" + value + "</p>");
                    fileWriter.write("<p><b>CGST @" + key + "% :</b> ₹" + value + "</p>");
                }
                else{
                    value = Float.parseFloat(decimalFormat.format(value));
                    fileWriter.write("<p><b>IGST @"+key+"% :</b> ₹"+value+"</p>");
                }
            }

            fileWriter.write("<p><b>Total :</b> ₹"+roundedTotal+"</p>");
            fileWriter.write("</div></div></div></div></div></div></div>");
            fileWriter.write("<script type=\"text/javascript\">");
            fileWriter.write("function printPage(){");
            fileWriter.write("window.open('', '_blank');");
            fileWriter.write("window.print();");
            fileWriter.write("}");
            fileWriter.write("printPage()");
            fileWriter.write("</script></body></html>");
            //convertHtmlToPdf(filename, filename.replaceAll(".html",".pdf"));
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception based on your application's needs
        }
        return "pdf generated";
    }

//    public void convertHtmlToPdf(String htmlFilePath, String pdfFilePath) {
//        try (FileOutputStream outputStream = new FileOutputStream(pdfFilePath)) {
//            ITextRenderer renderer = new ITextRenderer();
//            renderer.setDocument(htmlFilePath);
//            renderer.layout();
//            renderer.createPDF(outputStream);
//        } catch (IOException e) {
//            e.printStackTrace(); // Handle the exception based on your application's needs
//        } catch (DocumentException e) {
//            throw new RuntimeException(e);
//        }
//    }
}






