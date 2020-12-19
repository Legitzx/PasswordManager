# Password Manager

The Password Manager securely stores accounts in the cloud and takes advantage of various encryption and hashing algorithms. It interfaces with the [Password Manager Api](https://github.com/Legitzx/PasswordManagerApi) which handles all CRUD operations. All accounts are stored in a remote MongoDB Database with AES-256 bit encryption and salted bcrypt hashes.

## Functionality

***Register***
 - Description: Allows a user to register with a selected email and password. The email and password are then hashed multiple times until creating a unique identifier. This unique identifier is then sent to the api to create an account. 
 
***Login*** 
 - Description: Takes in a users email and password, the program then generates their unique identifier. The Password Manager then interacts with the api and authenticates the account using Json Web Tokens (JWT).
 
***Get Vault*** 
 - Description: Makes call to api with the authorization token in the header, returns encrypted users vault. The program then derives the vault key from the users email:pass.
 
***Update Vault***
 - Description: Allows a user to update their vault. Encrypts and hashes before sending to api. Requires authentication token. 
 
## Technologies Used
 - MongoDB
 - Json Web Tokens (JWT)
 - Bcrypt Hashing Function
 - Advanced Encryption Standard (AES) 256 bit [Mode: GCM]
 - Java
 - API: Golang
 
## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE) file for details
