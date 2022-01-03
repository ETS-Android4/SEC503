## Secure Chatting - Android Application

### Table of Content
1. [Requirements](#Req)
2. [How to Run](#run)
3. [Project description](#motivation)
4. [Prove of Concept](#poc)

## Requirements<a name="Req"></a>
There are 3 requirements that need to be satisfied before run this application:
1. Download mysql server or mariadb server in linux server.
2. Download NodeJs in linux server.
3. Download Latest version of Android Studio.

**The back-end application should run in linux server.**

## How to Run<a name="run"></a>

To launch this application, there are two parts, server-side and application-side
* Server Side
  * First Import Database Setting:
    ```
    mysql -u username -p Project << exported.sql
    ```
  * Second, Run the back-end application:
    ```
    node index.js
    ```
  * Third in new terminal, run the chat_room.js
    ```
    node chat_room.js
    ```
  * Modifiy the SMTP configuration so OTP services would run successfuly, provide it with support email account ro sending the OTP.


* Application Side
  * First Run Android Studio, and open project and select "MyApplication" folder.
  * After the application is being installed with all required dependencies. There are some modification must be done, the server IP most be change to match in two java files during android application compilation
    * com/example/myapplication/ChatActivity.java
    * com/example/myapplication/Retrofit/RetrofitClient.java
  * Run two emulators to simulate the demo.
## Project description<a name="motivation"></a>

this project aimed to have a secure envirnoment which include data encryption, key exchange mechanisms, and authentication.

## Prove of Concept<a name="poc"></a>
* Application - Secure Chatting App:
<img src="https://github.com/Abdulwahab55/SEC503/blob/main/demo.gif" width="1200" height="800" />

* Example of the encrypted Traffic
<img src="https://github.com/Abdulwahab55/SEC503/blob/main/encrypted_traffic.png"/>
