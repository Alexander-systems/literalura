package com.literalura;

import com.literalura.model.Autor;
import com.literalura.model.Libro;
import com.literalura.service.AutorService;
import com.literalura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	private LibroService libroService;

	@Autowired
	private AutorService autorService;

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		mostrarMenu();
	}

	private void mostrarMenu() {
		Scanner scanner = new Scanner(System.in);
		int opcion;

		do {
			System.out.println("\n--- Menú LiterAlura ---");
			System.out.println("1. Buscar libro por título");
			System.out.println("2. Listar todos los libros");
			System.out.println("3. Listar todos los autores");
			System.out.println("4. Listar autores vivos en un año");
			System.out.println("5. Mostrar cantidad de libros por idioma");
			System.out.println("0. Salir");
			System.out.print("Selecciona una opción: ");

			try {
				opcion = scanner.nextInt();
				scanner.nextLine();

				switch (opcion) {
					case 1:
						buscarLibroPorTitulo(scanner);
						break;
					case 2:
						listarLibros();
						break;
					case 3:
						listarAutores();
						break;
					case 4:
						listarAutoresVivosPorAnio(scanner);
						break;
					case 5:
						mostrarCantidadLibrosPorIdioma(scanner);
						break;
					case 0:
						System.out.println("Saliendo de la aplicación. ¡Hasta pronto!");
						break;
					default:
						System.out.println("Opción inválida. Por favor, selecciona una opción del 0 al 5.");
				}
			} catch (InputMismatchException e) {
				System.out.println("Entrada inválida. Por favor, ingresa un número para seleccionar una opción.");
				scanner.nextLine();
				opcion = -1;
			}

		} while (opcion != 0);

		scanner.close();
	}

	private void buscarLibroPorTitulo(Scanner scanner) {
		System.out.print("Ingrese el título del libro a buscar: ");
		String titulo = scanner.nextLine();
		List<Libro> librosEncontrados = libroService.buscarLibrosPorTitulo(titulo);
		if (librosEncontrados.isEmpty()) {
			System.out.println("No se encontraron libros con ese título.");
		} else {
			System.out.println("Libros encontrados:");
			librosEncontrados.forEach(System.out::println);
		}
	}

	private void listarLibros() {
		List<Libro> libros = libroService.listarLibros();
		if (libros.isEmpty()) {
			System.out.println("No hay libros guardados.");
		} else {
			System.out.println("Lista de libros:");
			libros.forEach(System.out::println);
		}
	}

	private void listarAutores() {
		List<Autor> autores = autorService.listarAutores();
		if (autores.isEmpty()) {
			System.out.println("No hay autores guardados.");
		} else {
			System.out.println("Lista de autores:");
			autores.forEach(System.out::println);
		}
	}

	private void listarAutoresVivosPorAnio(Scanner scanner) {
		System.out.print("Ingrese el año para listar autores vivos: ");
		try {
			int anio = Integer.parseInt(scanner.nextLine());
			if (anio < 0) {
				System.out.println("Por favor, ingrese un año válido.");
				return;
			}
			List<Autor> autoresVivos = autorService.obtenerAutoresVivosEnAnio(anio);
			if (autoresVivos.isEmpty()) {
				System.out.println("No se encontraron autores vivos en el año " + anio);
			} else {
				System.out.println("Autores vivos en el año " + anio + ":");
				autoresVivos.forEach(System.out::println);
			}
		} catch (NumberFormatException e) {
			System.out.println("Entrada inválida. Debe ingresar un número entero.");
		}
	}

	private void mostrarCantidadLibrosPorIdioma(Scanner scanner) {
		System.out.println("Ingrese el código del idioma (ej. 'en' para Inglés, 'es' para Español):");
		System.out.print("Idioma: ");

		String idiomaInput = scanner.nextLine().trim().toLowerCase();

		String idioma = null;
		switch (idiomaInput) {
			case "en":
				idioma = "en";
				break;
			case "es":
				idioma = "es";
				break;
			default:
				System.out.println("Código de idioma inválido. Por favor, ingrese 'en' o 'es'.");
				return;
		}

		long cantidad = libroService.contarLibrosPorIdioma(idioma);
		System.out.println("Cantidad de libros en " + idioma + ": " + cantidad);
	}
}