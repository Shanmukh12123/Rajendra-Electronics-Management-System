package com.rajendraelectronics.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.rajendraelectronics.entity.Sale;
import com.rajendraelectronics.entity.SaleItem;
import com.rajendraelectronics.repository.SaleRepository;

import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfGState;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.Locale;


@Service
public class InvoicePdfServiceImpl implements InvoicePdfService {
	
	private final SaleRepository saleRepository;
	
	public InvoicePdfServiceImpl(SaleRepository saleRepository) {
		super();
		this.saleRepository = saleRepository;
	}

	@Override
	public ByteArrayInputStream generateInvoice(Long saleId) {

	    Sale sale = saleRepository.findById(saleId)
	            .orElseThrow(() ->
	                    new RuntimeException("Sale not found"));

	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    NumberFormat currencyFormatter =
	            NumberFormat.getCurrencyInstance(new Locale("en", "IN"));

	    try {

	        Document document = new Document();

	        PdfWriter writer = PdfWriter.getInstance(document, out);
	        
	        document.open();
	        
	        addWatermark(writer);
	        addHeader(document, sale);
	        addCustomerSection(document, sale);
	        addProductTable(document, sale, currencyFormatter);
	        addBottomSection(document, sale, currencyFormatter);
	        addFooter(document);
	        
	        document.close();

	    } catch (Exception e) {
	        throw new RuntimeException("Error generating PDF", e);
	    }

	    return new ByteArrayInputStream(out.toByteArray());
	}

	private void addWatermark(PdfWriter writer) throws Exception {
		PdfContentByte canvas = writer.getDirectContentUnder();

		PdfGState state = new PdfGState();
		state.setFillOpacity(0.06f);

		canvas.saveState();
		canvas.setGState(state);

		InputStream watermarkStream = getClass()
		        .getResourceAsStream("/static/images/logo.png");

		Image watermark = Image.getInstance(watermarkStream.readAllBytes());

		watermark.scaleToFit(240, 240);
		watermark.setAbsolutePosition(145, 340);

		canvas.addImage(watermark);

		canvas.restoreState();
	}

	private void addHeader(Document document, Sale sale) throws Exception {
		Font shopNameFont = new Font(Font.HELVETICA, 18, Font.BOLD);
		Font normalFont = new Font(Font.HELVETICA, 11);
		Font invoiceTitleFont = new Font(Font.HELVETICA, 14, Font.BOLD);

		PdfPTable headerTable = new PdfPTable(3);
		headerTable.setWidthPercentage(100);
		headerTable.setWidths(new float[]{1.3f, 3.5f, 2.7f});

		PdfPCell logoCell = new PdfPCell();

		InputStream logoStream = getClass()
		        .getResourceAsStream("/static/images/logo.png");

		Image logo = Image.getInstance(logoStream.readAllBytes());

		logo.scaleToFit(55, 55);

		logoCell.addElement(logo);

		logoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		logoCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		logoCell.setBorder(PdfPCell.BOTTOM);
		logoCell.setPaddingBottom(10);

		headerTable.addCell(logoCell);

		PdfPCell shopCell = new PdfPCell();

		shopCell.setBorder(PdfPCell.BOTTOM);
		shopCell.setPaddingBottom(10);

		Paragraph shopName = new Paragraph(
		        "RAJENDRA ELECTRONICS",
		        shopNameFont);

		shopCell.addElement(shopName);

		shopCell.addElement(new Paragraph(
		        "All Types of Electronics Goods",
		        normalFont));

		shopCell.addElement(new Paragraph(
		        "Sihora, Maharashtra",
		        normalFont));

		shopCell.addElement(new Paragraph(
		        "Mobile: 9371173209",
		        normalFont));

		headerTable.addCell(shopCell);

		PdfPCell invoiceCell = new PdfPCell();

		invoiceCell.setBorder(PdfPCell.BOTTOM);
		invoiceCell.setPaddingBottom(10);
		invoiceCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		invoiceCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

		Paragraph title = new Paragraph(
		        "TAX INVOICE",
		        invoiceTitleFont);
		title.setAlignment(Element.ALIGN_RIGHT);

		Paragraph invoiceNo = new Paragraph(
		        "Invoice No: " + sale.getInvoiceNumber(),
		        normalFont);
		invoiceNo.setAlignment(Element.ALIGN_RIGHT);

		Paragraph date = new Paragraph(
		        "Date: " + sale.getSaleDate(),
		        normalFont);
		date.setAlignment(Element.ALIGN_RIGHT);

		invoiceCell.addElement(title);
		invoiceCell.addElement(invoiceNo);
		invoiceCell.addElement(date);

		headerTable.addCell(invoiceCell);

		document.add(headerTable);
		
		document.add(new Paragraph(" "));
	}

