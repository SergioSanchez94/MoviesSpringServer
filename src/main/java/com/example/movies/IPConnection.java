package com.example.movies;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URLEncoder;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;

import com.sergiosanchez.configuration.Config;

@SuppressWarnings("deprecation")
public class IPConnection {

    private static DefaultHttpClient httpclient;

	public static void load(String source) throws Exception {

        HttpHost targetHost = new HttpHost(Config.getIPADDRESS(), Integer.parseInt(Config.getPORT()), "http");

        httpclient = new DefaultHttpClient();
        try {
            httpclient.getCredentialsProvider().setCredentials(
                    new AuthScope(targetHost.getHostName(), targetHost.getPort()),
                    new UsernamePasswordCredentials("admin", "utorrent"));

            // Create AuthCache instance
            AuthCache authCache = new BasicAuthCache();
            // Generate BASIC scheme object and add it to the local
            // auth cache
            BasicScheme basicAuth = new BasicScheme();
            authCache.put(targetHost, basicAuth);

            // Add AuthCache to the execution context
            BasicHttpContext localcontext = new BasicHttpContext();
            localcontext.setAttribute(ClientContext.AUTH_CACHE, authCache);

            HttpGet httpget = new HttpGet("http://"+Config.getIPADDRESS()+":"+Integer.parseInt(Config.getPORT())+"/gui/");
            HttpResponse response = httpclient.execute(targetHost, httpget, localcontext);
            EntityUtils.consumeQuietly(response.getEntity());
            
            httpget = new HttpGet("http://"+Config.getIPADDRESS()+":"+Integer.parseInt(Config.getPORT())+"/gui/token.html");
            response = httpclient.execute(targetHost, httpget, localcontext);
            
            HttpEntity e = response.getEntity();
            InputStream is = e.getContent();
            StringWriter sw = new StringWriter();
            IOUtils.copy(is, sw);
            sw.flush();
            sw.close();
            is.close();
            //<html><div id='token' style='display:none;'>gzB9zbMru3JJlBf2TbmwwklESgXW2hD_caJfFLvNBjmaRbLZ3kNGnSHrFlIAAAAA</div></html>
            String t = sw.toString();
            int start = "<html><div id='token' style='display:none;'>".length();
            int end = t.indexOf("</div></html>");
            String token = t.substring(start,end);
            System.out.println(token);
            EntityUtils.consumeQuietly(response.getEntity());
            
            //"http://www.torrentportal.com/download/6066218/True.Blood.S06E01.Who.Are.You.Really.XviD-MGD%5Bettv%5D.torrent"
            String add = URLEncoder.encode(source,"UTF-8");
            httpget = new HttpGet("http://"+Config.getIPADDRESS()+":"+Integer.parseInt(Config.getPORT())+"/gui/?action=add-url&s="+add+"&token="+token);
            response = httpclient.execute(targetHost, httpget, localcontext);
            
            e = response.getEntity();
            is = e.getContent();
            sw = new StringWriter();
            IOUtils.copy(is, sw);
            sw.flush();
            sw.close();
            is.close();
            System.out.println(sw.toString());

        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
        }
    }
}