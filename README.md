# T2.2.-Catalogo-de-la-Biblioteca-FISEI-con-arbol-general

# Deber
**Deber individual — Catálogo de la Biblioteca FISEI con árbol general y ABB en Java**

Asignatura: Estructura de Datos · Nivel: Tercero – Ingeniería de Software
Estudiante: Altamirano Segovia Jullisa Brigitte

## 1. Problema

La Biblioteca FISEI se organiza en **Áreas** y cada Área en **Estantes**. Esta jerarquía se representa
con un **árbol general n-ario** (`ArbolBiblioteca`), donde cada **ESTANTE** guarda sus libros en un
**Árbol Binario de Búsqueda (ABB)** (`CatalogoABB`) ordenado por el código del libro (entero
positivo, sin repetidos). La aplicación permite construir, consultar, recorrer y modificar ambas
estructuras, además de resolver los retos (rango con poda, camino de búsqueda, validación ABB y
reconstrucción desde preorden).

Estructura inicial:

```
BIB Biblioteca FISEI
 ├── SW   Software
 │    ├── SW-E1 Estante 1 de Software   →  60, 35, 80, 20, 45, 70, 90, 40, 75
 │    └── SW-E2 Estante 2 de Software   →  15, 10, 25
 ├── TI   Tecnologías de la Información
 │    └── TI-E1 Estante 1 de TI         →  50
 └── TEL Telecomunicaciones
```

7 unidades, 4 hojas, altura 2 aristas y 13 libros.

## 2. Estructura del proyecto

```
src/
 ├── enums/       TipoUnidad.java, TipoRecorrido.java
 ├── modelo/      Libro.java, UnidadBiblioteca.java
 ├── negocio/     NodoUnidad.java, NodoLibro.java, ArbolBiblioteca.java, CatalogoABB.java
 └── app/         Main.java, DatosPrueba.java, PruebasBiblioteca.java
TRAZAS.md         Trazas resueltas a mano + salida real del programa
README.md         Este archivo
```

## 3. Instrucciones de ejecución

Requisitos: JDK 8 o superior (probado con OpenJDK 21).

```bash
# Compilar
javac -encoding UTF-8 -d bin $(find src -name "*.java")

# Ejecutar el menú interactivo
java -cp bin app.Main

# Ejecutar la batería de pruebas (mínimo 10 casos)
java -cp bin app.PruebasBiblioteca

```

En Visual Studio Code: abrir la carpeta del proyecto, instalar la extensión *Extension Pack for
Java* y ejecutar la clase deseada con el botón **Run**.

## 4. Resultado de PruebasBiblioteca

```
====================================================
25/25 pruebas OK
====================================================
```

Incluye todos los casos de prueba obligatorios del deber: altura/hojas de la raíz (0 y 1),
biblioteca inicial (2, 4 y 13 libros), niveles (BIB=0, sw=1, SW-E2=2, XYZ=-1), estante como hijo de
BIBLIOTECA (false), recorridos de SW-E1, código repetido (false), `caminoBusqueda(42) = [60, 35, 45, 40]`,
`contarEnRango(40, 75) = 5`, eliminaciones 20→70→60 con preorden final `75 35 45 40 42 80 90`,
códigos inexistentes (null/false), `IllegalArgumentException` con datos inválidos, `esABBValido()`
con un nieto mal ubicado (false) y el extra `desdePreorden`.

## 5. Restricciones respetadas

- No se usa `TreeSet`, `TreeMap`, `PriorityQueue`, `NavigableSet`, `NavigableMap`,
  `Collections.sort` ni `List.sort`.
- Todas las operaciones de los árboles están implementadas mediante **recursividad**.
- Se respetan los nombres de paquetes, clases y métodos indicados en el deber.