	private void addCustomerSection(Document document, Sale sale) {
		Font normalFont = new Font(Font.HELVETICA, 11);
		
		PdfPTable customerTable = new PdfPTable(1);
		customerTable.setWidthPercentage(100);

		PdfPCell customerCell = new PdfPCell();
		customerCell.setPadding(15);
		customerCell.setBorder(PdfPCell.BOX);

		Font headingFont = new Font(Font.HELVETICA, 13, Font.BOLD);
		Paragraph billTo = new Paragraph("BILL TO", headingFont);
		customerCell.addElement(billTo);

		customerCell.addElement(new Paragraph(
		        "Customer Name : " + sale.getCustomer().getCustomerName(),
		        normalFont));

		customerCell.addElement(new Paragraph(
		        "Mobile : " + sale.getCustomer().getMobileNumber(),
		        normalFont));
		
		customerCell.addElement(new Paragraph(
		        "Address : " + sale.getCustomer().getAddress(),
		        normalFont));

		customerTable.addCell(customerCell);

		document.add(customerTable);

		document.add(new Paragraph(" "));
	}

	private void addProductTable(Document document, Sale sale, NumberFormat currencyFormatter) {
		Font normalFont = new Font(Font.HELVETICA, 11);
		
		PdfPTable productTable = new PdfPTable(4);
		productTable.setWidthPercentage(100);
		productTable.setSpacingBefore(10f);

		productTable.setWidths(new float[]{4f, 1f, 2f, 2f});
		
		productTable.addCell(createHeaderCell("Product"));
		productTable.addCell(createHeaderCell("Qty"));
		productTable.addCell(createHeaderCell("Unit Price"));
		productTable.addCell(createHeaderCell("Total"));
		
		for (SaleItem item : sale.getSaleItems()) {

			Paragraph productName = new Paragraph(item.getProductName(), normalFont);
			productName.setAlignment(Element.ALIGN_CENTER);

			PdfPCell productNameCell = new PdfPCell(productName);
			productNameCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			productNameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			productNameCell.setPadding(8);

			productTable.addCell(productNameCell);

			Paragraph qty = new Paragraph(
			        String.valueOf(item.getQuantity()),
			        normalFont);

			qty.setAlignment(Element.ALIGN_CENTER);

			PdfPCell qtyCell = new PdfPCell(qty);
			qtyCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			qtyCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			qtyCell.setPadding(8);

			productTable.addCell(qtyCell);

			Paragraph unitPrice = new Paragraph(
			        currencyFormatter.format(item.getUnitPrice()),
			        normalFont);

			unitPrice.setAlignment(Element.ALIGN_RIGHT);

			PdfPCell unitPriceCell = new PdfPCell(unitPrice);
			unitPriceCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			unitPriceCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			unitPriceCell.setPadding(8);

			productTable.addCell(unitPriceCell);

			Paragraph totalPrice = new Paragraph(
			        currencyFormatter.format(item.getTotalPrice()),
			        normalFont);

			totalPrice.setAlignment(Element.ALIGN_RIGHT);

			PdfPCell totalPriceCell = new PdfPCell(totalPrice);
			totalPriceCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			totalPriceCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			totalPriceCell.setPadding(8);

			productTable.addCell(totalPriceCell);
		}
		
		document.add(productTable);
		document.add(new Paragraph(" "));
	}

