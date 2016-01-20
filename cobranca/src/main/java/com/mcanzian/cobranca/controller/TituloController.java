package com.mcanzian.cobranca.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mcanzian.cobranca.model.StatusTitulo;
import com.mcanzian.cobranca.model.Titulo;
import com.mcanzian.cobranca.repository.filter.TituloFilter;
import com.mcanzian.cobranca.service.CadastroTituloService;

@Controller
@RequestMapping("/titulos")
public class TituloController {
	
	private static String CADASTRO_VIEW = "CadastroTitulo";
	
	@Autowired
	private CadastroTituloService cadastroTituloService;
	

	@RequestMapping("/novo")
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject("titulo", new Titulo());
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated Titulo titulo, Errors errors, RedirectAttributes attributes) {
		if (errors.hasErrors())
			return CADASTRO_VIEW;
		
		try {
			cadastroTituloService.salvar(titulo);
		} catch (IllegalArgumentException e) {
			errors.rejectValue("dataVencimento", null, e.getMessage());
			return CADASTRO_VIEW;
		}
		attributes.addFlashAttribute("mensagemSucesso", "Título salvo com sucessso.");
		
		return "redirect:/titulos/novo";
	}
	
	@RequestMapping
	public ModelAndView listar(@ModelAttribute("filtro") TituloFilter filtro) {
		ModelAndView mv = new ModelAndView("ListaTitulos");
		
		List<Titulo> todosTitulos = cadastroTituloService.filtar(filtro);
		mv.addObject("titulos", todosTitulos);
		
		return mv;
	}
	
	@RequestMapping("{id}")
	public ModelAndView editar(@PathVariable("id") Titulo titulo) {
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		
		mv.addObject("titulo", titulo);
		
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "{id}")
	public String excluir(@PathVariable Long id, RedirectAttributes attributes) {
		cadastroTituloService.excluir(id);
		attributes.addFlashAttribute("mensagemSucesso", "Título excluido com sucesso.");
		return "redirect:/titulos";
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}/atualizar-status")
	public @ResponseBody String atualizarStatus(@PathVariable Long id) {
		return cadastroTituloService.atualizarStatus(id);
	}
	
	@ModelAttribute("todosStatusTitulo")
	public List<StatusTitulo> todosStatusTitulo() {
		return Arrays.asList(StatusTitulo.values());
	}

}
