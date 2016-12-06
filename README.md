Gjuppen Security Demo


Purpose
Gjuppen is a demo web application showing the importance and purpose of using two-factor authentication for website login. 


There are three different ways to log on to this application. 
•	Registration
To use this application you have to register with a username, password and a Yubikey. The registration process is completed over HTTPS which means all data is encrypted between the user and server. The server stores the data in three different database tables. 

•	Low Security
The server saves the password in plain text. The login page for Low Security is normally conducted over HTTP and the input username and password is therefore sent un-encrypted to the server. If correct, the server responds with returning the userdata again in plain text back to the client. The user can see his/her password. 

•	Medium Security
During registration the server will use MD5 hash-algorithm without SALT to store the password in the database. When a user wants to log on with Medium Security the server forces the connection to HTTPS which means it's encrypted. When logged on the server has retrieved the hashed password. Using an API from the website MD5decrypt.net we have a function to check if the password can be unhashed against a database with about 4 billion stored passwords similar to a rainbow table. 
Write something about RSA

•	High Securty 
During registration the server will use PBKDF2 for storing a secure hashed password. The function is using SHA512, a pseudo-random-generated SALT and 5000 iterations which produces a hexadecimal password with 128 characters. The High Security login version also implements two-factor authentication using the Yubico key. During the registration the Yubikey id was saved and the key's status was synchronized aginst Yubico's server. The logon process is using HTTPS and encrypts the data.

 


