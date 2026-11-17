# login_danni — Prototipo de Login (Material 3 + Material You)

Prototipo de interfaz de inicio de sesión para Android con **Jetpack Compose**, **Material 3** y **Material You** (color dinámico).

## Contenido
- `docs/00-investigacion-y-arquitectura.md` — investigación y arquitectura
- `docs/sketches/` — bocetos · `docs/wireframes/` — wireframes · `docs/mockups/` — mockups (abrir los .svg en el navegador)
- `app/` — código del prototipo (Kotlin + Compose)

## Cómo ejecutarlo
1. Abrir la carpeta en **Android Studio** (Koala 2024.1.1 o superior, JDK 17).
2. Esperar el *Gradle Sync* (Android Studio descarga el wrapper y las dependencias).
3. Ejecutar en un emulador/dispositivo. Para ver Material You usar Android 12+ (API 31+) y cambiar el fondo de pantalla.
4. Credenciales de prueba: `demo@correo.com` / `Demo1234`.

## Tecnologías
Kotlin 2.0 · Compose BOM 2024.09 · Material3 · ViewModel + StateFlow (MVVM/UDF) · minSdk 24 · targetSdk 35
