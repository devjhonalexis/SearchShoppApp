# ✅ SearchShoppApp

Una aplicación Android desarrollada en **Kotlin + Jetpack Compose** con arquitectura **Clean Architecture** y modularización por capas, implementando inyección de dependencias usando **Hilt**.

---

# ✅ Requisitos

- Android Studio Giraffe o superior
- Conexión a Internet

---

# ✅ Configuración de BASE URL

1. Abre el archivo `local.properties` en el proyecto raíz.
2. Agrega la siguiente línea:

```
BASE_URL="TU_BASE_URL_AQUI"
```

3. Asegúrate de que en tu `build.gradle` (modulo de data) exista esta línea:

```kotlin
buildConfigField "String", "BASE_URL", "\"${baseUrl}\""
```


## 🛠️ Estructura del Proyecto

```
├app/    # UI con Jetpack Compose + Navegación       
├domain/ # Modelos, casos de uso, interface repository
├data/   # Retrofit, repositorio impl.
```
