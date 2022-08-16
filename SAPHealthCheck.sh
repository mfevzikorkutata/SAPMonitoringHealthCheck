# Author: Fevzi Korkutata

#Variables
_scriptHome=/u01/scripts/SAPMonitoring
_javaHome=/data/weblogic/products/jdk/jdk1.8.0_121/
_domainHome=/data/weblogic/config/domains/OSB-PROD-DMN

#Clear Output
echo > $_scriptHome/dev_jco_rfc.trc 

#Jar Execution Commands
$_javaHome/bin/java -cp $_domainHome/lib/sapjco3.jar:$_scriptHome/SAPHealthChecker.jar \ 
com.volthread.devops.module.sap.monitor.health.SAPHealthCheck $_scriptHome/config.properties \ 
> $_scriptHome/output.txt
