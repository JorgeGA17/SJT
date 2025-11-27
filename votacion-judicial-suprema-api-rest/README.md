# votacion-judicial-suprema-api-rest

Proyecto back con arquitectura hexagonal que maneja toda la funcionalidad de la mesa de trabajo de Suprema

### Requisitos Iniciales

| Herramienta     | Version                           |
|:----------------|:----------------------------------|
| Java            | 21.x                              |
| Springboot      | 3.5.4                             |
| Maven           | 3.9                               |
| Lombok          | 1.x                               |

- Configurar Java, Maven y Lombok en una ruta local del espacio de trabajo.

### Contexto del servicio

- /votacion-judicial-suprema-api-rest

### arquitectura

```
votacion-judicial-suprema-api-rest/
│
├── votacion-judicial-suprema-api-domain/
│   ├── src/main/java
│   │   ├── pe/gob/pj/votacion/domain/common/
│   │   │   ├── enums/                                              # Enums comunes.
│   │   │   ├── constants/                                          # Constantes globales.
│   │   │   ├── utils/                                              # Clases utilitarias.
│   │   │   │
│   │   │
│   │   ├── pe/gob/pj/votacion/domain/model/                   # Modelos y DTOs.
│   │   │   ├── negocio/
│   │   │   ├── [basedatos]/
│   │   │   ├── cliente/[servicioconsumir]/
│   │   │   │
│   │   │
│   │   ├── pe/gob/pj/votacion/domain/port/                    # Interfaces de la funcionalidad a utilizar.
│   │   │   ├── client/[servicioconsumir]/                          # Servicios externos consumidos.
│   │   │   ├── persistence/                                        # Manejo de datos en BD.
│   │   │   ├── usecase/                                            # Casos de uso.
│   │   │   │
│   │   │
│   │   ├── pe/gob/pj/votacion/domain/exceptions/              # Package donde va todas las excepciones personalizadas.
│   │   │   ├── TokenException.java
│   │   │   ├── CaptchaException.java
│   │   │   ├── ...
│   │   │   │
│   │
│   └── pom.xml                                                     # Archivo domain de configuración maven
│
├── votacion-judicial-suprema-api-usecase/
│   ├── src/main/java
│   │   ├── pe/gob/pj/votacion/usecase/                        # Package donde van las clases que implementan ports usecase
│   │   │   ├── SeguridadUseCaseAdapter.java                        # Clase que implementa el caso de uso para seguridad
│   │   │   ├── ...
│   │   │   │
│   │
│   └── pom.xml                                                     # Archivo usecase de configuración maven               
│
├── votacion-judicial-suprema-api-infrastructure/
│   ├── src/main/java
│   │   ├── pe/gob/pj/votacion/infrastructure/async/           # Package donde va todo referente al manejo de asyncronia
│   │   │   ├── AsyncConfig.java                                    # Clase de configuración para async
│   │   │   ├── ...
│   │   │   │
│   │   │   
│   │   ├── pe/gob/pj/votacion/infrastructure/client/          # Package donde va todo referente al consumo de servicios
│   │   │   ├── RestTemplateConfig.java                             # Clase de configuración para resttemplate.
│   │   │   ├── ...
│   │   │   │
│   │   │
│   │   ├── pe/gob/pj/votacion/infrastructure/db/              # Package donde va todo referente al consumo de base de datos
│   │   │   ├── [basedatos]Config.java                              # Clase de configuración a bd a manejar Ej: SeguridadConfig.java
│   │   │   ├── [basedatos]/entities/                               # Package donde va las clases entidades
│   │   │   ├── [basedatos]/repositories/                           # Package donde va los objetos reposiotry y dsl de las entidades
│   │   │   ├── persistence/                                        # Package donde va las clases que implementan ports persistence
│   │   │   ├── ...
│   │   │   │
│   │   │
│   │   ├── pe/gob/pj/votacion/infrastructure/mappers/         # Package donde van las interfaces que sirven para mapear clases
│   │   │   ├── ...
│   │   │   │
│   │   │
│   │   ├── pe/gob/pj/votacion/infrastructure/documentation/   # Package donde va todo referente a documentación de endpoints
│   │   │   ├── SwaggerConfig.java                                  # Clase de configuración de swagger
│   │   │   ├── ...
│   │   │   │
│   │   │
│   │   ├── pe/gob/pj/votacion/infrastructure/rest/            # Package donde va todo referente a los endpoints.
│   │   │   ├── advises/                                            # Package donde va interceptor para manejar excepciones
│   │   │   ├── controllers/                                        # Package donde va interfaces y clases de endpoints
│   │   │   ├── requests/                                           # Package donde va las clases request de las peticiones.
│   │   │   ├── responses/                                          # Package donde va las clases response de las peticiones.
│   │   │   ├── ...
│   │   │   │
│   │   │
│   │   ├── pe/gob/pj/votacion/infrastructure/security/        # Package donde va todo referente a la seguridad.
│   │   │   ├── SecurityConfig.java                                 # Clase de configuración de spring security
│   │   │   ├── adapters/
│   │   │   │   └── UserDetailsServiceAdapter.java
│   │   │   └── filters/                                            # Package donde van los filtros de seguridad
│   │   │       ├── JwtAuthenticationFilter.java                    # Clase encargada de validar la autenticación de los parámetros
│   │   │       └── JwtAuthorizationFilter.java                     # Clase encargada de validar la autorización a los endpoints
│   │   │   
│   │   ├── ...
│   │
│   └── pom.xml                                                     # Archivo infraestructure de configuración maven
│
└── pom.xml                                                         # Archivo general de configuración de Maven.

```

### Base de Datos

| Entorno     | Tipo de BD        | Servidor      |Puerto|BD                    |Usuario del Servicio       |
|:------------|:------------------|:--------------|:-----|:---------------------|:--------------------------|
| Desarrollo  | PostgreSQL        |172.18.11.241  |39969 |Seguridad             |uc_prueba                  |
| Desarrollo  | Sybase ASE        |172.34.0.68    |5000  |sij_001_18_50_suprema |uc_VotacionJudicialSuprema |

### Recursos 

+ Los recursos corresponden a los archivo propeties y logs, y se debe ubicar en la carpeta modules/pe/gob/pj/prueba del servidor donde se despliega el proyecto.

+ Para obtener los recursos con git bash ejecutar el siguiente comando: 
```
git clone https://desagit.pj.gob.pe/sdsi/recurso-servicios-in-modules/votacion-judicial-suprema/api.git
```

### URLs del servicio

| Nombre        | Link Url                                                       |
|:--------------|:---------------------------------------------------------------|
|Url Base       |http://172.19.9.35:8080/votacion-judicial-suprema-api-rest                         |
|Documentación  |http://172.19.9.35:8080/votacion-judicial-suprema-api-rest/swagger-ui/index.html   |
|Disponibilidad |http://172.19.9.35:8080/votacion-judicial-suprema-api-rest/healthcheck             |

