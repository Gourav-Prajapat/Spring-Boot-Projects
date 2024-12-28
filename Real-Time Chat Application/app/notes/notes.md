Backend (Server-Side)

Spring Boot
Spring WebSocket(This enables real time communication over websockets, and it forms base for live messaging features)
(it provides an underlying protocol to keep a continuous two-way communication between a client and server)

Spring Messaging (Stomp Protocol(simple text oriented messaging protocol))  
(it manages the messaging between client and server above websocket, and it is used to handle subscription and message broadcasting (groups or chatroom))
(it sits on top of the websocket, and it gives a structure to the communication that we do or a structure to the messages that we pass to client and server)
(stomp decides how a message is organized, routed within that connection)

Thymeleaf (server side template engine which is dynamically used to render html elements)

Frontend (Client-Side)
Thymeleaf
JavaScript
SockJS (it provide websocket like communication and handles any sort of fallback options if websocket support is unavailable)
Stomp.js (is used to get the stomp protocol)
Html/Css
Bootstrap