	private void addBottomSection(Document document, Sale sale, NumberFormat currencyFormatter) {
		Font normalFont = new Font(Font.HELVETICA, 11);
		
		PdfPTable bottomTable = new PdfPTable(2);
		bottomTable.setWidthPercentage(100);
		bottomTable.setSpacingBefore(15f);
		bottomTable.setWidths(new float[]{3f, 2f});
		
		PdfPCell notesCell = new PdfPCell();

		notesCell.setPadding(15);

		notesCell.setBorder(PdfPCell.BOX);

		Font notesHeadingFont = new Font(Font.HELVETICA, 12, Font.BOLD);

		notesCell.addElement(
		        new Paragraph("NOTES", notesHeadingFont));

		notesCell.addElement(
		        new Paragraph(""));

		notesCell.addElement(
		        new Paragraph("Thank you for shopping with Rajendra Electronics.", normalFont));

		notesCell.addElement(
		        new Paragraph(" ", normalFont));

		notesCell.addElement(
		        new Paragraph("• Goods once sold will not be taken back.", normalFont));

		notesCell.addElement(
		        new Paragraph("• Please keep this invoice for warranty purposes.", normalFont));

		bottomTable.addCell(notesCell);

		PdfPTable summaryTable = new PdfPTable(2);
		summaryTable.setWidthPercentage(100);

		summaryTable.setWidths(new float[]{2f, 2f});
		
		addSummaryRow(
		        summaryTable,
		        "Total Amount",
		        currencyFormatter.format(sale.getTotalAmount()),
		        new java.awt.Color(135, 206, 235),
		        java.awt.Color.BLACK);

		addSummaryRow(
		        summaryTable,
		        "Paid Amount",
		        currencyFormatter.format(sale.getPaidAmount()),
		        java.awt.Color.WHITE,
		        java.awt.Color.BLACK);

		addSummaryRow(
		        summaryTable,
		        "Remaining Amount",
		        currencyFormatter.format(sale.getRemainingAmount()),
		        java.awt.Color.WHITE,
		        java.awt.Color.RED);
		
		PdfPCell summaryCell = new PdfPCell();

		summaryCell.setPadding(5);

		summaryCell.addElement(summaryTable);

		bottomTable.addCell(summaryCell);
		
		document.add(bottomTable);
		
		document.add(new Paragraph(" "));
	}

	private void addFooter(Document document) {
		Font normalFont = new Font(Font.HELVETICA, 11);
		
		Paragraph thanks = new Paragraph(
		        "Thank You For Shopping With Us!",
		        new Font(Font.HELVETICA, 13, Font.BOLD));

		thanks.setAlignment(Element.ALIGN_CENTER);

		document.add(thanks);

		Paragraph note = new Paragraph(
		        "Come back soon.",
		        normalFont);

		note.setAlignment(Element.ALIGN_CENTER);

		document.add(note);

		document.add(new Paragraph(" "));

		Paragraph sign = new Paragraph(
		        "Authorized Signature",
		        normalFont);

		sign.setAlignment(Element.ALIGN_RIGHT);

		document.add(sign);
	}

	private void addSummaryRow(PdfPTable table,
            String label,
            String value,
            java.awt.Color backgroundColor,
            java.awt.Color textColor) {

		Font labelFont = new Font(Font.HELVETICA, 12, Font.BOLD, textColor);
		Font valueFont = new Font(Font.HELVETICA, 12, Font.BOLD, textColor);
		
		PdfPCell labelCell = new PdfPCell(new Paragraph(label, labelFont));
		labelCell.setPadding(8);
		labelCell.setBackgroundColor(backgroundColor);
		
		PdfPCell valueCell = new PdfPCell(new Paragraph(value, valueFont));
		valueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		valueCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		valueCell.setPadding(8);
		valueCell.setPaddingRight(8);
		valueCell.setBackgroundColor(backgroundColor);
		
		table.addCell(labelCell);
		table.addCell(valueCell);
	}

	private PdfPCell createHeaderCell(String text) {

	    Font font = new Font(
	            Font.HELVETICA,
	            11,
	            Font.BOLD,
	            java.awt.Color.WHITE);

	    Paragraph paragraph = new Paragraph(text, font);
	    paragraph.setAlignment(Element.ALIGN_CENTER);

	    PdfPCell cell = new PdfPCell(paragraph);

	    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	    cell.setPadding(8);
	    cell.setBackgroundColor(new java.awt.Color(0,102,204));

	    return cell;
	}
}
