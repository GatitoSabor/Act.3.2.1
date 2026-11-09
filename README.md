# Actividad 3.2.1 - Kafka: topic, productor, consumidor, offsets y particiones

Este proyecto implementa el topic `tutorial-particiones` con **3 particiones** y un consumidor del grupo `mi-grupo-educativo`.

## 1. Levantar Kafka

Con Docker Desktop iniciado, desde esta carpeta ejecuta:

```powershell
docker compose up -d
```

La interfaz Kafka UI queda disponible en http://localhost:8080. El topic se crea automáticamente al iniciar Spring Boot; también se puede revisar allí en **Topics > tutorial-particiones**.

## 2. Ejecutar la aplicación

Requiere Java 17 y Maven:

```powershell
mvn clean spring-boot:run
```

Comprueba el estado en http://localhost:8888/api/kafka/health.

## 3. Evidencias solicitadas

Sin key - distribución explícita round robin entre 0, 1 y 2:

```powershell
curl.exe "http://localhost:8888/api/kafka/send?message=Mensaje1"
curl.exe "http://localhost:8888/api/kafka/send?message=Mensaje2"
curl.exe "http://localhost:8888/api/kafka/send?message=Mensaje3"
```

Con key - Kafka mantiene los mensajes de la misma key en la misma partición:

```powershell
curl.exe "http://localhost:8888/api/kafka/send?message=Paso1-Creada&key=orden-555"
curl.exe "http://localhost:8888/api/kafka/send?message=Paso2-Pagada&key=orden-555"
curl.exe "http://localhost:8888/api/kafka/send?message=Paso3-Enviada&key=orden-555"
```

La consola de Spring Boot muestra para cada mensaje su partición, offset, key y contenido. En Kafka UI revisa la pestaña **Messages** del topic para capturar las mismas evidencias.

## Cierre

Para detener la infraestructura:

```powershell
docker compose down
```
