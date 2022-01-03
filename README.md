## Secure Chatting - Android Application

### Table of Content
1. [Dependencies](#depend)
2. [How to Run](#run)
3. [Project description](#motivation)
4. [Prove of Concept](#poc)

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
* Application Side
  * There are some modification must be done, the server IP most be change to match in two java files during android application compilation
    * com/example/myapplication/ChatActivity.java
    * com/example/myapplication/Retrofit/RetrofitClient.java

## Project description<a name="motivation"></a>

this project aimed to have a secure envirnoment which include data encryption, key exchange mechanisms, and authentication.

## Prove of Concept<a name="poc"></a>
* application - Secure Chatting:
