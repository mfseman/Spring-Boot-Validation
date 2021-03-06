# Spring-Boot-Validation
This repository shows how to utilize spring boot validations along with testing them using mockMVC.

For the endpoint "/api/validation/players", this will take a single request:

Success when firstName and team are provided:

<img width="750" alt="image" src="https://user-images.githubusercontent.com/43916357/178160466-cef0e1f1-3c70-4f39-8e3d-3744d8f920ae.png">

Success when firstName and squad are provided:

<img width="750" alt="image" src="https://user-images.githubusercontent.com/43916357/178160840-a81b41ce-cabd-4063-a24e-95a64b545bf3.png">

When firstName is too long:

<img width="750" alt="image" src="https://user-images.githubusercontent.com/43916357/178160745-4be215e6-bd0a-45ff-8ebc-97dd5b86bce3.png">

When firstName invalid:

<img width="750" alt="image" src="https://user-images.githubusercontent.com/43916357/178160775-9906e69d-c106-4eef-84ac-9f24e19b205b.png">

When both squad and team are provided:

<img width="750" alt="image" src="https://user-images.githubusercontent.com/43916357/178160796-138e36ac-78cf-4246-9270-733af0505e51.png">

When squad and team are not provided:

<img width="750" alt="image" src="https://user-images.githubusercontent.com/43916357/178160812-cb37b955-dbdb-44e1-baf7-b8849c249d75.png">

Error when Method is not supported:

<img width="750" alt="image" src="https://user-images.githubusercontent.com/43916357/178161700-1bd987e4-540e-4bb3-b9e2-a16bd5e34d2c.png">

Error when Authorization header is invalid:

<img width="750" alt="image" src="https://user-images.githubusercontent.com/43916357/178161728-dc3e12f1-85fa-48c9-abab-be36000be2a3.png">

Error when Content-Type is invalid:

<img width="750" alt="image" src="https://user-images.githubusercontent.com/43916357/178161716-a25e726c-7bd0-4652-88b0-230060446a11.png">

---------------------------------------------------------------------------------------------------------------------------------------------------

For the endpoint "/api/validation/players", this will take a list of requests:

Success for single request:

<img width="750" alt="image" src="https://user-images.githubusercontent.com/43916357/178161096-fec14822-0766-4cdc-b2d6-21d454d3df5e.png">

Success for multiple requests:

<img width="750" alt="image" src="https://user-images.githubusercontent.com/43916357/178161115-1450da19-4845-4264-9248-fc24458685fc.png">

Multistatus for Success with failures:

<img width="750" alt="image" src="https://user-images.githubusercontent.com/43916357/178161195-e51ea4fb-2792-4edd-b0d1-ef8f0c887d3f.png">

When list size exceeds 10:

<img width="750" alt="image" src="https://user-images.githubusercontent.com/43916357/178161336-3901b927-ef40-45ab-bbcf-f8f47810e247.png">

---------------------------------------------------------------------------------------------------------------------------------------------------

For reference on how to test with mockMVC, please refer to:
https://github.com/mfseman/Spring-Boot-Validation/blob/main/spring-boot-validation/src/test/java/com/practice/springbootvalidation/api/ValidationControllerFeatureTest.java


For reference on how to use a Controller advice along with difference types of exceptions, please refer to:
https://github.com/mfseman/Spring-Boot-Validation/blob/main/spring-boot-validation/src/main/java/com/practice/springbootvalidation/errorhandling/ErrorHandlingControllerAdvice.java
