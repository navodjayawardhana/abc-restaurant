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
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/generatePdfReport")
public class GeneratePdfReportServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BranchService branchService;
    private ProductService productService;
    private BookingService bookingService;
    private UserService userService;  // Add UserService
    private UserViewDao userViewDao;

    @Override
    public void init() {
        branchService = new BranchService(new BranchDAO(DatabaseConnection.getConnection()));
        productService = new ProductService(new ProductDAO(DatabaseConnection.getConnection()));
        bookingService = new BookingService();
        userViewDao = new UserViewDao();
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String reportType = request.getParameter("report");

        if ("branch".equalsIgnoreCase(reportType)) {
            generateBranchReport(response);
        } else if ("product".equalsIgnoreCase(reportType)) {
            generateProductReport(response);
        } else if ("booking".equalsIgnoreCase(reportType)) {
            generateBookingReport(response);  // Generates booking report
        } else if ("user".equalsIgnoreCase(reportType)) {  // Generates user report
            generateUserReport(response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid report type.");
        }
    }

    private void generateUserReport(HttpServletResponse response) throws ServletException, IOException {
        try {
            List<User> users = userService.getAllUsers();  // Fetch all users

            // Categorize users by roles
            List<User> admins = users.stream().filter(user -> "ADMIN".equalsIgnoreCase(user.getRole())).collect(Collectors.toList());
            List<User> staff = users.stream().filter(user -> "STAFF".equalsIgnoreCase(user.getRole())).collect(Collectors.toList());
            List<User> customers = users.stream().filter(user -> "CUSTOMER".equalsIgnoreCase(user.getRole())).collect(Collectors.toList());

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=user_report.pdf");

            Document document = new Document();
            OutputStream out = response.getOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, out);
            document.open();

            addHeader(document, writer, "User Report");

            // Add user tables for each role category
            addUserTable(document, "Admins", admins);
            addUserTable(document, "Staff", staff);
            addUserTable(document, "Customers", customers);

            addFooter(document, writer);
            document.close();
            out.close();
        } catch (DocumentException e) {
            throw new ServletException("Cannot generate User PDF report", e);
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

            // Separate the bookings based on their status
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
}
