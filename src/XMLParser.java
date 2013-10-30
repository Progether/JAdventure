import javax.xml.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import java.io.File;

public class XMLParser {
    XMLParser(){ }
    
    public void parserDebug(){
    	try{
    		//If anybody knows a better way to parse XML
    		// please make a suggestion at 
    		// http://www.reddit.com/r/progether/comments/1pkpgz/jadventure_discussion_thread_1/
			File xmlFile = new File("xml/menus.xml");
			DocumentBuilderFactory dbFacotory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFacotory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			
			doc.getDocumentElement().normalize();
			
			System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
			
			NodeList nList = doc.getElementsByTagName("Main");
			
			System.out.println("-------------------");
			
			for (int i = 0; i < nList.getLength(); i++){
				Node nNode = nList.item(i);
				
				System.out.println("\nCurrent Element: " + nNode.getNodeName());
				if (nNode.getNodeType() == Node.ELEMENT_NODE){
					Element eElement = (Element)nNode;
					// trying to cycle through sub-elements of Main for elements with the same name
					// Need to find out how to determine how many sub-elements there are.
					// for (int m = 0; m < eElement.)
					System.out.println("Options: " + eElement.getElementsByTagName("option").item(0).getTextContent());
				}
			}
			
		} catch (Exception e){
			e.printStackTrace();
		}
		// End Debug
    }
}
