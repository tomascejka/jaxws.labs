package simplest.soap.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import jakarta.xml.soap.*;

// http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso?WSDL
public class SimplestSoapClient {

	// SAAJ - SOAP Client Testing,
	// http://java.boot.by/ocewsd6-guide/ch10s03.html#c10s3a
	public static void main(String args[]) {
		String soapEndpointUrl = "https://www.w3schools.com/xml/tempconvert.asmx"; // CHANGE ME
		String soapAction = "https://www.w3schools.com/xml/CelsiusToFahrenheit"; // CHANGE ME

		callSoapWebService(soapEndpointUrl, soapAction);
	}

//*
	// https://www.tutorialspoint.com/soap/soap_message_structure.htm
	private static void createSoapEnvelope(SOAPMessage soapMessage) throws SOAPException {
		SOAPPart soapPart = soapMessage.getSOAPPart();

		String myNamespace = "tem";
		String myNamespaceURI = "http://tempuri.org/";

		// SOAP Envelope
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);

		// SOAP Body
		SOAPBody soapBody = envelope.getBody();
		SOAPElement soapBodyElem = soapBody.addChildElement("pay", myNamespace);

		SOAPElement merchantId = soapBodyElem.addChildElement("merchantId", myNamespace);
		merchantId.addTextNode("7507231");

		SOAPElement branch = soapBodyElem.addChildElement("branch", myNamespace);
		branch.addTextNode("Licensed Branch Name");

		SOAPElement alias = soapBodyElem.addChildElement("alias", myNamespace);
		alias.addTextNode("Service alias Name");

		SOAPElement paymentId = soapBodyElem.addChildElement("paymentId", myNamespace);
		paymentId.addTextNode("merchants payment idetificator");

		SOAPElement data = soapBodyElem.addChildElement("data", myNamespace);
		SOAPElement dataParam = data.addChildElement("param", myNamespace);
		SOAPElement dataParamKey = dataParam.addChildElement("key", myNamespace);
		dataParamKey.addTextNode("account");
		SOAPElement dataParamValue = dataParam.addChildElement("value", myNamespace);
		dataParamValue.addTextNode("account cridentials");

		SOAPElement hash = soapBodyElem.addChildElement("hash", myNamespace);
		hash.addTextNode("?");
	}

	private static void callSoapWebService(String soapEndpointUrl, String soapAction) {
		try {
			// Create SOAP Connection
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection soapConnection = soapConnectionFactory.createConnection();

			// Send SOAP Message to SOAP Server
			SOAPMessage soapRequest = createSOAPRequest(soapAction);
			SOAPMessage soapResponse = soapConnection.call(soapRequest, soapEndpointUrl);

			// Print the SOAP Response
			System.out.println("Response SOAP Message:");
			//soapResponse.writeTo(System.out);
			System.out.println(formatXml(soapResponse, null));
			System.out.println();

			soapConnection.close();
		} catch (Exception e) {
			System.err.println(
					"\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
			e.printStackTrace();
		}
	}

	private static SOAPMessage createSOAPRequest(String soapAction) throws Exception {
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();

		createSoapEnvelope(soapMessage);

		MimeHeaders headers = soapMessage.getMimeHeaders();
		headers.addHeader("SOAPAction", soapAction);

		soapMessage.saveChanges();

		System.out.println("Request SOAP Message:");
		//soapMessage.writeTo(System.out);
		System.out.println(formatXml(soapMessage, null));
		System.out.println("\n");

		return soapMessage;
	}

	private static String formatXml(SOAPMessage soapMessage, String indent) throws SOAPException, IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		soapMessage.writeTo(out);	
		
		Source xmlInput = new StreamSource(new StringReader(new String(out.toByteArray())));
		StringWriter stringWriter = new StringWriter();
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "yes");
			transformer.setOutputProperty("{https://xml.apache.org/xslt}indent-amount", indent==null? "2" : indent);
			transformer.transform(xmlInput, new StreamResult(stringWriter));

			return stringWriter.toString().trim();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	// */

}
