1. Setup mysql on local and update details in application-dev.yaml / You can install mysql-workbench for view purpose.
2. Build application using mvn clean install
3. Deploy service on local using command mvn spring-boot:run
4. Open swagger url in browser with url

http://localhost:9091/swagger-ui.html#/

4. You can hit and check the ledger APIs workflows by hitting them from swagger url.