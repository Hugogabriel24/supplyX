/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servico;

import com.sun.org.apache.xerces.internal.util.DOMUtil;
import java.awt.Desktop;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 *
 * @author hugog
 */
public class DistanciaWS {


    public static String calcular(String origem, String destino) {
        URL url;       
        try {
            url = new URL(
                    "https://maps.googleapis.com/maps/api/directions/json?origin=%22R.%20Francisco%20Berenguer,%20737%20-%20Campo%20Grande,%20Recife%20-%20PE,%2052040-070%22&destination=%22R.%20Amazonas,%20307%20-%20Jardim%20Brasil,%20Olinda%20-%20PE,%2053230-430%22&key=AIzaSyAOME7FCQDBd-yzqsZWAhWxh6-95llIJTA");
//            url = new URL(
//                    "https://maps.googleapis.com/maps/api/directions/json?origin="
//                    + origem + "&destination=" + destino
//                    + "&key=AIzaSyAOME7FCQDBd-yzqsZWAhWxh6-95llIJTA");

            Document doc = getDocumento(url);
            

            return analisaXml(doc);
        } catch (MalformedURLException | DocumentException e) {
            e.printStackTrace();
        }
        return "";
    }

    @SuppressWarnings("rawtypes")
    public static String analisaXml(Document document) {
        List list = document
                .selectNodes("");

        Element element = (Element) list.get(list.size() - 1);

        return element.getText();
    }

    public static Document getDocumento(URL url) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(url);
        return document;
    }
    
    public static void main(String[] args) {
        System.out.println(calcular("Rua Francisco Berenguer, 737, Recife - PE",
                "Rua Amazonas, 307, Olinda - PE"));
    }
}
