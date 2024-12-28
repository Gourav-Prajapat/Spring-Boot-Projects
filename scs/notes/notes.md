<!-- commented points in features may not be presented in the current project but will be implemented in the near future -->

<!-- features -->

(1) User signUp with email and password
(2) verify account using email verification link
(3) User signUp with google and github
(4) Add the contact with picture
(5) contact picture is uploaded to cloud (AWS, Cloudinary)
(6) User can view all contacts and its details
(8) Compose and send the email directly from SCM
<!-- (9) email contain text + attachment -->
(10) update, delete and search the contact
(11) pagination
(12) export the contact data to excel
(13) mark the favourite contacts
(14) see the user profile
(15) dark and light theme
<!-- (16) provide feedback -->
<!-- pdf/excel for generating reports -->

<!-- technologies -->
latest spring boot
spring framework 
spring mvc
spring data jpa [ORM]-> using database in our project
oAuth for social login-> google and github
thymleaf template engine
validation 
spring security -> for securing routes
mysql database
java email services -> for sending and receiving emails
cloudinary SDK's -> for storing images/files
javascript
tailwind css
flowbite components


<!-- tailwind input - output command -> -->
npx tailwindcss -i ./src/main/resources/static/css/input.css -o ./src/main/resources/static/css/output.css --watch  