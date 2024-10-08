package exercise.service;

import exercise.dto.BookCreateDTO;
import exercise.dto.BookDTO;
import exercise.dto.BookUpdateDTO;
import exercise.exception.BadRequestException;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.BookMapper;
import exercise.repository.AuthorRepository;
import exercise.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    // BEGIN
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private AuthorRepository authorRepository;

    public List<BookDTO> getAll() {
        return bookRepository.findAll().stream().map(bookMapper::map).toList();
    }

    public BookDTO findById(Long id) {
        var book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ResourceNotFoundException"));
        return bookMapper.map(book);
    }

    public BookDTO create(BookCreateDTO bookCreateDTO) {
        authorRepository.findById(bookCreateDTO.getAuthorId())
                .orElseThrow(() -> new BadRequestException("BadRequestException"));
        var book = bookMapper.map(bookCreateDTO);
        bookRepository.save(book);
        return bookMapper.map(book);
    }

    public BookDTO update(BookUpdateDTO bookUpdateDTO, Long id) {
        authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ResourceNotFoundException"));
        var book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ResourceNotFoundException"));
        bookMapper.update(bookUpdateDTO, book);
        bookRepository.save(book);
        return bookMapper.map(book);
    }

    public void delete(Long id) {
        var book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ResourceNotFoundException"));
        bookRepository.delete(book);
    }
    // END
}
