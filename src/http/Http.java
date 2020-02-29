package http;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class Http {

	private String url;
	private String date;
	private CloseableHttpClient closeableHttpClient ;
	
	public Http(String url) {
		
		this.url = url;	
		closeableHttpClient = HttpClients.createDefault();
	}

	public String doget() {
		HttpGet httpGet = new HttpGet(url);

		httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:68.0) Gecko/20100101 Firefox/68.0");
		httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
		httpGet.setHeader("Accept-Encoding", "gzip, deflate, br");
		httpGet.setHeader("Connection", "keep-alive");
		httpGet.setHeader("Cache-Control", "max-age=0");
		
		try {
			CloseableHttpResponse closeableHttpRespose =closeableHttpClient.execute(httpGet);
			HttpEntity httpEntity = closeableHttpRespose.getEntity();
            Header ceheader = httpEntity.getContentEncoding();
            if (ceheader != null) {
                HeaderElement[] codecs = ceheader.getElements();
                for (int i = 0; i < codecs.length; i++) {
                    if (codecs[i].getName().equalsIgnoreCase("gzip")) {
                    	closeableHttpRespose.setEntity(
                                new GzipDecompressingEntity(closeableHttpRespose.getEntity())); 
                    }
                }
            }
            httpEntity = closeableHttpRespose.getEntity();
			date = EntityUtils.toString(httpEntity);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				closeableHttpClient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return date;
	}


	public void setUrl(String url) {
		this.url = url;
	}

}
