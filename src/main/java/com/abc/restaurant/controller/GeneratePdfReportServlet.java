package com.abc.restaurant.controller;

import com.abc.restaurant.dao.BranchDAO;
import com.abc.restaurant.dao.DatabaseConnection;
import com.abc.restaurant.dao.ProductDAO;
import com.abc.restaurant.dao.UserViewDao;
import com.abc.restaurant.model.*;
import com.abc.restaurant.service.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/generatePdfReport")
public class GeneratePdfReportServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BranchService branchService;
    private ProductService productService;
    private BookingService bookingService;
    private UserService userService; 
    private UserViewDao userViewDao;
    private OrderServiceview orderServiceview;

    @Override
    public void init() {
        branchService = new BranchService(new BranchDAO(DatabaseConnection.getConnection()));
        productService = new ProductService(new ProductDAO(DatabaseConnection.getConnection()));
        bookingService = new BookingService();
        userViewDao = new UserViewDao();
        orderServiceview = new OrderServiceview();
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String reportType = request.getParameter("report");
        String orderId = request.getParameter("orderId");

        if (reportType != null) {
            switch (reportType) {
                case "branch":
                    generateBranchReport(response);
                    break;
                case "product":
                    generateProductReport(response);
                    break;
                case "booking":
                    generateBookingReport(response);
                    break;
                case "order":
                    if (orderId != null) {
                        generateOrderPdf(response, Integer.parseInt(orderId)); 
                    } else {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Order ID is required.");
                    }
                    break;
                case "incomeStatement":
                    generateIncomeReport(response); 
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid report type.");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Report type is required.");
        }
    }

    

    private void addUserTable(Document document, String title, List<User> users) throws DocumentException {
        Font sectionTitleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
        Paragraph sectionTitle = new Paragraph(title, sectionTitleFont);
        sectionTitle.setAlignment(Element.ALIGN_LEFT);
        document.add(sectionTitle);
        document.add(Chunk.NEWLINE);

        if (users.isEmpty()) {
            document.add(new Paragraph("No users available."));
            document.add(Chunk.NEWLINE);
            return;
        }

        PdfPTable table = createStyledTable(new String[]{"Name", "Email", "Role", "Status"}, 4);

        for (User user : users) {
            addRowToTable(table, user.getName(), user.getEmail(), user.getRole(), user.isActive() ? "Active" : "Inactive");
        }

        document.add(table);
        document.add(Chunk.NEWLINE);
    }

    private void generateBranchReport(HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Branch> branches = branchService.getAllBranches();

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=branch_report.pdf");

            Document document = new Document();
            OutputStream out = response.getOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, out);
            document.open();

            addHeader(document, writer, "Branch Report");

            PdfPTable table = createStyledTable(new String[]{"Name", "Email", "Contact", "Address"}, 4);

            for (Branch branch : branches) {
                addRowToTable(table, branch.getName(), branch.getEmail(), branch.getContact(), branch.getAddress());
            }

            document.add(table);
            addFooter(document, writer);
            document.close();
            out.close();
        } catch (SQLException | DocumentException e) {
            throw new ServletException("Cannot generate Branch PDF report", e);
        }
    }

    private void generateProductReport(HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Product> products = productService.getAllProducts();

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=product_report.pdf");

            Document document = new Document();
            OutputStream out = response.getOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, out);
            document.open();

            addHeader(document, writer, "Product Report");

            PdfPTable table = createStyledTable(new String[]{"Name", "Description", "Price", "Available", "Category"}, 5);

            for (Product product : products) {
                addRowToTable(table, product.getName(), product.getDescription(), String.valueOf(product.getPrice()),
                        product.isAvailable() ? "Yes" : "No", product.getCategory());
            }

            document.add(table);
            addFooter(document, writer);
            document.close();
            out.close();
        } catch (SQLException | DocumentException e) {
            throw new ServletException("Cannot generate Product PDF report", e);
        }
    }

    private void generateBookingReport(HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Booking> bookings = bookingService.getAllBookings();

         
            List<Booking> approvedBookings = bookings.stream()
                    .filter(booking -> "Approved".equalsIgnoreCase(booking.getStatus()))
                    .collect(Collectors.toList());

            List<Booking> rejectedBookings = bookings.stream()
                    .filter(booking -> "Rejected".equalsIgnoreCase(booking.getStatus()))
                    .collect(Collectors.toList());

            List<Booking> pendingBookings = bookings.stream()
                    .filter(booking -> "Pending".equalsIgnoreCase(booking.getStatus()))
                    .collect(Collectors.toList());

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=booking_report.pdf");

            Document document = new Document();
            OutputStream out = response.getOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, out);
            document.open();

            addHeader(document, writer, "Booking Report");

            addBookingTable(document, "Approved Bookings", approvedBookings);
            addBookingTable(document, "Rejected Bookings", rejectedBookings);
            addBookingTable(document, "Pending Bookings", pendingBookings);

            addFooter(document, writer);
            document.close();
            out.close();
        } catch (SQLException | DocumentException e) {
            throw new ServletException("Cannot generate Booking PDF report", e);
        }
    }

    private void addBookingTable(Document document, String title, List<Booking> bookings) throws DocumentException {
        Font sectionTitleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
        Paragraph sectionTitle = new Paragraph(title, sectionTitleFont);
        sectionTitle.setAlignment(Element.ALIGN_LEFT);
        document.add(sectionTitle);
        document.add(Chunk.NEWLINE);

        if (bookings.isEmpty()) {
            document.add(new Paragraph("No bookings available."));
            document.add(Chunk.NEWLINE);
            return;
        }

        PdfPTable table = createStyledTable(new String[]{"Name", "Email", "Phone", "Persons", "Date", "Status"}, 6);

        for (Booking booking : bookings) {
            addRowToTable(table, booking.getUserName(), booking.getUserEmail(), booking.getPhoneNumber(),
                    String.valueOf(booking.getNumPersons()), booking.getBookingDate().toString(), booking.getStatus());
        }

        document.add(table);
        document.add(Chunk.NEWLINE);
    }

    private void addHeader(Document document, PdfWriter writer, String titleText) throws DocumentException {
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, BaseColor.BLACK);
        Paragraph title = new Paragraph(titleText, titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(Chunk.NEWLINE);
    }

    private PdfPTable createStyledTable(String[] headers, int numColumns) throws DocumentException {
        PdfPTable table = new PdfPTable(numColumns);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);
        BaseColor headerBackground = new BaseColor(79, 129, 189);

        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
            cell.setBackgroundColor(headerBackground);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(10f);
            table.addCell(cell);
        }

        return table;
    }

    private void addRowToTable(PdfPTable table, String... columns) {
        for (String column : columns) {
            PdfPCell cell = new PdfPCell(new Phrase(column));
            cell.setPadding(8f);
            table.addCell(cell);
        }
    }

    private void addFooter(Document document, PdfWriter writer) {
        PdfContentByte canvas = writer.getDirectContent();
        Font footerFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.GRAY);
        Phrase footer = new Phrase("Generated by Restaurant Management System", footerFont);
        ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, footer,
                (document.right() - document.left()) / 2 + document.leftMargin(),
                document.bottom() - 10, 0);
    }
    
    
 
    private void generateOrderPdf(HttpServletResponse response, int orderId) throws ServletException, IOException {
        try {
           
            Order order = orderServiceview.getOrder(orderId);
            List<OrderItem> orderItems = orderServiceview.getOrderItems(orderId);

          
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=order_" + orderId + "_report.pdf");

          
            Document document = new Document();
            OutputStream out = response.getOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, out);
            document.open();

         
            addOrderDetailsHeader(document, "Order Details Report", order);
            addOrderDetails(document, order);

          
            addOrderItemsTable(document, orderItems);

           
            addOrderTotal(document, orderItems);

            document.close();
            out.close();
        } catch (SQLException | DocumentException e) {
            throw new ServletException("Cannot generate order PDF report", e);
        }
    }

  
    private void addOrderDetailsHeader(Document document, String title, Order order) throws DocumentException {
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, BaseColor.BLACK);
        Paragraph header = new Paragraph(title, titleFont);
        header.setAlignment(Element.ALIGN_CENTER);
        document.add(header);
        document.add(Chunk.NEWLINE);
    }

   
    private void addOrderDetails(Document document, Order order) throws DocumentException {
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 12);
        document.add(new Paragraph("Order ID: " + order.getId(), font));
        document.add(new Paragraph("Customer Name: " + order.getName(), font));
        document.add(new Paragraph("Customer Email: " + order.getEmail(), font));
        document.add(new Paragraph("Address: " + order.getAddress(), font));
        document.add(new Paragraph("Order Type: " + order.getOrderType(), font));
        document.add(new Paragraph("Payment Method: " + order.getPaymentMethod(), font));
        document.add(new Paragraph("Order Status: " + order.getStatus(), font));
        document.add(new Paragraph("Order Date: " + order.getCreatedAt().toString(), font));
        document.add(Chunk.NEWLINE);
    }

   
    private void addOrderItemsTable(Document document, List<OrderItem> orderItems) throws DocumentException {
        PdfPTable table = new PdfPTable(3); 
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);

        PdfPCell cell = new PdfPCell(new Phrase("Product Name"));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Quantity"));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Price"));
        table.addCell(cell);

        for (OrderItem item : orderItems) {
            table.addCell(item.getProductName());
            table.addCell(String.valueOf(item.getQuantity()));
            table.addCell(item.getPrice().toString());
        }

        document.add(table);
    }

  
    private void addOrderTotal(Document document, List<OrderItem> orderItems) throws DocumentException {
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (OrderItem item : orderItems) {
            totalPrice = totalPrice.add(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }

        Font totalFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
        Paragraph total = new Paragraph("Total Price: " + totalPrice.toString(), totalFont);
        total.setAlignment(Element.ALIGN_RIGHT);
        document.add(total);
    }

    private void generateIncomeReport(HttpServletResponse response) throws ServletException, IOException {
        try {
           
            List<Order> orders = orderServiceview.getAllOrdersWithTotalPrice();

            
            BigDecimal totalRevenue = BigDecimal.ZERO;

          
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=income_report.pdf");

            
            Document document = new Document();
            OutputStream out = response.getOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, out);
            document.open();

           
            addHeader(document, writer, "Income Report");

           
            PdfPTable table = new PdfPTable(5);  
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);

           
            PdfPCell cell = new PdfPCell(new Phrase("Order ID"));
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Customer Name"));
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Date"));
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Quantity"));
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Total Price"));
            table.addCell(cell);

          
            for (Order order : orders) {
                BigDecimal orderTotal = order.getTotalPrice().multiply(new BigDecimal(order.getTotalQuantity())); 

                table.addCell(String.valueOf(order.getId()));
                table.addCell(order.getName());
                table.addCell(order.getCreatedAt().toString());
                table.addCell(String.valueOf(order.getTotalQuantity()));  
                table.addCell(orderTotal.toString());  

            
                totalRevenue = totalRevenue.add(orderTotal);
            }

            document.add(table);

       
            Font totalFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            Paragraph total = new Paragraph("Total Revenue: LKR" + totalRevenue.toString(), totalFont);
            total.setAlignment(Element.ALIGN_RIGHT);
            document.add(total);

            document.close();
            out.close();
        } catch (SQLException | DocumentException e) {
            throw new ServletException("Cannot generate income report PDF", e);
        }
    }




    
}
