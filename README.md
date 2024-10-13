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

PARTE CODIGO 
esta compuesto por las clases: customThreadFactory,Datos,DatosController,DatosService,DatosWebSocket,DemoApplication,ExecutorServiceManger,Exponencial,ExponencialController,ExponencialService

CustomThreadFactory:
esta clase es una factoria de hilos la cual implementa de ThreadFactory, como atributos hace uso de un string y de un AtomicInteger
El Atomic integer se utliza para evitar condiciones de carrera entre los hilos,el string es para proporcionar un nombre personalizado a cada hilo, esta clase tiene un constructor que recibe el string y un metodo con la notacion @Override y de tipo Thread que recibe un objeto de tipo Runnable, se encarga de imprimir que hilo ha escrito cada dato de la base de datos

Datos:
Esta clase es una entidad en la cual se guardarán los datos de la tabla normal que luego aparece en la base de datos de mySQL, hace uso de las anotaciones @Entity,@Data,@Table,@Id,@Column y @GeneratedValue.
el nombre de la tabla es datos, tiene los atributos ID, que se usa para darle un valor por asi decirlo a cada una de las lineas de la entidad, y el atributo value que es el valor de cada dato del csv de la distribucion normal, esta clase ademas esta compuesta por los getters y setters de ambos atributos aunque en este ejercicio solo se hacen uso de los getters y setteres de value

DatosController: esta clase se encarga de manejar las solicitudes que se le harán a la entidad de datos. hace uso de las anotaciones @Rest
Controller,@RequestController,@Autowired,@GetMapping,@RequestBody y @PostMapping.

se tiene un atributo de tipo DatosService, tiene una Lista la cual se usa para retornar los 1000 valores de la entidad de datos y un metodo de tipo Datos para guardar un dato que se recibirá por parametro

esta compuesto por la interfaces:
DatosRepository y ExponencialRepository

DatosRepository: se encarga de que se puedan realizar las operaciones de CRUD en la base de datos de la entidad Datos.
esta entidad extiende de JpaRepository

ExponencialRepository: se encarga de que se puedan realizar las operaciones de CRUD en la base de datos de la entidad Exponencial.
esta entidad extiende de jpaRepository

-[] DatosService:
-

-[] DatosWebSocketHandler
-

-[] DemoApplication 
-

Exponencial: esta clase es la entidad en la que se guardarán los datos de la exponencial en mySQL, esta compuesto por las anotaciones @Entity,@Data,@Table,@Id,@GeneratedValue,@Column.
en este caso la entidad se llama exponencial, tiene un atributo principal qu es el Id, el cual tiene la misma funcion que en la otra entidad, y en esta entidad el otro atributo en vez de value, es valor, en este caso no se hace uso de los getters y setters

ExponencialController: se encarga de gestionar las peticiones que se realizarán a la entidad exponencial, hace uso de las anotaciones @RestController,@RequestMapping,@Autowired,@PostMapping,@RequestBody.
tiene un atributo de tipo ExponencialService, tambien tiene un constructor que recibe un objeto de tipo exponencialService y un metodo de tipo Exponencial el cual se encarga de guardar los valores, Recibe un objeto de tipo Exponencial

ExponencialService

MenuController


PARTE FRONT
