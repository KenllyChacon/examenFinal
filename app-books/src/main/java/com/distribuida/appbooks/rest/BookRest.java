package com.distribuida.appbooks.rest;

import com.distribuida.appbooks.clients.AuthorsRestClient;
import com.distribuida.appbooks.db.Book;
import com.distribuida.appbooks.dtos.BookDto;
import com.distribuida.appbooks.repo.BookRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.stream.Collectors;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
@Transactional
public class BookRest {
    @Inject
    BookRepository repo;

    @Inject
    @RestClient
    AuthorsRestClient authorClient;

    @GET
    @Operation(
            summary = "Lista todos los libros",
            description = "Lista todos los libros ordenados por nombre")
    public List<BookDto> findAll() {
       return repo.streamAll()
               .map(book->{
                   var author = authorClient.getById(book.getAuthorId());

                   var dto = BookDto.from(book);

                   String aname = String.format("%s %s",
                           author.getLastName(), author.getFirstName());

                   dto.setAuthorName( aname );

                   return dto;
               })
               .collect(Collectors.toList());
    }

    @GET
    @Operation(summary = "Obtener un libro por ID")
    @APIResponse(responseCode = "200", description = "Libro encontrado")
    @Path("/{id}")
    public Response findById(@PathParam("id")Integer id) {
        var book = repo.findByIdOptional(id);

        if(book.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(book.get()).build();
    }

    @POST
    @Operation(summary = "Crear un nuevo libro")
    @APIResponse(responseCode = "201", description = "Libro creado")
    public Response create(Book obj) {
        obj.setId(null);

        repo.persist(obj);

        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Actualizar un libro por ID")
    @APIResponse(responseCode = "200", description = "Libro actualizado")
    @APIResponse(responseCode = "404", description = "Libro no encontrado")
    public Response update(@PathParam("id")Integer id, Book obj) {

        Book b = repo.findById(id);

        b.setIsbn(obj.getIsbn());
        b.setTitle(obj.getTitle());
        b.setPrice(obj.getPrice());
        b.setAuthorId(obj.getAuthorId());

        return Response.ok()
                .build();
    }

    @DELETE
    @Operation(summary = "Eliminar un libro por ID")
    @APIResponse(responseCode = "200", description = "Libro eliminado")
    @Path("/{id}")
    public Response delete(@PathParam("id")Integer id) {
        repo.deleteById(id);

        return Response.ok()
                .build();
    }
}
