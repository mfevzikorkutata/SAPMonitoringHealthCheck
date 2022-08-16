/* Author: Fevzi Korkutata, Mustafa Sensoy, Alper Bulut */
package com.volthread.devops.module.sap.monitor.health.SAPHealthCheck;

public class SAPHealthCheck {
    private static CustomDestinationProviderMap provider;         
    private static final String DEST_METADATA_ZRT = "METADATA_ZRT";
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws IOException {
        long st = System.currentTimeMillis();
        int UP_DOWN;

        provider = new CustomDestinationProviderMap();
        Environment.registerDestinationDataProvider(provider);
        String filePath = args[0];
        Properties pros;
        pros = new Properties();
        FileInputStream ip = new FileInputStream(filePath);
        pros.load(ip);

        String jco_ahost;
        String jco_sysnr;
        String jco_client;
        String jco_user;
        String jco_passwd;
        String jco_lang;

        jco_ahost = pros.getProperty("jco_ahost");
        jco_sysnr = pros.getProperty("jco_sysnr");
        jco_client = pros.getProperty("jco_client");
        jco_user = pros.getProperty("jco_user");
        jco_passwd = pros.getProperty("jco_passwd");
        jco_lang = pros.getProperty("jco_lang");

        Properties connectProperties = new Properties();
        connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, jco_ahost);
        connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR, jco_sysnr);
        connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, jco_client);
        connectProperties.setProperty(DestinationDataProvider.JCO_USER, jco_user);
        connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, jco_passwd);
        connectProperties.setProperty(DestinationDataProvider.JCO_LANG, jco_lang);

        provider.addDestination(DEST_METADATA_ZRT, connectProperties);

        try {
            JCoDestination destination = JCoDestinationManager.getDestination(DEST_METADATA_ZRT);
            JCoFunction function = destination.getRepository().getFunction("SAMPLE_READ_DOC");
            JCoParameterList ipl = function.getImportParameterList();

            ipl.setValue("IV_SAP_OBJECT", "SAMPLE_SAP_OBJECT");
            ipl.setValue("IV_AR_OBJECT", "SAMPLE_AR_OBJECT");

            ipl.setValue("IV_ARCHIV_ID", "SAMPLE_ARCHIV_ID");
            ipl.setValue("IV_ARC_DOC_ID", "SAMPLE_ARC_DOC_ID");
            ipl.setValue("IV_OBJECT_ID", SAMPLE_OBJECT_ID);

            function.execute(destination);
            UP_DOWN = 1;
            System.out.println("UP_DOWN=" + UP_DOWN);  // "--> connection status: " +
            System.out.println("SAP_Response_Time=" + (System.currentTimeMillis() - st));
        } catch (Exception e) {  

            UP_DOWN = 0;

            System.out.println("UP_DOWN=" + UP_DOWN); // "--> connection status: " +

            System.out.println("SAP_Response_Time=" + (System.currentTimeMillis() - st));
            // System.err.println(e.getMessage());    

//            e.printStackTrace();

        }
    }
}
