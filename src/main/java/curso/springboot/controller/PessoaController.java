package curso.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import curso.springboot.model.Pessoa;
import curso.springboot.repository.PessoaRepository;

@Controller
public class PessoaController {
	
	@Autowired
	private PessoaRepository pessoaRepository;

	@RequestMapping(method=RequestMethod.GET, value="/cadastropessoa")
	public String inicio(){
		return "cadastro/cadastropessoa";
	}
	
	//requestMapping intercepta a requisição sempre que a ação "salvarpessoa" for acionada
	@RequestMapping(method=RequestMethod.POST, value="/salvarpessoa")
	public String salvar(Pessoa pessoa){
		//persiste no banco
		pessoaRepository.save(pessoa);
		return "cadastro/cadastropessoa";
	}
	
	@RequestMapping(method= RequestMethod.GET, value="/listapessoas")
	public ModelAndView pessoas(){
		//instanciar o view para injetar os dados na tela
		ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
		//Obtem do banco a lista de pessoas
		Iterable<Pessoa> PessoasIt = pessoaRepository.findAll();
		//adiciona na viw a lista de pessoas. Atributo pessoas criado no html
		andView.addObject("pessoas", PessoasIt);
		return andView;
	}
	
}
