package br.com.sapucaia.print;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import br.com.sapucaia.model.Pedido;
import br.com.sapucaia.model.Produto;
import br.com.sapucaia.repository.PedidoRepository;

@RestController
@RequestMapping("/api/print")
@CrossOrigin("*")
public class PedidoPrintController {

	@Autowired
	private PedidoRepository pedidoRepository;

	@PostMapping("/pedido")
	public ResponseEntity<?> printPedido(@RequestParam("pedido_id") Long id) {

		Optional<Pedido> pedido = pedidoRepository.findById(id);

		if (pedido.isPresent()) {
			PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
			PrintService selectedService = null;
			PrinterJob job = null;
			for (PrintService service : services) {
				if (service.getName().contains("EPSON L3150 Series")) {
					selectedService = service;
					break;
				}
			}

			if (selectedService == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Serviço de impressora não encontrado.");
			}

			try {
				job = PrinterJob.getPrinterJob();
				job.setPrintService(selectedService);
			} catch (PrinterException e) {
				e.printStackTrace();
			}

			Pedido pedido_ = pedido.get();
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			Document document = new Document();
			PdfWriter writer = null;

			try {
				writer = PdfWriter.getInstance(document, outputStream);
				document.open();
				document.add(new Paragraph("Nome: " + pedido_.getUsuario().getName()));
				document.add(new Paragraph("--------------------------------------------------------"));

				double subTotal[] = { 0 };

				pedido_.getProdutos().forEach(qProduto -> {
					Produto produto = qProduto.getProduto();

					try {
						document.add(new Paragraph("Nome do Produto: " + produto.getNomeDoProduto()));
						document.add(new Paragraph("id: " + produto.getId()));
						document.add(new Paragraph("Quantidade: " + qProduto.getQuantidade() + "x"));
						document.add(new Paragraph("--------------------------------------------------------"));

						subTotal[0] = subTotal[0] + produto.getPreco() * qProduto.getQuantidade();

					} catch (DocumentException e) {
						e.printStackTrace();
					}
				});

				document.add(new Paragraph("Sub-Total:  " + subTotal[0]));

			} catch (DocumentException e) {
				e.printStackTrace();
			}

			document.close();
			writer.close();

			try {
				PdfReader reader = new PdfReader(outputStream.toByteArray());

				int numPages = reader.getNumberOfPages();

				String pageContent_ = PdfTextExtractor.getTextFromPage(reader, 1);
				System.out.println(pageContent_);

				for (int pageIndex = 1; pageIndex <= numPages; pageIndex++) {

					String pageContent = PdfTextExtractor.getTextFromPage(reader, pageIndex);

					Printable print = new Printable() {

						@Override
						public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
								throws PrinterException {

							System.out.println(pageIndex);

							if (pageIndex > 0) {
								return NO_SUCH_PAGE;
							}

							pageFormat.setPaper(new Paper());

							pageFormat.getPaper().setImageableArea(10, 10, 10, 10);

							Graphics2D g2d = (Graphics2D) graphics;
							//g2d.drawString(pageContent, 0, 200);

							String text = pageContent;
							Font font = new Font("Arial", Font.PLAIN, 12);
							FontMetrics metrics = g2d.getFontMetrics(font);
							int x = 100; // posição horizontal do texto
							int y = 200; // posição vertical inicial do texto
							int lineHeight = metrics.getHeight(); // altura de uma linha de texto
							String[] lines = text.split("\n");

							for (String line : lines) {
							    g2d.drawString(line, x, y);
							    y += lineHeight;
							}
							
							return PAGE_EXISTS;
						}
					};

					job.setPrintable(print);
					
					
				}
				try {
						job.print();
					} catch (PrinterException e) {
						e.printStackTrace();
					}

			} catch (IOException e) {
				e.printStackTrace();
			}

			return ResponseEntity.ok().build();
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido não encontrado.");
	}
}
