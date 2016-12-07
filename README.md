--- Gjuppen™ ---
Security Demo                                                                                                                                                                                           
                                                                                                                                                                                                        
                                                                                                                                                                                                        
    `:///////////:.                                                                                            :////////`.//:    `//:                                                                   
    dMMMMMMMMMMMMMm.                                                                                           dNNMMMNNN.+MMN.   yMMd                                                                   
    MMMMNhhhhhmMMMM:  :hhhh.                                                                                   ...dMM:.. +MMMy  -MMMd                                                                   
    MMMMh     +MMMM:  /MMMM-                                                                                      hMM.   +MMMM- dMMMd                                                                   
    MMMMh     +MMMM:  -ssss.                                                                                      hMM.   +MMMMd/MMMMd                                                                   
    MMMMh     .////`   ````   `````    ````    ````    ````    `````    ```      ``````````    `````    ```       hMM.   +MMhMMNMhMMd                                                                   
    MMMMh             :mmmm`  smmmh    mmmm+  .mmmmyyhddmmdo   dmmmhyhddmmmy`  .hmmmmmmmmmmy`  smmmdsyhdmmmh-     hMM.   +MM+dMMM-MMd                                                                   
    MMMMh  -:::::::`  :MMMM.  hMMMd    MMMMo  -MMMMNmmmNMMMM`  NMMMNmmmNMMMM:  +MMMMhhhhMMMM/  hMMMMNNmmMMMMs     hMM.   +MM+:MMy`MMd                                                                   
    MMMMh  dMMMMMMM:  :MMMM.  hMMMd    MMMMo  -MMMM+...sMMMM`  NMMMy.../MMMM:  +MMMm    NMMM/  hMMMN....MMMMs     hMM.   +MM+ oy.`MMd                                                                   
    MMMMh  yddmMMMM:  :MMMM.  hMMMd    MMMMo  -MMMM/   oMMMM`  NMMMy   :MMMM:  +MMMm````NMMM/  hMMMm    MMMMs     /oo`   -oo-     oo+                                                                   
    MMMMh     +MMMM:  :MMMM.  hMMMd    MMMMo  -MMMM/   oMMMM`  NMMMy   :MMMM:  +MMMMddddMMMM/  hMMMm    MMMMs                                                                                           
    MMMMh     +MMMM:  :MMMM.  hMMMd    MMMMo  -MMMM/   oMMMM`  NMMMy   :MMMM:  +MMMNyyyyyyyo`  hMMMm    MMMMs                                                                                           
    MMMMh     +MMMM:  :MMMM.  hMMMd    MMMMo  -MMMM/   oMMMM`  NMMMy   :MMMM:  +MMMm    ----`  hMMMm    MMMMs                                                                                           
    MMMMh.....oMMMM:  :MMMM.  hMMMm```.MMMMo  -MMMM+```yMMMM`  NMMMy```/MMMM:  +MMMm    NMMM/  hMMMm    MMMMs                                                                                           
    MMMMNmmmmmNMMMM-  :MMMM.  yMMMMdmmmMMMMo  -MMMMmdddMMMMm   NMMMNdddNMMMM-  +MMMNssssMMMM/  hMMMm    MMMMs                                                                                           
    smNNNNNNNNNNNmh`  :MMMM.  /mNNmmmdhNNNN+  -MMMMNNNNNmmy-   NMMMNmNNNmmh/   -dmNNNNNNNNmd.  yNNNd    NNNNo                                                                                           
     `............    :MMMM.   `...``  ....`  -MMMM+.....`     NMMMy.....`      `..........    `....    ....`                                                                                           
                    --+MMMM.                  -MMMM/           NMMMy                                                                                                                                    
                   .MMMMMMM`                  -MMMM/           NMMMy                                                                                                                                    
                   `hhhhhy/                   .hhhh:           yhhh+                                                                                                                                    
                                                                                                             
--SUMMARY--
Gjuppen™ is a demo web application showing the importance and purpose of using two-factor authentication for login 
procedures. 



--APPLICATION DETAILS--
To try out this application you need a Yubico key (Yubikey). First visit the logon page at 
https://localhost:8181/Gjuppen/secure/startpage.xhtml to register a new user. 
When that is completed you are able to use any of the three different logon methods which 
look the same way but behave very different. 

•	Registration
To use this application you have to register with a username, password and a Yubikey. 
The registration process is completed over HTTPS which means all data is encrypted between 
the user and server during the registration process. The server stores the data in three 
different database tables which is further described below. 

•	Low Security
The server saves our password in plain text. The login page for Low Security is conducted 
over HTTP (localhost:8080) and the input username and password is therefore sent un-encrypted 
to the server. If correct, the server responds with returning the userdata (username and 
password) again in plain text (and unencrypted) back to the client. The user can click a 
button to show his/her password. 

•	Medium Security
During registration the server will use a MD5 hash-algorithm to encrypt the medium security 
password before storing it in the database. When a user wants to log on with Medium Security 
the server forces the connection to HTTPS which means that the login information is encrypted 
when sent over the internet. The server will hash the entered passsword with MD5 and compare 
it to the stored password. If correct, the server returns the username and hashed password 
to display to the user. Using an API from the website MD5decrypt.net we have a function to 
check if the password can be "unhashed" against a database with about 4 billion stored passwords 
(similar to a rainbow table). This shows the difference between having a weak or strong password 
when the encryption is broken like MD5. Additionally we have the protection of HTTPS (RSA 
encryption) which means all data is encrypted when sent to/from the server. We have a simple 
RSA demo to show how this works. 

•	High Securty 
The main difference from medium security is better encryption of the password and the use of 
two-factor authentication. During registration the server will use PBKDF2 for storing a secure 
hashed password. The function is using SHA512, a pseudo-random-generated SALT and 5000 iterations 
which produces a hexadecimal password with 128 characters. Similar to medium security login, the 
server forces the connection for high security login to HTTPS to get encrypted internet traffic. 
The High Security login version also implements two-factor authentication using the Yubico key. 
During the registration the Yubikey id was saved and the key's status was synchronized aginst 
Yubico's server. A key can't be used by multiple users. When logged on the user will see some 
additional information about the logon process. 
