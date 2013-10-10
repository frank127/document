package com.rim.test.syslog.logfetcher;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class SysLogFetcher
{
    private static final String SYS_URL = "http://bbssyslog01.bbs.testnet.rim.net/besng/UOS/NG-Chakotay01/20131002/uos.log";

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        // TODO Auto-generated method stub
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(SYS_URL);
        CloseableHttpResponse response = null;
        try
        {
            response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            if (entity != null)
            {
                InputStream in = entity.getContent();
                BufferedReader br = null;

                FileWriter out = null;
                BufferedWriter fbw = null;
                try
                {
                    br = new BufferedReader(new InputStreamReader(in));

                    out = new FileWriter("uos.txt");
                    fbw = new BufferedWriter(out);

                    String line;
                    while ((line = br.readLine()) != null)
                    {
                        fbw.write(line + "\n");
                    }

                    fbw.flush();

                    System.out.println("Done");
                }
                finally
                {
                    in.close();
                    out.close();

                    if (fbw != null)
                    {
                        fbw.close();
                    }

                    if (br != null)
                    {
                        br.close();
                    }
                }
            }
        }
        catch (ClientProtocolException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally
        {
            if (response != null)
            {
                try
                {
                    response.close();
                }
                catch (IOException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

}
