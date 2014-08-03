package com.scorpio4.vendor.camel
import com.scorpio4.fact.stream.FactStream
import org.apache.camel.CamelContext
import org.apache.camel.Exchange
import org.apache.camel.Processor
import org.apache.camel.builder.RouteBuilder
import org.apache.camel.impl.DefaultCamelContext
import org.apache.poi.ss.usermodel.Workbook
/**
 * scorpio4-oss (c) 2014
 * Module: com.scorpio4.vendor.camel
 * User  : lee
 * Date  : 1/08/2014
 * Time  : 1:58 PM
 *
 *
 */
public class ConversionTest extends GroovyTestCase {
	File samplePDF = new File("scorpio4/scorpio4tests/src/test/resources/scorpio4/files/ProjectData.xls");

	public void testFileToWorkbook() {
		def files = new File("./temp.test/vendor/camel/conversion/");
		println "Saving to: "+files.getAbsolutePath()
		files.mkdirs();

		CamelContext camel = new DefaultCamelContext();
		camel.addRoutes(new RouteBuilder() {
			@Override
			void configure() throws Exception {
				from("direct:testFileToWorkbook").process(new Processor() {
					@Override
					void process(Exchange exchange) throws Exception {
						//convert from (InputStream) into Workbook
						Workbook workbook = exchange.getIn().getBody(Workbook.class);
						assert workbook != null;
						assert workbook instanceof Workbook;
						exchange.getOut().setBody(workbook);
						// body is a Workbook
					}
				}).convertBodyTo(FactStream.class).to("file://./temp.test/vendor/camel/conversion/").end();
				// body is converted from a Workbook to a new InputStream
			}
		})
		camel.start();
		def template = camel.createProducerTemplate();
		assert template!=null;
		def result = template.requestBody("direct:testFileToWorkbook", samplePDF)
		assert result!=null;
		assert result
		assert result instanceof Workbook;
		println "WB: "+result;
	}
}
