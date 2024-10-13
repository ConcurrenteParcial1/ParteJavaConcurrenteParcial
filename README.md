https://github.com/ffernandoss/ParteJavaConcurrenteParcial.git
## TODO
-[] Dividir por paquetes
-[] escribir la clase DatosService
-[] escribir la clase ExponencialService
-[] realizar issues
-[] ver si datos web socket handler se usa o no
-[] escribir main
-[] borrar executor
-[] borrar ExecutorServiceManager
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
(Descripción pendiente)

## DatosWebSocketHandler
(Descripción pendiente)

## DemoApplication 
(Descripción pendiente)

## Exponencial
Esta clase es la entidad en la que se guardarán los datos de la exponencial en MySQL. Está compuesta por las anotaciones `@Entity`, `@Data`, `@Table`, `@Id`, `@GeneratedValue`, `@Column`. En este caso, la entidad se llama `exponencial`, tiene un atributo principal que es el `Id`, el cual tiene la misma función que en la otra entidad, y en esta entidad el otro atributo en vez de `value`, es `valor`. En este caso no se hace uso de los getters y setters.

## ExponencialController
Se encarga de gestionar las peticiones que se realizarán a la entidad `exponencial`. Utiliza las anotaciones `@RestController`, `@RequestMapping`, `@Autowired`, `@PostMapping`, `@RequestBody`. Tiene un atributo de tipo `ExponencialService`, también tiene un constructor que recibe un objeto de tipo `exponencialService` y un método de tipo `Exponencial` el cual se encarga de guardar los valores. Recibe un objeto de tipo `Exponencial`.

## ExponencialService
(Descripción pendiente)

## MenuController
(Descripción pendiente)

# Front
(Descripción pendiente)
