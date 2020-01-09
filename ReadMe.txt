This program supports a Server/Client type network. To run as this, you will need to choose a VM to run the server
on.

Obtain the IP Address of your local machine that your server is running on and then create various external Portals (network connected)
with that IP Address to connect to your Server.

Once the Server is created, a background thread will be running awaiting a potential network connection request.

Local portals can be created by simply specifying the created router object on the same VM.

X amount of portals and UserAgents (UserAgents connected to Portals, Portals connected to Routers) can be created and connected to the
Router given enough time for synchronisation to occur before any messages are sent.

For each UserAgent, Portal and singular Router created on the network, a handle (username) must also be provided.