https://github.com/ffernandoss/ParteJavaConcurrenteParcial.git
## TODO
-[] Dividir por paquetes
-[] ver si datos web socket handler se usa o no
-[] escribir main
-[] ver si MenuController se usa o no
-

# Código

El código está compuesto por las siguientes clases:

## CustomThreadFactory
Esta clase es una fábrica de hilos que implementa `ThreadFactory`. Sus atributos son un `String` y un `AtomicInteger`. El `AtomicInteger` se utiliza para evitar condiciones de carrera entre los hilos, y el `String` se utiliza para proporcionar un nombre personalizado a cada hilo. Esta clase tiene un constructor que recibe el `String` y un método con la anotación `@Override` y de tipo `Thread` que recibe un objeto de tipo `Runnable`. Se encarga de imprimir qué hilo ha escrito cada dato de la base de datos.

## Datos
Esta clase es una entidad en la que se guardarán los datos de la tabla normal que luego aparece en la base de datos de MySQL. Utiliza las anotaciones `@Entity`, `@Data`, `@Table`, `@Id`, `@Column` y `@GeneratedValue`. El nombre de la tabla es `datos`, y tiene los atributos `ID`, que se usa para darle un valor a cada una de las líneas de la entidad, y el atributo `value` que es el valor de cada dato del CSV de la distribución normal. Esta clase también está compuesta por los getters y setters de ambos atributos, aunque en este ejercicio solo se hacen uso de los getters y setters de `value`.

## DatosController
Esta clase se encarga de manejar las solicitudes que se le harán a la entidad de datos. Utiliza las anotaciones `@RestController`, `@RequestController`, `@Autowired`, `@GetMapping`, `@RequestBody` y `@PostMapping`. Tiene un atributo de tipo `DatosService`, una lista que se usa para retornar los 1000 valores de la entidad de datos y un método de tipo `Datos` para guardar un dato que se recibirá por parámetro.

El código también está compuesto por las siguientes interfaces:

## DatosRepository
Se encarga de que se puedan realizar las operaciones de CRUD en la base de datos de la entidad `Datos`. Esta entidad extiende de `JpaRepository`.

## ExponencialRepository
Se encarga de que se puedan realizar las operaciones de CRUD en la base de datos de la entidad `Exponencial`. Esta entidad extiende de `JpaRepository`.

## DatosService
`DatosService` es una clase de servicio en la aplicación que maneja operaciones relacionadas con los datos. Sus principales funciones incluyen:
- **getAllValores()**: Obtiene todos los valores de la entidad `Datos`.
- **saveValor(Datos datos)**: Guarda un valor de la entidad `Datos`.
- **loadCSVToDatabase(String csvFile)**: Carga datos desde un archivo CSV a la base de datos.
- **printDatos()**: Imprime los datos almacenados en la base de datos.
- **shutdownExecutor()**: Apaga el `ExecutorService` utilizado para manejar tareas en hilos separados.

## DatosWebSocketHandler
(Descripción pendiente)

## DemoApplication 
`DemoApplication` es la clase principal de la aplicación Spring Boot. Sus principales funciones incluyen:
- **main(String[] args)**: Método principal que inicia la aplicación Spring Boot.

## Exponencial
Esta clase es la entidad en la que se guardarán los datos de la exponencial en MySQL. Está compuesta por las anotaciones `@Entity`, `@Data`, `@Table`, `@Id`, `@GeneratedValue`, `@Column`. En este caso, la entidad se llama `exponencial`, tiene un atributo principal que es el `Id`, el cual tiene la misma función que en la otra entidad, y en esta entidad el otro atributo en vez de `value`, es `valor`. En este caso no se hace uso de los getters y setters.

## ExponencialController
Se encarga de gestionar las peticiones que se realizarán a la entidad `exponencial`. Utiliza las anotaciones `@RestController`, `@RequestMapping`, `@Autowired`, `@PostMapping`, `@RequestBody`. Tiene un atributo de tipo `ExponencialService`, también tiene un constructor que recibe un objeto de tipo `exponencialService` y un método de tipo `Exponencial` el cual se encarga de guardar los valores. Recibe un objeto de tipo `Exponencial`.

## ExponencialService
`ExponencialService` es una clase de servicio en la aplicación que maneja operaciones relacionadas con datos exponenciales. Sus principales funciones incluyen:
- **printExponencial()**: Imprime los datos exponenciales.
- **shutdownExecutor()**: Apaga el `ExecutorService` utilizado para manejar tareas en hilos separados.

## MenuController
`MenuController` es una clase de controlador en la aplicación que maneja las solicitudes HTTP relacionadas con el menú principal. Sus principales funciones incluyen:
- **showMenu()**: Muestra la página del menú principal.

# Frontend
## Descripción de las Páginas HTML

A continuación se describen los detalles de las páginas HTML que forman parte de este proyecto:

---

### `MenuNormales.html`

Esta página simula el comportamiento clásico del Tablero de Galton con una **distribución normal**. 

- **Interfaz gráfica**: Muestra un tablero donde las bolas caen a través de una serie de pines dispuestos en filas.
- **Funcionalidad**:
  - Al presionar el botón **Iniciar Caída de Bolitas**, se inicia la simulación de la caída de bolas. Las bolas caen a través de los pines, desviándose aleatoriamente hacia la izquierda o derecha con una probabilidad del 50%. 
  - Las bolas terminan acumulándose en columnas al fondo del tablero, formando una distribución normal (más acumulación de bolas en el centro).
- **Consola de Datos**:
  - Al iniciarse la simulación, también se envía una solicitud al backend para imprimir en la consola los datos relacionados con la distribución normal. Esto es útil para realizar un seguimiento de los datos estadísticos de la simulación.
  
---

### `MenuExponencial.html`

Esta página implementa una variante del Tablero de Galton donde las bolas caen según una **distribución exponencial**, lo que introduce un sesgo en la dirección de las bolas.

- **Interfaz gráfica**: Similar a la página de distribución normal, se muestra un tablero con pines y columnas donde las bolas caerán.
- **Funcionalidad**:
  - Al presionar el botón **Iniciar Caída de Bolitas**, se simula la caída de bolas, pero esta vez con una probabilidad sesgada hacia la derecha. Esto se implementa usando una función de probabilidad exponencial que aumenta la probabilidad de que las bolas se desvíen hacia un lado (derecha).
  - Las bolas terminan acumulándose en columnas, pero la distribución resultante será asimétrica (sesgada hacia un lado).
- **Consola de Datos**:
  - Al iniciarse la simulación, se envía una solicitud al backend para que imprima los datos relacionados con la distribución exponencial en la consola. Esto permite observar cómo varían los datos cuando se usa una distribución exponencial frente a la normal.

---

### `MenuPrincipal.html`

Esta página sirve como el **menú principal** para acceder a las simulaciones de distribuciones. Contiene botones que redirigen a las páginas de simulación de distribuciones normales y exponenciales.

- **Funcionalidad**:
  - La página presenta dos botones:
    1. **Ir al Menu Normal**: Al hacer clic en este botón, se redirige a la página `MenuNormales.html` donde se puede iniciar la simulación con distribución normal.
    2. **ir al menu Exponncial**: Al hacer clic en este botón, se redirige a la página `MenuExponencial.html`, donde se puede iniciar la simulación con distribución exponencial.

---

Cada una de estas páginas utiliza **D3.js** para manipular elementos SVG y animar la caída de bolas en el tablero de Galton. Las bolas caen simulando desviaciones aleatorias (o sesgadas en el caso de la distribución exponencial), y los datos correspondientes se envían al backend mediante `fetch` para su posterior análisis o impresión en consola.

