/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch03010_photoalbum;

import java.io.*;
import java.net.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

/**
 *
 * @author tudoi
 */
public class Ch03010_PhotoAlbum {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            URL resourceUrl = new URL("https://api.flickr.com/services/rest/?method=flickr.photosets.getPhotos&api_key=f56aa49be93190f2fdd0d6e4b9f49aca&photoset_id=72157700511248451&user_id=161186359@N02&privacy_filter=1");
            //... make a single request from the URL object.
            HttpURLConnection conn = (HttpURLConnection) resourceUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                                            + conn.getResponseCode());
            }

            //...create an object of the InputStream class to handle the response.
            InputStream responseStream = conn.getInputStream();

            try {

                //...write the XML payload returned by the response stream into a document 
                //   using the DocumentBuilderFactory and DocumentBuilder classes.
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(responseStream);
                
/*
<rsp stat="ok">
    <photoset id="72157700511248451" primary="43297599950" owner="161186359@N02" ownername="tudoistube@ymail.com" page="1" per_page="500" perpage="500" pages="1" title="Natural" total="10">
        <photo id="43297599950" secret="f8dd2744a7" server="1966" farm="2" title="20170901_125412" isprimary="1" ispublic="1" isfriend="0" isfamily="0"/>
        <photo id="44390927524" secret="7bbce09afc" server="1943" farm="2" title="20170901_125425" isprimary="0" ispublic="1" isfriend="0" isfamily="0"/>
        <photo id="44390927664" secret="3903d0ac83" server="1977" farm="2" title="20170902_135158" isprimary="0" ispublic="1" isfriend="0" isfamily="0"/>
        <photo id="44390927884" secret="845d7de22d" server="1949" farm="2" title="20170902_135402" isprimary="0" ispublic="1" isfriend="0" isfamily="0"/>
        <photo id="45062030262" secret="32405e6e2e" server="1908" farm="2" title="20170902_135409" isprimary="0" ispublic="1" isfriend="0" isfamily="0"/>
        <photo id="44390928344" secret="4a963a264c" server="1917" farm="2" title="20170902_135744" isprimary="0" ispublic="1" isfriend="0" isfamily="0"/>
        <photo id="45062030472" secret="e82fe20da6" server="1919" farm="2" title="20170902_135748" isprimary="0" ispublic="1" isfriend="0" isfamily="0"/>
        <photo id="44390928824" secret="c0dec13232" server="1960" farm="2" title="20170902_135753" isprimary="0" ispublic="1" isfriend="0" isfamily="0"/>
        <photo id="43297601400" secret="d9eb2d6dac" server="1966" farm="2" title="20170903_124300" isprimary="0" ispublic="1" isfriend="0" isfamily="0"/>
        <photo id="44390927444" secret="562aed3666" server="1957" farm="2" title="20170903_124304" isprimary="0" ispublic="1" isfriend="0" isfamily="0"/>
    </photoset>
</rsp>                
*/                
                //...build a node list that starts with photo, using the NodeList class.
                NodeList nodeList = doc.getElementsByTagName("photo");

                for (int x = 0, size = nodeList.getLength(); x < size; x++) {
                    //...https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}_[mstzb].jpg
                    //...https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{o-secret}_o.(jpg|gif|png)
                    String s = "https://farm" + nodeList.item(x).getAttributes().getNamedItem("farm").getNodeValue() 
                            + ".staticflickr.com/" + nodeList.item(x).getAttributes().getNamedItem("server").getNodeValue()
                            + "/" + nodeList.item(x).getAttributes().getNamedItem("id").getNodeValue()
                            + "_" + nodeList.item(x).getAttributes().getNamedItem("secret").getNodeValue()
                            + "_b.jpg"; //...image size.

                    System.out.println(s);

                    //...download all the images from the server. 
                    URL mediaUrl = new URL(s);
                    InputStream in = new BufferedInputStream(mediaUrl.openStream());
                    //...The FileOutputStream class constructor can be used to create new files with unique filenames. 
                    //   You can also define the write operation as append with a second parameter by using 'true'.
                    //   https://docs.oracle.com/javase/8/docs/api/?java/io/FileOutputStream.html
                    OutputStream out = new BufferedOutputStream(new FileOutputStream("/home/pi/NetBeansProjects/Public/Images/Ch03010_PhotoAlbum/" + nodeList.item(x).getAttributes().getNamedItem("id").getNodeValue() + ".jpg", true));

                    //...The OutputStream class writes the file to disk using the write method, 
                    //   where the i variable holds the bytes to this output stream returned by the InputStream variable 'in'.
                    for (int i; (i = in.read()) != -1;) {
                        out.write(i);
                    }
                    
                    //...close the input and output stream objects using the close method.
                    in.close();
                    out.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            //...disconnect the HttpURLConnection object, conn, using the disconnect method.
            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
