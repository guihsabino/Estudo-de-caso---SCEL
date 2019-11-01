package com.fatec.scel;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.fatec.scel.model.Livro;
import com.fatec.scel.model.LivroRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class REQ02ConsultarLivro {
	@Autowired
	LivroRepository repository;
	private Validator validator;
	private ValidatorFactory validatorFactory;

	@Test
	public void CT01ConsultarLivroComSucesso() {
// dado que o livro esta cadastrado
		validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();

		Livro livro = new Livro("3333", "Teste de Software", "Delamaro");
		repository.save(livro);

		Set<ConstraintViolation<Livro>> violations = validator.validate(livro);
// quando o usurio consulta o livro
		Livro ro = repository.findByIsbn("3333");
// entao
		assertThat(ro.getTitulo()).isEqualTo(livro.getTitulo());
		assertThat(ro.getAutor()).isEqualTo(livro.getAutor());
	}

	@Test
	public void CT02DetectarConsultaInvalida() {
// dado que o livro esta cadastrado
		validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();

		Livro livro = new Livro("3333", "", "Delamaro");
		repository.save(livro);

		Set<ConstraintViolation<Livro>> violations = validator.validate(livro);
// quando o usuario consulta o livro
		Livro ro = repository.findByIsbn("3333");
// entao
		assertThat(ro.getTitulo()).isEqualTo(livro.getTitulo());
		assertEquals(violations.size(), 1);
		assertEquals("O titulo deve ser preenchido", violations.iterator().next().getMessage());
	}
}
