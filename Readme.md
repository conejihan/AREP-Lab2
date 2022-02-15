# TALLER CLIENTES Y SERVICIOS

En este taller se creo un servidor HTTP usando nanoSpark, junto a tres programas, el primero imprime los componenetes de una URL, el segundo lee los datos de una URL y los alamacena en resultados.html y el tercero es un servidor que recibe un número y este devuelve su cuadrado, junto a este se creo un cliente el cual pueda conectarse y utilizarlo.

## Prerrequisitos

Para la realización de este se utilizaron los siguientes elementos:
* Maven
* Java versión 8
* Git
* Heroku

## ¿Cómo funciona?

El programa principal utiliza HttpServer que mediante el uso de sockets, hace que el servidor corra una aplicación web. Por otro lado tenemos el NanoSpark que utiliza el metodo get para poder definir los endpoints.
Además de esto, este tiene como componentes el BackEnd y el FrontEnd, en donde el ultimo lee la información dada por el ususario y se conecta al HttpServer para hacer uso de los endpoints.

## Ejecución

Para ejecutar el proyecto se puede realizar de tres maneras:
 * Primero clonamos el repositorio por medio del comando desde el cmd y la ubicación donde queremos guardar.
 
   ```git clone https://github.com/conejihan/AREP-Lab3```

* Despues compilamos el proyecto por medio del siguiente comando.
    
    ```mvn compile```

* Despues de hacer esto ejecutamos el proyecto de la siguiente manera usando java.

     ```web: java $JAVA_OPTS -cp target/classes:target/dependency/* edu.escuelaing.arep.App```
     
* Y podriamos ingresar por medio de nuestro localhost o utilizando el link de heroku que se encuentra más adelante.



## ¿Comó extenderlo?

La mejor forma para extenderlo se tendria que primero implementar interfaces para que al hacer cambios en este no se vea muy afectado. A parte de esto se podría hacer que aparte de obtener una imagén, este devuelva cualquier tipo de archivo.


## Heroku

[![Deployed to Heroku](https://www.herokucdn.com/deploy/button.png)](https://lab2-arep.herokuapp.com/)



 