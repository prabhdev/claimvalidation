# claimvalidation

Claims validation application contains 2 functional microservices and 1 service discovery app. All the components are written in Spring boot on Java 8. 

Submitter Validation service:

This microservice accepts a JSON request containing submitter information. Submitter ID is parsed from the request and a lookup service is called to ensure submitter is enrolled. A master data of submitter IDs is maintained in an in memory cache. 

Patient Validation service

This microservice accepts a JSON request comprising of patient information such as ID, first, last name, DOB and corelation ID. Service validates the patient ID and populates error codes for the below scenarios:

1. Patient ID is more than 5 digits
2. Patient ID is not numeric

This service also has an API to pull patient information based on patient ID. 

![Claims_Validation_Microservice_Architecture](https://user-images.githubusercontent.com/37843820/130281465-61509a1a-b9b4-4a23-890a-976db712b54c.JPG)
