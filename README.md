# Trabajo-Fin-Grado
Trabajo de Fin de Grado: Estudio de Tendencias Diarias en Twitter

#Realizado por: 

Ángel Luis Ortiz Folgado

Óscar Eduardo Pérez la Madrid

Esteban Vargas Rastrollo,

#Resumen:

En la actualidad las redes sociales han cambiado el paradigma de las relaciones
sociales y del acceso a la información. Twitter con el paso del tiempo se
ha convertido en un medio de comunicación que está dejando poco a poco a los
medios tradicionales como los periódicos y la televisión en un segundo plano.
La ingente cantidad de información que se genera constantemente en esta plataforma
provoca que surja una necesidad de agrupar y concretar esta información
para que el usuario medio de Twitter no se vea abrumado por el exceso de
información, muchas veces irrelevante, que recibe.
Una de las funcionalidades que ofrece Twitter es la posibilidad de agrupar
los temas de los que se está hablando en la aplicación bajo un conjunto de
tendencias, llamados trending topics. Estos trending topics tienen un tiempo de
vida limitado, es decir, que una vez los usuarios dejan de hablar de ese tema en
la aplicación, éste ya no formará parte de la lista de trending topics que Twitter
ofrece a sus clientes. Esto puede llevar a que si el usuario no ha estado presente
en la aplicación durante la aparición de una tendencia que le pueda resultar
atractiva, le resultará complicado informarse sobre la misma.
El objetivo de este trabajo es el desarrollo de una aplicación que permita a los
usuarios explorar el conjunto de tendencias que han ido apareciendo a lo largo
del tiempo, permitiendo así que cada trending topic que ofrece Twitter quede
registrado en la aplicación para su posterior análisis. La aplicación desarrollada
ofrece funcionalidades de clasificación y agrupamiento de trending topics, así
como visualización gráfica de la evolución en el tiempo de las tendencias más
importantes. Además incluye un buscador de tweets populares que permita al
usuario obtener aquellos tweets que más han destacado en la comunidad de
usuarios de Twitter con respecto a cada trending topic.
Distintos aspectos de la aplicación implementada han sido evaluados. La
evaluación del clasificador de trending topics obtiene una precisión del 90 %. La
evaluación del agrupamiento de comunidades concluye que las tendencias están
bien relacionadas entre sí aunque puede ser mejorable. Por último, en cuanto al
buscador de tweets populares se concluye que los tweets devueltos son relevantes
en su mayoría (por encima del 90 %) para cada criterio usado.
Los resultados obtenidos nos permiten concluir que la aplicación es capaz de
informar al usuario de las tendencias que han surgido en Twitter a lo largo del
tiempo, así como mostrarle información necesaria para que pueda percatarse
sobre qué temas se ha hablado, cuáles han sido los mensajes más populares,
cómo se relacionan esas tendencias entre sí y cómo ha sido la evolución en el
tiempo de los trending topics más importantes.

#Estructura del proyecto:

En la carpeta principal se encuentra la memoria del trabajo realizado.
En la carpeta Ejecutables se encuentran las aplicaciones ya compiladas para la puesta en funcionamiento.
En la carpeta Ejecutables/Servidor se encuentran los tres procesos que hay que ejecutar en un servidor remoto para capturar los datos necesarios de Twitter.
En la carpeta Ejecutables/Aplicacion se encuentra el ejecutable de la aplicación principal TrendSpy, requiere la instalación de Java8.
El código fuente se puede encontrar en la carpeta Fuentes.
