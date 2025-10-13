# Sprint-0
Resumidamente en este proyecto deberemos enviar un valor desde la placa mediante un beacon, recibirlo en nuestra aplicación de telefono creada en android studio, finalmente que desde esta se envie ese valor a una pagina web donde se pueda ver claramente.

Encontramos el proyecto dividido en 3 carpetas:
src: todos los códigos propiamente.
doc: diseños de todos los códigos del pryecto.
test: carpeta con el test automático de la api.

Dentro de la carpeta src encontramos otras 4 carpetas, una para el código de arduino, otra para el código de android studio, una para el front end y por último otra para el backend.
Carpeta Arduino: deberemos acceder al archivo HolaMundoIBeacon.ino, conectar la placa y cargar el código, una vez cargado, debermos abrir el serial para ver que se están enviando datos mediante IBeacon.
Carpeta Telefono: deberemos descargar la apk que se encuentra en la carpeta Android y aceptar los permisos.
Carpeta Servidor y Cliente: Acceder a la página web, en este enlace: https://amarare.upv.edu.es/index.html , una vez todo lo demás funcione podremos acceder a ella y pulsar el botón "cargar ultima" para obtener la ultima medición enviada.

Al usar plesk todo el codigo de servidor y cliente debe estar dentro de la carpeta httpdocs, además de instalar la extensión de node.js, seleccionar servidorREST.js como Application Startup File y por ultimo rellenar el package.json para que todo funcione correctamente.
Para probar el test dentro de plesk deberemos ir a: Websites and Domains -> dashboard ->dev tools-> seleccionar Node.js ->Run Node.js commands -> y seleccionar en la barra buscadora: run test - node testApi.js (esta opción no aparecerá si no se ha añadido al package.json).
