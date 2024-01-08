package com.book.management.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.book.management.domain.Author;
import com.book.management.dto.AuthorDTO;
import com.book.management.repository.AuthorRepository;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public AuthorDTO createAuthor(AuthorDTO authorDTO) {
        Author author = new Author();
        author.setGivenName(authorDTO.getGivenName());
        author.setLastName(authorDTO.getLastName());
        author = authorRepository.save(author);
        return mapToDTO(author);
    }
    public Author getAuthorEntityById(Long id) {
        return authorRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Author not found for id :: " + id));
    }

    public List<AuthorDTO> getAllAuthors() {
        return authorRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public AuthorDTO getAuthorById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found for id :: " + id));
        return mapToDTO(author);
    }

    public AuthorDTO updateAuthor(Long id, AuthorDTO authorDTO) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found for id :: " + id));
        author.setGivenName(authorDTO.getGivenName());
        author.setLastName(authorDTO.getLastName());
        author = authorRepository.save(author);
        return mapToDTO(author);
    }

    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    private AuthorDTO mapToDTO(Author author) {
        return new AuthorDTO(
                author.getId(),
                author.getGivenName(),
                author.getLastName()
        );
    }
}
