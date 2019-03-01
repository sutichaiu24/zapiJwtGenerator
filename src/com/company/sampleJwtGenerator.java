/* D SOFTWARE INCORPORATED
 * Copyright 2007-2011 D Software Incorporated
 * All Rights Reserved.
 *
 * NOTICE: D Software permits you to use, modify, and distribute this 
file
 * in accordance with the terms of the license agreement accompanying 
it.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an “AS IS? BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or 
implied.
 */
/*
 * This is a sample of what can be done by using API's with Zephyr through 
the JAVA coding language.
 * By creating the .java files, you can import them 
into your workspace and then call them in your custom program. 
 * 
 * Eclipse Java EE IDE for Web Developers.
Version: Neon Release (4.6.0)
Build id: 20160613-1800
 * Java- Java JDK 1.8.0_101
 * 
 * Author: Swapna Kumar Vemula, Product Support Engineer, D Software Inc.
 */

package com.thed.zapi.cloud.sample;

import com.thed.zephyr.cloud.rest.ZFJCloudRestClient;
import com.thed.zephyr.cloud.rest.client.JwtGenerator;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author swapna.vemula 12-Dec-2016
 */
public class sampleJwtGenerator {

    /**
     * @param args
     * @throws URISyntaxException
     * @throws IllegalStateException
     * @author Created by swapna.vemula on 12-Dec-2016.
     */
    public static void main(String[] args) throws URISyntaxException, IllegalStateException, IOException {
        // Replace Zephyr BaseUrl with the <ZAPI_CLOUD_URL> shared with ZAPI Cloud Installation

        Properties prop = new Properties();
        InputStream input = null ;


        input = new FileInputStream("config.properties");
        prop.load(input);

        String accessKey = prop.getProperty("accessKey");
        String zephyrBaseUrl = prop.getProperty("zephyrBaseUrl");
        String secretKey = prop.getProperty("secretKey");
        String accountId = prop.getProperty("accountId");
        String publicurl = prop.getProperty("publicurl");

        // zephyr accessKey , we can get from Addons >> zapi section


        // zephyr secretKey , we can get from Addons >> zapi section



        ZFJCloudRestClient client = ZFJCloudRestClient.restBuilder(zephyrBaseUrl, accessKey, secretKey, accountId).build();
        JwtGenerator jwtGenerator = client.getJwtGenerator();

        // API to which the JWT token has to be generated
        List<String> createCycleUriList = getUrls(args, zephyrBaseUrl,publicurl);

        for (String createCycleUri : createCycleUriList) {
            URI uri = new URI(createCycleUri);
            int expirationInSec = 3600;
            String jwt = jwtGenerator.generateJWT("GET", uri, expirationInSec);

            // Print the URL and JWT token to be used for making the REST call
            System.out.println("FINAL API : " + uri.toString());
            System.out.println("JWT Token : " + jwt);
        }
    }

    private static List<String> getUrls(String[] args, String zephyrBaseUrl, String publicurl) {
        List<String> list = new ArrayList<>();


        if(args == null || args.length < 2) {
            list.add(zephyrBaseUrl + publicurl + getValue(args, 0) + "&projectId=" + getValue(args, 1));
        } else {
            for(int i = 0; i < args.length; i = i + 2) {
                list.add(zephyrBaseUrl + publicurl + getValue(args, i) + "&projectId=" + getValue(args, i+1));
            }
        }

        return list;
    }

    private static String getValue(String[] args, int index) {
        if(args != null && args.length > index) {
            return args[index];
        } else {
            return "";
        }
    }

}