package com.fatec.scel.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.fatec.scel.model.Livro;
import com.fatec.scel.model.LivroRepository;

@RestController
@RequestMapping(path = "/livros")
public class LivroController {
	@Autowired
	private LivroRepository repository;

	@GetMapping("/cadastrar")
	public ModelAndView cadastraLivro(Livro livro) {
		ModelAndView mv = new ModelAndView("CadastrarLivro");
		mv.addObject("livro", livro);
		return mv;
	}

	@GetMapping("/consulta")
	public ModelAndView listar() {
		ModelAndView modelAndView = new ModelAndView("ConsultarLivros");
		modelAndView.addObject("livros", repository.findAll());
		return modelAndView;
	}

	@PostMapping("/save")
	public ModelAndView save(@Valid Livro livro, BindingResult result) {
		ModelAndView mv = new ModelAndView("CadastrarLivro");
		if (result.hasErrors()) {
			mv.addObject("fail", "Dados inválidos"); // quando fail nao eh nulo a msg aparece na tela
			return mv;
		}
		try {
			Livro jaExiste = null;
			jaExiste = repository.findByIsbn(livro.getIsbn());
			if (jaExiste == null) {
				repository.save(livro);
				mv.addObject("success", "Livro cadastrado com sucesso"); // quando success nao eh nulo
				return mv;
			} else {
				mv.addObject("fail", "Livro já cadastrado."); // quando fail nao eh nulo msg na tela
				return mv;
			}
		} catch (Exception e) {
			mv.addObject("fail", "erro ===> " + e.getMessage());
			return mv;
		}
	}
}