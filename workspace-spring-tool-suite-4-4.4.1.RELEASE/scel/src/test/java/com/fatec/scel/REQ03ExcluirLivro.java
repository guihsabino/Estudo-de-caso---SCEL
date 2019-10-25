package com.fatec.scel;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.fatec.scel.model.Livro;
import com.fatec.scel.model.LivroRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
class REQ03ExcluirLivro {
	@Autowired
	LivroRepository repository;

	@Test
	public void CT01ExcluirLivroComSucesso() {
// dado que o isbn esta cadastrado
		Livro livro = new Livro("3333", "Teste de Software", "Delamaro");
		repository.save(livro);
// quando o usurio exclui o livro
		Livro ro = repository.findByIsbn("3333");
		repository.deleteById(ro.getId());
// entao
		assertThat(repository.findByIsbn("3333")).isEqualTo(null);
	}
}
