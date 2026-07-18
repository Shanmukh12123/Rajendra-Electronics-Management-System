package com.rajendraelectronics.service;

import java.io.ByteArrayInputStream;

public interface InvoicePdfService {
	
	ByteArrayInputStream generateInvoice(Long saleId);
}
